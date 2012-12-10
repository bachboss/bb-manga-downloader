/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.bus;

import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.bus.model.data.DownloadTask.DownloadTaskStatus;
import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.gui.MangaDownloadGUI;
import bbmangadownloader.ult.*;
import bbmangadownloader.ult.HttpDownloadManager.MyConnection;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class ListTaskDownloader implements Runnable {

    private List<DownloadTask> listTask;
    private boolean isRunning = false;
    private AbstractTableModel tableModel;

    public ListTaskDownloader(List<DownloadTask> listTask, AbstractTableModel tableModel) {
        this.listTask = listTask;
        this.tableModel = tableModel;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        try {
            if (!isRunning) {
                isRunning = true;
                if (listTask == null) {
                    return;
                }
                for (DownloadTask task : listTask) {
                    if (task.getStatusEnum() == DownloadTaskStatus.No || task.getStatusEnum() == DownloadTaskStatus.Stopped) {
                        synchronized (task) {
                            DownloadTaskStatus status = task.getStatusEnum();
                            if (status == DownloadTaskStatus.No || status == DownloadTaskStatus.Stopped) {
                                task.setStatus(DownloadTaskStatus.Downloading);
                                doDownloadChapter(task);
                                tableModel.fireTableDataChanged();
                                task.setStatus(DownloadTaskStatus.Done);
                            }
                        }
                    }
                }
                isRunning = false;
            }
        } catch (Exception ex) {
            GUIUtilities.showException(null, ex);
            Logger.getLogger(ListTaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void downloadListImages(final DownloadTask task, List<Image> lstImg, File imageFolder) throws InterruptedException {
        ArrayList<Callable<Boolean>> listImageTask = new ArrayList<Callable<Boolean>>();
        for (int i = 0; i < lstImg.size(); i++) {
            Image img = lstImg.get(i);
            try {
                File fileImage = FileManager.getFileForImage(imageFolder, img);
                listImageTask.add(new DefaultFileDownloader(task, img.getConnection(), fileImage));
                tableModel.fireTableDataChanged();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ListTaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        task.clearCurrentImage();
        MultitaskJob.doTask(listImageTask);
    }

    private void doDownloadChapter(DownloadTask task) {
        // Prepare for downloading
        Chapter c = task.getChapter();
        IFacadeMangaServer usingServer = c.getManga().getServer().getMangaServer();
        System.out.println("Loading Chapter: " + c + "\t" + c.getUrl());
        GUIUtilities.showLog("Loading Chapter: " + c + "\t" + GUIUtilities.compressPath(c.getUrl()));
        File imageFolder = FileManager.getFolderForChapter(c);
        if (imageFolder.exists()) {
            // Check for duplicate folder - May downloaed before
            if (!FileUtilities.isImageFolderEmpty(imageFolder)) {
                int value = GUIUtilities.showConfirmError(null,
                        "Chapter \"" + c.getDisplayName() + "\" could already been downloaded. "
                        + "Delete it and start new download?",
                        "Directory not empty", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                // Cancel
                if (value == JOptionPane.CANCEL_OPTION) {
                    task.setStatus(DownloadTaskStatus.Stopped);
                    return;
                }
                FileUtilities.deleteDirector(imageFolder);
            }
        }
        imageFolder.mkdirs();
        task.setDownloadTo(imageFolder);
        try {
            System.out.println("Start Get Image of " + c);
            // Parsing Data...
            task.setStatus(DownloadTaskStatus.Parsing);
            tableModel.fireTableCellUpdated(0, 2);
            List<Image> lstImg = usingServer.getAllImages(c);
            c.addImages(lstImg);
            System.out.println("\tGot " + lstImg.size() + " image(s)");
            // Download...
            task.setStatus(DownloadTaskStatus.Downloading);
            tableModel.fireTableDataChanged();
            System.out.println("\tSave to: " + imageFolder.getAbsolutePath());
            downloadListImages(task, lstImg, imageFolder);
            // Turn-off the logger
            //MyLogger.log(lstImg, imageFolder.getParentFile(), c);
            // Download is done. Zip File and do other stuffs
            {
                ConfigManager config = (ConfigManager.getCurrentInstance());
                if (config.isGenerateHtml()) {
                    HtmlUtilities.doGenerate(c.getDisplayName(), imageFolder);
                }
                if (config.isZip()) {
                    FileUtilities.zipDirectory(imageFolder);
                    if (config.isDeleteAfterZip()) {
                        FileUtilities.deleteDirector(imageFolder);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
            task.setStatus(DownloadTaskStatus.Error);
        }

    }

    private class DefaultFileDownloader extends AFileDownloader {

        private DownloadTask task;

        public DefaultFileDownloader(DownloadTask task, MyConnection connection, File fileOutput) {
            super(connection, fileOutput);
            this.task = task;
        }

        @Override
        public void finishFileDownload() {
            task.increaseCurrentImage();
            tableModel.fireTableDataChanged();
        }
    }
}
