/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.ult;

import java.io.File;
import java.net.MalformedURLException;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;

/**
 *
 * @author Bach
 */
public class FileManager {

    private static File downloadToFolder;
    private static File logFile;
    //
    private static final String[] FIX_CHARACTERS = new String[]{
        "\\\\", "/", "\\:", "\\*", "\\?", "\"", "<", ">", "\\|", "\\.\\."
    };

    public static void setDownloadFolder(File f) {
        System.out.println("Saved Download folder to " + f.getAbsolutePath());
        downloadToFolder = f;
    }

    public static void setLogFile(File f) {
        logFile = f;
    }

    public static File getDownloadToFolder() {
        return downloadToFolder;
    }

    public static File getLogFile() {
        return logFile;
    }

    private static String normalizeFileName(String fileName) {
        String s = fileName;
        for (String str : FIX_CHARACTERS) {
            s = s.replaceAll(str, "_");
        }
        return s;
    }

    private static String getWithNumber(String text, int number) {
        return new StringBuilder(text).insert(text.indexOf("."), "-" + number).toString();
    }

    public static File getFolderForChapter(Chapter c) {
        File f = new File(new File(downloadToFolder, normalizeFileName(c.getManga().getMangaName())),
                normalizeFileName(c.getDisplayName()));
        f.mkdirs();
        return f;
    }

    public static File getFileForImage(File folderOutput, Image img) throws MalformedURLException {
        File fileImage = new File(folderOutput,
                String.format("%03d-%s", img.getImgOrder(),
                normalizeFileName(MyUtilities.getFileNameViaUrl(img.getURL()))));
//        String fileUrl = img.getURL().getFile();
//        File fileImage = new File(folderOutput, img.getImgOrder() + fileUrl.substring(fileUrl.lastIndexOf('.')));
        String fName = fileImage.getAbsolutePath();
        // Add Extension
        if (!fName.contains(".")) {
            fName = fName + ".jpg";
            fileImage = new File(fName);
        }
//        int attemp = 0;
//        while (fileImage.exists()) {
//            fileImage = new File(getWithNumber(fName, ++attemp));
//        }

        return fileImage;
    }

    public static File getFileInFolder(File folder, String fileName) {
        return new File(folder, normalizeFileName(fileName));
    }
}
