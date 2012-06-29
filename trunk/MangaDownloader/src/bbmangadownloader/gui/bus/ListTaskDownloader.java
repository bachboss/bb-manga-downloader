/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.bus;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.gui.MangaDownloadGUI;
import bbmangadownloader.ult.FileManager;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.MultitaskJob;
import bbmangadownloader.ult.MyLogger;

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
        isRunning = true;
        DownloadTask task = null;
        while (this.listTask != null && !this.listTask.isEmpty()) {
            synchronized (listTask) {
                if (this.listTask != null && !this.listTask.isEmpty()) {
                    task = listTask.get(0);
                } else {
                    task = null;
                }
            }
            if (task != null) {
                synchronized (task) {
                    task.setStatus(DownloadTask.STATUS_RUNNING);
                    Chapter c = task.getChapter();
                    IFacadeMangaServer usingServer = c.getManga().getServer().getMangaServer();
                    System.out.println("Loading Chapter: " + c + "\t" + c.getUrl());
                    GUIUtilities.showLog("Loading Chapter: " + c + "\t" + GUIUtilities.compressPath(c.getUrl()));
                    try {
                        System.out.println("Start Get Image of " + c);
                        // Parsing Data...
                        task.setStatus(DownloadTask.STATUS_PARSING);
                        tableModel.fireTableCellUpdated(0, 2);
                        List<Image> lstImg = usingServer.getAllImages(c);
                        System.out.println("\tGot " + lstImg.size() + " image(s)");
                        // Download...
                        task.setStatus(DownloadTask.STATUS_RUNNING);
                        tableModel.fireTableCellUpdated(0, 2);
                        File imageFolder = FileManager.getFolderForChapter(c);
                        System.out.println("\tSave to: " + imageFolder.getAbsolutePath());

                        downloadListImages(task, lstImg, imageFolder);

                        MyLogger.log(lstImg, imageFolder.getParentFile(), c);
                    } catch (Exception ex) {
                        Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
                        task.setStatus(DownloadTask.STATUS_ERROR);
                    }
                }
                synchronized (listTask) {
                    listTask.remove(task);
                }
                tableModel.fireTableDataChanged();
            }
        }
        GUIUtilities.showLog("Done !");
        System.out.println("Done !");
        isRunning = false;
    }

    private void downloadListImages(final DownloadTask task, List<Image> lstImg, File imageFolder) throws InterruptedException {
        ArrayList<Callable<Boolean>> listImageTask = new ArrayList<>();
        for (int i = 0; i < lstImg.size(); i++) {
            Image img = lstImg.get(i);
            try {
                URL u = img.getURL();
                File fileImage = FileManager.getFileForImage(imageFolder, img);
                listImageTask.add(new DefaultFileDownloader(task, u, fileImage));
            } catch (MalformedURLException ex) {
                Logger.getLogger(ListTaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        task.clearCurrentImage();

        MultitaskJob.doTask(DEFAULT_POOL_SIZE, listImageTask);
    }
    private static final int DEFAULT_POOL_SIZE = 4;

    private class DefaultFileDownloader extends AFileDownloader {

        private DownloadTask task;

        public DefaultFileDownloader(DownloadTask task, URL url, File fileOutput) {
            super(url, fileOutput);
            this.task = task;
        }

        @Override
        public void doOnFinish() {
            task.increaseCurrentImage();
            tableModel.fireTableCellUpdated(0, 2);
        }
    }
}
