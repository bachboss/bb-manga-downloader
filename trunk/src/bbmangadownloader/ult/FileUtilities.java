/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.*;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

/**
 *
 * @author Bach
 */
public class FileUtilities {

    public static void saveChannelToFile(ReadableByteChannel rbc, File out) throws FileNotFoundException {
        System.out.println("Writting to file: " + out.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(out);
        try {
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void saveStreamToFile(InputStream is, File out) throws FileNotFoundException, IOException {
        System.out.println("Writting to file: " + out.getAbsolutePath());
        byte[] buffer = new byte[1024];
        int read;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            while ((read = is.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String loadFromFile(File out) throws FileNotFoundException, IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(out));
        try {
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fileData.toString();
    }

    public static String getFileNameViaUrl(URL url) {
        String s = url.getPath();
        s = s.substring(s.lastIndexOf("/") + 1);
        s.replaceAll("\\?", "_");
        return s;
    }

    public static void writeStringToFile(String data, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        try {
            fw.write(data);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static List<File> getAllSubFile(File folder, java.io.FilenameFilter filter) {
        List<File> returnValue = new ArrayList<File>();
        File[] arrFiles = folder.listFiles(filter);
        for (File f : arrFiles) {
            if (f.isDirectory()) {
                returnValue.addAll(getAllSubFile(f, filter));
            } else if (f.isFile() && !f.isHidden()) {
                returnValue.add(f);
            }
        }
        return returnValue;
    }

    public static void zipDirectory(File inDirectory) throws IOException {
        if (!inDirectory.exists()) {
            throw new IOException("Directory not existed !");
        }
        File outFolder = new File(inDirectory.getParent(), inDirectory.getName() + ".zip");

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
        BufferedInputStream in;
        byte[] data = new byte[DEFAULT_ZIP_BUFFER];
        List<File> listFile = FileUtilities.getAllSubFile(inDirectory, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.startsWith(".") || name.endsWith(".db")) {
                    return false;
                }
                return true;
            }
        });
        int folderNameLength = inDirectory.getAbsolutePath().replaceAll("\\\\", "/").length();
        for (File f : listFile) {
            in = new BufferedInputStream(new FileInputStream(f), DEFAULT_ZIP_BUFFER);
            String zipEntryName = f.getAbsolutePath().replaceAll("\\\\", "/").substring(folderNameLength + 1);
            out.putNextEntry(new ZipEntry(zipEntryName));
            int count;
            while ((count = in.read(data, 0, DEFAULT_ZIP_BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            in.close();
            out.closeEntry();
        }
        out.flush();
        out.close();
    }
    private static final int DEFAULT_ZIP_BUFFER = 1024 * 2;

    public static void deleteDirector(File folder) {
        File[] lstFile = folder.listFiles();
        for (File f : lstFile) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteDirector(f);
            }
        }
        folder.delete();
    }

    /**
     *
     * A folder is empty only if it does no contain any images file in all of
     * its sub-directory
     *
     * @param f Input File
     * @return Is File Empty
     */
    public static boolean isImageFolderEmpty(File f) {
        File[] arrFile = f.listFiles(ImageFileNameFilter.getInstance());
        if (arrFile.length > 0) {
            return false;
        }
        arrFile = f.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        for (File folder : arrFile) {
            if (!isImageFolderEmpty(folder)) {
                return false;
            }
        }
        return true;
    }
    public static final String[] EXT_IMG = new String[]{".jpeg", ".jpg", ".png"};

    static class ImageFileNameFilter implements FilenameFilter {

        public static final ImageFileNameFilter cI = new ImageFileNameFilter();

        public static ImageFileNameFilter getInstance() {
            return cI;
        }

        private ImageFileNameFilter() {
        }

        @Override
        public boolean accept(File dir, String name) {
            for (String str : EXT_IMG) {
                if (name.endsWith(str)) {
                    return true;
                }
            }
            return false;
        }
    }
}
