/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.bus;

import bbmangadownloader.ult.ExceptionUtilities;
import bbmangadownloader.ult.FileUtilities;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.HtmlUtilities;
import bbmangadownloader.ult.HttpDownloadManager.MyConnection;
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
    private static int DEFAULT_STEP = 10000;
    private static int DEFAULT_CONNECT_TIMEOUT = 10000;
    private static int DEFAULT_READ_TIMEOUT = 10000;
    private static int DEFAULT_ATTEMP = 3;
    private static int DEFAULT_DEADLOCK = 10;
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

    private void saveFile(MyConnection connection, File fileOutput, int connectTimeOut, int readTimeOut) throws IOException {
        //  Option 1: Use Stream                
        FileUtilities.saveStreamToFile(connection.getInputStream(), fileOutput);
        // Option 2: Use NIO            
//        ReadableByteChannel rbc = Channels.newChannel(fileUrl.openConnection().getInputStream());
//        MyUtilities.saveChannelToFile(rbc, fileOutput);
        // Option 3: Use Apache Lib
//        FileUtils.copyURLToFile(fileUrl, fileOutput, connectTimeOut, readTimeOut);
    }

    @Override
    public Boolean call() throws Exception {
        //        System.out.println("\tDownloading: " + url + " -> " + fileOutput.getAbsolutePath());
//        GUIUtil.showLog("\tDownloading: " + GUIUtil.compressPath(url.toString())
//                + " -> " + GUIUtil.compressPath(fileOutput.getAbsolutePath()));
        boolean isDownloaded = false;
        Exception lastEx = null;
        if (!fileOutput.exists()) {
            try {
                System.out.println("\t\t\tCreating New File: " + fileOutput);
                fileOutput.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(AFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // If only can write to file += file existed!
        int connectTimeOut = DEFAULT_CONNECT_TIMEOUT;
        int readTimeOut = DEFAULT_READ_TIMEOUT;
        int timOutTryTime = 0;
        boolean useEncodeString = true;
        int caughtFileNotFoundExTime = 0;

        int counterToPreventDeadLock = 0;

        if (fileOutput.canWrite()) {
            try {
                boolean isTryAgain;
                do {
                    try {
                        System.out.println("\tDownloading from " + connection.getUrl());
                        saveFile(connection, fileOutput, connectTimeOut, readTimeOut);
                        isTryAgain = false;
                        System.out.println("\t\tDownloaded from " + connection.getUrl());
                        finishFileDownload();
                        return Boolean.TRUE;
                    } catch (SocketTimeoutException ex) {
                        //<editor-fold>
                        lastEx = ex;
                        timOutTryTime++;
                        connectTimeOut += DEFAULT_STEP;
                        readTimeOut += DEFAULT_STEP;
                        isTryAgain = true;
                        System.out.println("\tSocketTimeOutExcption, re-try (" + timOutTryTime + ") with timeout = " + connectTimeOut);
                        //</editor-fold>
                    } catch (FileNotFoundException ex) {
                        lastEx = ex;
                        if (caughtFileNotFoundExTime < 2) {
                            caughtFileNotFoundExTime++;
                            isTryAgain = true;
                            connection.URL(HtmlUtilities.encodeUrl(connection.getURL(), useEncodeString));
                            useEncodeString = !useEncodeString;
                            System.out.println("\tFileNotFoundException: " + ex.getMessage());
                        } else {
                            break;
                        }
                    } catch (IOException ex) {
                        lastEx = ex;
                        isTryAgain = true;
                        System.out.println("\tIOException: " + ex.getMessage());
                        int httpError = ExceptionUtilities.getHttpErrorCode(ex);
                        if (httpError != -1) {
                            System.out.println("\t\tHTTP Error: " + httpError);
                            if (httpError == 400) {
                                if (caughtFileNotFoundExTime < 2) {
                                    caughtFileNotFoundExTime++;
                                    isTryAgain = true;
                                    connection.URL(HtmlUtilities.encodeUrl(connection.getURL(), useEncodeString));
                                    useEncodeString = !useEncodeString;
                                    System.out.println("\tHTTP Error = 404: " + ex.getMessage());
                                } else {
                                    break;
                                }
                            } else if (httpError == 403) {
                                isTryAgain = false;
                                System.out.println("\t\t\tStop this, due to HTTP Error = 403");
//                            Logger.getLogger(AFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            // is not HTTP Error (Code >= 400, != 410,400)
                            Logger.getLogger(AFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } while (isTryAgain && timOutTryTime < DEFAULT_ATTEMP && (++counterToPreventDeadLock) < DEFAULT_DEADLOCK);
            } catch (Exception ex) {
                System.out.println("\tCan Process this type of error !");
                Logger.getLogger(AFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
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
