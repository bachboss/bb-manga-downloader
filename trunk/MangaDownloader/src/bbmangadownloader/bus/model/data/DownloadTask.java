/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus.model.data;

import mangadownloader.entity.Chapter;

/**
 *
 * @author Bach
 */
public class DownloadTask {

    public static final int STATUS_NO = 0;
    public static final int STATUS_STOPPED = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_RUNNING = 3;
    public static final int STATUS_PARSING = 4;
    private static final String[] STATUS_ALL = new String[]{
        "", "Stoped", "Error", "Running", "Parsing"
    };
    private Chapter c;
    private int currentImage;
    private int status;

    public DownloadTask(Chapter c) {
        this.c = c;
    }

    public Chapter getChapter() {
        return c;
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setStatus(int status) {
        if (status >= 0 && status < 5) {
            this.status = status;
        } else {
            this.status = STATUS_ERROR;
            System.out.println("Error Here !");
        }
    }

    public String getStatus() {
        switch (status) {
            case (STATUS_PARSING): {
                if (currentImage != c.getPagesCount()) {
                    return String.format("@ (%.2f", (((float) currentImage) / c.getPagesCount() * 100)) + "%)";
                } else {
                    return "100%";
                }
            }
            case (STATUS_RUNNING):
                if (currentImage != c.getImagesCount()) {
                    return String.format("˅ (%.2f", (((float) currentImage) / c.getImagesCount() * 100)) + "%)";
                } else {
                    return "˅ (100%)";
                }
            default:
                return STATUS_ALL[status];
        }

    }

    public void clearCurrentImage() {
        this.currentImage = 0;
    }

    public void increaseCurrentImage() {
        this.currentImage++;
    }
}
