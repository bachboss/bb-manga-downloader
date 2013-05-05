/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.bus;

import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.bus.model.data.DownloadTask.DownloadTaskStatus;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.gui.MangaDownloadGUI;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.manager.FileManager;
import bbmangadownloader.ult.FileUtilities;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.HtmlUtilities;
import bbmangadownloader.ult.MultitaskJob;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bach
 */
public class TaskDownloader {

    private static final int DEFAULT_IMAGE_QUEUE_SIZE = 3;
    //
    private ITaskDownloaderListener listener;
    private final DownloadTask task;
    private Thread thread;

    public TaskDownloader(ITaskDownloaderListener listener, DownloadTask task) {
        this.listener = listener;
        this.task = task;
    }

    private boolean isRunning() {
        return task.isIsRunning();
    }

    private void doResume() {
        task.setIsRunning(true);
        try {
            switch (task.getStatusEnum()) {
                case No:
                case Queue:
                case Error:
                case Checking:
                    if (isRunning()) {
                        check();
                    }
                case Parsing:
                    if (isRunning()) {
                        parse();
                    }
                case Downloading:
                    if (isRunning()) {
                        download();
                    }
                case Cleaning:
                    if (isRunning()) {
                        cleaning();
                    }
//                case Done:
//                    return;
            }
        } catch (Exception ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
            task.setStatus(DownloadTask.DownloadTaskStatus.Error);
            GUIUtilities.showException(null, ex);
        }
    }

    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (bbmangadownloader.BBMangaDownloader.TEST) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                doResume();
            }
        });
        thread.start();
    }

    public void stop() {
        if (isRunning() && task.getStatusEnum() != DownloadTaskStatus.Done) {
            task.setIsRunning(false);
        }
    }

    public void resume() {
        if (!isRunning() && task.getStatusEnum() != DownloadTaskStatus.Done) {
            task.setIsRunning(true);
            start();
        }
    }

    private void check() {
        task.setStatus(DownloadTaskStatus.Checking);
        Chapter c = task.getChapter();
        System.out.println("Checking Chapter: " + c + "\t" + c.getUrl());
        File imageFolder = FileManager.getFolderForChapter(c);
        // Check Dupliate Image Folder
        if (imageFolder.exists()) {
            // Check for duplicate folder - May downloaed before
            if (!FileUtilities.isImageFolderEmpty(imageFolder)) {
                int value = GUIUtilities.showConfirmError(null,
                        "Chapter \"" + c.getDisplayName() + "\" could already been downloaded. "
                        + "Delete it and start new download?",
                        "Directory not empty", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                // Cancel
                if (value == JOptionPane.CANCEL_OPTION) {
                    task.setStatus(DownloadTask.DownloadTaskStatus.Stopped);
                    return;
                }
                FileUtilities.deleteDirector(imageFolder);
            }
        }
        imageFolder.mkdirs();
        task.setDownloadTo(imageFolder);
    }

    private void parse() throws Exception {
        Chapter c = task.getChapter();
        if (c.getImagesCount() == -1) {
            System.out.println("Parsing chapter: " + c);
            task.setStatus(DownloadTask.DownloadTaskStatus.Parsing);
            listener.updateRecord(task);

            IFacadeMangaServer usingServer = c.getManga().getServer().getMangaServer();
            List<Image> lstImg = usingServer.getAllImages(c);

            c.addImages(lstImg);
            System.out.println("\tGot " + lstImg.size() + " image(s)");
        }
    }

    private void download() throws InterruptedException {
        task.setStatus(DownloadTask.DownloadTaskStatus.Downloading);
        listener.updateRecord(task);

        Chapter c = task.getChapter();
        File imageFolder = task.getDownloadTo();
        Collection<Image> lstImg = c.getSetImage();
        System.out.println("\tSave to: " + imageFolder.getAbsolutePath());
        downloadImages(task, lstImg, imageFolder);
        // Turn-off the logger
        //MyLogger.log(lstImg, imageFolder.getParentFile(), c);
    }

    private void cleaning() throws IOException {
        task.setStatus(DownloadTask.DownloadTaskStatus.Cleaning);
        listener.updateRecord(task);

        Chapter c = task.getChapter();
        File imageFolder = task.getDownloadTo();
        System.out.println("Cleaning: " + imageFolder.getCanonicalPath());
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
        done();
    }

    private void done() {
        task.setStatus(DownloadTaskStatus.Done);
        listener.updateRecord(task);
        listener.onTaskDownloadFinish(task);
    }

    private void downloadImages(final DownloadTask task, Collection<Image> lstImg, File imageFolder) throws InterruptedException {
        ArrayList<Callable<Boolean>> listImageTask = new ArrayList<Callable<Boolean>>();
        synchronized (task) {
//            task.clearCurrentImage();
            for (Image img : lstImg) {
                if (!img.isIsDownloaded()) {
                    try {
                        File fileImage = FileManager.getFileForImage(imageFolder, img);
                        listImageTask.add(new DefaultFileDownloader(task, img, fileImage));
                    } catch (IOException ex) {
                        Logger.getLogger(TaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        MultitaskJob.doTask(DEFAULT_IMAGE_QUEUE_SIZE, listImageTask);
    }

    private class DefaultFileDownloader extends AFileDownloader {

        private Image image;

        private DefaultFileDownloader(DownloadTask task, Image image, File fileOutput) throws IOException {
            super(image.getConnection(), fileOutput);
            this.task = task;
            this.image = image;
        }
        private DownloadTask task;

        @Override
        public Boolean call() throws Exception {
            if (isRunning()) {
                return super.call();
            } else {
                return false;
            }
        }

        @Override
        public void finishFileDownload() {
            image.setIsDownloaded(true);
            task.increaseCurrentImage();
            listener.updateRecord(task);
        }
    }

    public static interface ITaskDownloaderListener {

        public void updateRecord(DownloadTask task);

        public void onTaskDownloadFinish(DownloadTask task);
    }
}
