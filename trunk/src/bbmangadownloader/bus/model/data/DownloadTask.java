/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.model.data;

import bbmangadownloader.entity.Chapter;
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

    public String getStatus() {
        if (status == DownloadTaskStatus.Parsing) {
            int numberOfImage = Math.max(c.getPagesCount(), c.getImagesCount());
            if (currentImage != c.getPagesCount()) {
                return String.format("◊ (%.2f", (((float) currentImage) / numberOfImage * 100)) + "%)";
            } else {
                return "100%";
            }
        } else if (status == DownloadTaskStatus.Downloading) {
            int numberOfImage = Math.max(c.getPagesCount(), c.getImagesCount());
//                System.out.println("numberOfImage = " + numberOfImage);
            if (currentImage != c.getImagesCount()) {
                return String.format("▼ (%.2f", (((float) currentImage) / numberOfImage * 100)) + "%)";
            } else {
                this.status = DownloadTaskStatus.Done;
            }
        }
        return status.getStatus();
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

    public static enum DownloadTaskStatus {

        No(0), Stopped(1), Error(2), Downloading(3), Parsing(4), Done(5);

        private DownloadTaskStatus(int id) {
            this.id = id;
        }
        private int id;

        private String getStatus() {
            return STATUS_ALL[id];
        }
        private static final String[] STATUS_ALL = new String[]{
            "", "Stopped", "Error", "Running", "Parsing", "Done"
        };
    }
}
