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

    private static final int DEFAULT_IMAGE_QUEUE_SIZE = ConfigManager.
            getCurrentInstance().getMaxiumDownloadImage();
    //
    private final ITaskDownloaderListener listener;
    private final DownloadTask task;
    private Thread thread;

    public TaskDownloader(ITaskDownloaderListener listener, DownloadTask task) {
        this.listener = listener;
        this.task = task;
    }

    public void start() {
        check();
    }

    public void check() {
        if (isStart()) {
            task.setStatus(DownloadTaskStatus.Checking);
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
                    doCheck();
                }
            });
            thread.start();
        }
    }

    boolean isTryStop = false;

    public void tryStop() {
        isTryStop = true;
    }

    public boolean isStart() {
        DownloadTaskStatus status = task.getStatusEnum();
        return (status == DownloadTaskStatus.No || status == DownloadTaskStatus.Queue);
    }

    public boolean isStop() {
        DownloadTaskStatus status = task.getStatusEnum();
        return (status == DownloadTaskStatus.Checking
                || status == DownloadTaskStatus.Parsing
                || status == DownloadTaskStatus.Downloading);
    }

    private void stop() {
        if (isStop()) {
            doStop();
        }
    }

    public boolean isResume() {
        return (task.getStatusEnum() == DownloadTaskStatus.Stopped
                || task.getStatusEnum() == DownloadTaskStatus.Error);

    }

    public void resume() {
        if (isResume()) {
            doResume();
        }
    }

    public boolean isQueue() {
        return task.getStatusEnum() == DownloadTaskStatus.No;
    }

    private void clean() throws IOException {
        if (task.getStatusEnum() == DownloadTaskStatus.Downloading) {
            doClean();
        }
    }

    public void queue() {
        DownloadTaskStatus status = task.getStatusEnum();
        if (status == DownloadTaskStatus.No) {
            doQueue();
        }
    }

    public void waitForResume() {
        if (isTryStop) {
            isTryStop = false;
            while (task.getStatusEnum() == DownloadTaskStatus.Stopped) {
                Thread.yield();
            }
        }
    }

    // Private State Machine
    private void doResume() {
        isTryStop = false;
        try {
            switch (task.getLastStatusEnum()) {
                case Checking: {
                    doCheck();
                    break;
                }
                case Parsing: {
                    doParse();
                    break;
                }
                case Downloading: {
                    doDownload();
                    break;
                }
                case Error: {
                    start();
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MangaDownloadGUI.class
                    .getName()).log(Level.SEVERE, null, ex);
            task.setStatus(DownloadTask.DownloadTaskStatus.Error);
            GUIUtilities.showException(null, ex);
        }
    }

    private void doCheck() {
        task.setStatus(DownloadTaskStatus.Checking);
        //<editor-fold defaultstate="collapsed" desc="Do business Logic">
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
        //</editor-fold>
        doParse();
    }

    private void doQueue() {
        task.setStatus(DownloadTaskStatus.Queue);
    }

    private void doParse() {
        try {
            task.setStatus(DownloadTask.DownloadTaskStatus.Parsing);
            Chapter c = task.getChapter();
            if (c.getImagesCount() == -1) {
                System.out.println("Parsing chapter: " + c);
                listener.updateRecord(task);

                IFacadeMangaServer usingServer = c.getManga().getServer().getMangaServer();
                List<Image> lstImg = usingServer.getAllImages(c);

                c.addImages(lstImg);
                System.out.println("\tGot " + lstImg.size() + " image(s)");
            }
            doDownload();
        } catch (Exception ex) {
            doError();
            Logger.getLogger(TaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doDownload() {
        try {
            task.setStatus(DownloadTask.DownloadTaskStatus.Downloading);
            listener.updateRecord(task);
            Chapter c = task.getChapter();
            File imageFolder = task.getDownloadTo();
            Collection<Image> lstImg = c.getSetImage();
            System.out.println("\tSave to: " + imageFolder.getAbsolutePath());
            downloadImages(task, lstImg, imageFolder);
            // Turn-off the logger
            //MyLogger.log(lstImg, imageFolder.getParentFile(), c);
        } catch (InterruptedException ex) {
            doError();
            Logger
                    .getLogger(TaskDownloader.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doError() {
        task.setStatus(DownloadTaskStatus.Error);
        isDownloadImages = false;
    }

    private void doClean() throws IOException {
        task.setStatus(DownloadTaskStatus.Cleaning);
        listener.updateRecord(task);

        Chapter c = task.getChapter();
        File imageFolder = task.getDownloadTo();
        System.out.println("Cleaning: " + imageFolder.getCanonicalPath());
        ConfigManager config = (ConfigManager.getCurrentInstance());
        if (config.isGenerateHtml()) {
            HtmlUtilities.doGenerate(c.getDisplayName(), imageFolder, config.isGenerateHtmlManifest());
        }
        if (config.isZip()) {
            FileUtilities.zipDirectory(imageFolder);
            if (config.isDeleteAfterZip()) {
                FileUtilities.deleteDirector(imageFolder);
            }
        }
        doDone();
    }

    private void doDone() {
        task.setStatus(DownloadTaskStatus.Done);
        listener.updateRecord(task);
        listener.onTaskDownloadFinish(task);
    }

    private boolean isDownloadImages = false;

    private void downloadImages(final DownloadTask task, Collection<Image> lstImg, File imageFolder) throws InterruptedException {
        if (isDownloadImages) {
        } else {
            isDownloadImages = true;
            ArrayList<Callable<Boolean>> listImageTask = new ArrayList<Callable<Boolean>>();
            synchronized (task) {
//            task.clearCurrentImage();
                for (Image img : lstImg) {
                    if (!img.isIsDownloaded()) {
                        try {
                            File fileImage = FileManager.getFileForImage(imageFolder, img);
                            listImageTask.add(new DefaultFileDownloader(task, img, fileImage));

                        } catch (IOException ex) {
                            Logger.getLogger(TaskDownloader.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            MultitaskJob.doTask(DEFAULT_IMAGE_QUEUE_SIZE, listImageTask);
        }
    }

    private void doStop() {
        task.setStatus(DownloadTaskStatus.Stopped);

    }

    private class DefaultFileDownloader extends AFileDownloader {

        private final Image image;

        private DefaultFileDownloader(DownloadTask task, Image image, File fileOutput) throws IOException {
            super(image.getConnection(), fileOutput);
            this.task = task;
            this.image = image;
        }
        private final DownloadTask task;

        @Override
        public Boolean call() throws Exception {
            while (true) {
                if (task.getStatusEnum() == DownloadTaskStatus.Downloading) {
                    return super.call();
                } else {
                    Thread.yield();
                }
            }
        }

        @Override
        public void finishFileDownload() {
            image.setIsDownloaded(true);
            task.increaseCurrentImage();
            listener.updateRecord(task);
            if (task.isFinish()) {
                try {
                    doClean();
                } catch (IOException ex) {
                    doError();
                    Logger.getLogger(TaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static interface ITaskDownloaderListener {

        public void updateRecord(DownloadTask task);

        public void onTaskDownloadFinish(DownloadTask task);
    }
}
