/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.model.data;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.gui.bus.TaskDownloader;
import java.io.File;

/**
 *
 * @author Bach
 */
public class DownloadTask {

    private Chapter c;
    private int currentImage;
    private DownloadTaskStatus status = DownloadTaskStatus.No;
    private File downloadTo;
    private TaskDownloader downloader;
    private boolean isRunning = false;

    public DownloadTask(Chapter c) {
        this.c = c;
    }

    public Chapter getChapter() {
        return c;
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public File getDownloadTo() {
        return downloadTo;
    }

    public void setDownloadTo(File downloadTo) {
        this.downloadTo = downloadTo;
    }

    public void setStatus(DownloadTaskStatus status) {
        this.status = status;
    }

    public synchronized String getStatus() {
        if (status == DownloadTaskStatus.No) {
            return status.getStatus();
        }
        String s;
        if (status == DownloadTaskStatus.Downloading) {
            int numberOfImage = c.getImagesCount();
            if (currentImage == 0) {
                s = ("▼ (0%)");
            } else if (currentImage == c.getImagesCount()) {
                s = ("▼ (100%)");
            } else {
                s = String.format("▼ (%.2f", (((float) currentImage) / numberOfImage * 100)) + "%)";
            }
        } else {
            s = status.getStatus();
        }

        if (isRunning) {
            return s;
        } else {
            return ("Pause: " + s);
        }
    }

    public DownloadTaskStatus getStatusEnum() {
        return status;
    }

    public void clearCurrentImage() {
        this.currentImage = 0;
    }

    public void increaseCurrentImage() {
        this.currentImage++;
    }

    public TaskDownloader getDownloader() {
        return downloader;
    }

    public void setDownloader(TaskDownloader downloader) {
        this.downloader = downloader;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public static enum DownloadTaskStatus {

        No(0), Checking(1), Parsing(2), Downloading(3), Cleaning(4),
        Done(5), Error(6), Stopped(7);

        private DownloadTaskStatus(int id) {
            this.id = id;
        }
        private int id;

        private String getStatus() {
            return STATUS_ALL[id];
        }

        @Override
        public String toString() {
            return getStatus();
        }
        private static final String[] STATUS_ALL = new String[]{
            "", "Checking", "Parsing", "Downloading", "Cleaning",
            "Done", "Error", "Stopped"
        };
    }
}
