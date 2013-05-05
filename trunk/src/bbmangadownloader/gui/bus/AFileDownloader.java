/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.bus;

import bbmangadownloader.manager.HttpDownloadManager.MyConnection;
import bbmangadownloader.ult.ExceptionUtilities;
import bbmangadownloader.ult.FileUtilities;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.HtmlUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public abstract class AFileDownloader implements Runnable, Callable<Boolean>, IFileDownloader {

    /**
     * This class is abstract class, used to download 1 file at a time
     */
    //
    private MyConnection connection;
    private File fileOutput;

    public AFileDownloader(MyConnection connection, File fileOutput) {
        this.connection = connection;
        this.fileOutput = fileOutput;
    }

    public File getFileOutput() {
        return fileOutput;
    }

    public void setFileOutput(File fileOutput) {
        this.fileOutput = fileOutput;
    }

    private void saveFile(MyConnection connection, File fileOutput) throws Exception {
        //  Option 1: Use Stream                
        FileUtilities.saveStreamToFile(connection.getInputStreamOpen(), fileOutput);
        // Option 2: Use NIO            
//        ReadableByteChannel rbc = Channels.newChannel(fileUrl.openConnection().getInputStream());
//        MyUtilities.saveChannelToFile(rbc, fileOutput);
        // Option 3: Use Apache Lib
//        FileUtils.copyURLToFile(fileUrl, fileOutput, connectTimeOut, readTimeOut);
    }

    @Override
    public Boolean call() throws Exception {
        if (bbmangadownloader.BBMangaDownloader.TEST
                && connection.getUrl().contains("localhost:80")) {
            Thread.sleep(2000);
            finishFileDownload();
            return true;
        }
        //        System.out.println("\tDownloading: " + url + " -> " + fileOutput.getAbsolutePath());
//        GUIUtil.showLog("\tDownloading: " + GUIUtil.compressPath(url.toString())
//                + " -> " + GUIUtil.compressPath(fileOutput.getAbsolutePath()));
        boolean isDownloaded = false;
        Exception lastEx = null;
        if (!fileOutput.exists()) {
            try {
                fileOutput.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(AFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Can not create file: " + fileOutput.getAbsolutePath());
            }
        }
        // If only can write to file += file existed!

        if (fileOutput.canWrite()) {
            try {
                saveFile(connection, fileOutput);
                System.out.println("\t\tDownloaded from " + connection.getUrl());
                finishFileDownload();
                return Boolean.TRUE;
            } catch (Exception ex) {
                lastEx = ex;
            }
        }

        if (!isDownloaded) {
            fileOutput.delete();
            throw lastEx;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public void run() {
        try {
            call();
        } catch (final Exception ex) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    GUIUtilities.showException(null, ex);
                    Logger.getLogger(AFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        }
    }
}
