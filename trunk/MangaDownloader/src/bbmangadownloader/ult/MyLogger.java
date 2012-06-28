/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.ult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.gui.MangaDownloadGUI;

/**
 *
 * @author Bach
 */
public class MyLogger {

    private static SimpleDateFormat DEFAULT_FILE_NAME_FORMAT;
    private static SimpleDateFormat DEFAULT_TIME_STAMP_FORMAT;
    private static String DEFAULT_OUTPUT_PATH = "log";

    static {
        DEFAULT_TIME_STAMP_FORMAT = new SimpleDateFormat("HH:mm:ss");
        DEFAULT_FILE_NAME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    }
    private static MyLogger cI = new MyLogger();
    //
    private File fileOut;
    private FileWriter currentWritter;

    public MyLogger() {
        try {
            fileOut = new File(DEFAULT_OUTPUT_PATH, DEFAULT_FILE_NAME_FORMAT.format(new Date()) + ".txt");
            File dir = new File(DEFAULT_OUTPUT_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            currentWritter = new FileWriter(fileOut);
        } catch (IOException ex) {
            Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeToFile(String str) {
        try {
            currentWritter.write(DEFAULT_TIME_STAMP_FORMAT.format(new Date()) + ':' + str + "\n");
            currentWritter.flush();
        } catch (IOException ex) {
            Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void log(String str) {
        cI.writeToFile(str);
    }

    public static void log(List<Image> lstImg, File outputFolder, Chapter c) {
        MyLogger.log(c + "\t" + c.getUrl());
        FileWriter fw = null;
        try {
            fw = new FileWriter(FileManager.getFileInFolder(outputFolder, c.getDisplayName() + ".txt"));

            for (Image i : lstImg) {
                fw.write(i + "\n");
                MyLogger.log("\t" + i);
            }
        } catch (IOException ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
            try {
                MyLogger.log(MyUtilities.getStacktrace(ex));
            } catch (Exception ex2) {
                Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyLogger.log("----------------------------------------------------------------------------------------------------");
    }
}
