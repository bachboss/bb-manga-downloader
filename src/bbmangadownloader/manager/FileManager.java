/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.ult.FileUtilities;
import com.google.code.regexp.NamedPattern;
import java.io.File;
import java.net.MalformedURLException;

/**
 *
 * @author Bach
 */
public class FileManager {

    private static File downloadToFolder;
    private static File logFile;
    //    
    private static final NamedPattern PATTERN;
    private static final String PATTERN_STR;

    static {
        String[] FIX_CHARACTERS = new String[]{
            "\\\\", "/", "\\:", "\\*", "\\?", "\"", "<", ">", "\\|", "\\.\\."
        };
        StringBuilder sb = new StringBuilder("[");
        for (String s : FIX_CHARACTERS) {
            sb.append(s);
        }
        sb.append(']');
        PATTERN_STR = sb.toString();
        PATTERN = NamedPattern.compile(PATTERN_STR);
    }

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

//    private static String getWithNumber(String text, int number) {
//        return new StringBuilder(text).insert(text.indexOf("."), "-" + number).toString();
//    }
    public static File getFolderForChapter(Chapter c) {
        String folderName = normalizeFileName(c.getDisplayName());
        if (folderName.isEmpty()) {
            folderName = c.getManga().getMangaName() + " - " + c.getChapterNumber();
        }

        File f = new File(new File(downloadToFolder, normalizeFileName(c.getManga().getMangaName())),
                folderName);
        f.mkdirs();
        return f;
    }

    public static File getFileForImage(File folderOutput, Image img) throws MalformedURLException {
        File fileImage = new File(folderOutput,
                String.format("%03d-%s", img.getImgOrder(),
                normalizeFileName(FileUtilities.getFileNameViaUrl(img.getURL()))));
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

    private static String normalizeFileName(String fileName) {
        return PATTERN.matcher(fileName).replaceAll("_");
    }

    public static File getFileInFolder(File folder, String fileName) {
        return new File(folder, normalizeFileName(fileName));
    }

//    public static void main(String[] args) {
//        String s = "Ch.202: King's Crest for Dummies";
//        System.out.println(normalizeFileName(s));
//    }
}
