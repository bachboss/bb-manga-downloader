/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.*;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;

/**
 *
 * @author Bach
 */
public class FileUtilities {

    public static void saveChannelToFile(ReadableByteChannel rbc, File out) throws FileNotFoundException, IOException {
        System.out.println("Writting to file: " + out.getAbsolutePath());
        try (FileOutputStream fos = new FileOutputStream(out)) {
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
        }
    }

    public static void saveStreamToFile(InputStream is, File out) throws FileNotFoundException, IOException {
        System.out.println("Writting to file: " + out.getAbsolutePath());
        byte[] buffer = new byte[1024];
        int read;
        try (FileOutputStream fos = new FileOutputStream(out)) {
            while ((read = is.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.close();
        }
    }

    public static String loadFromFile(File out) throws FileNotFoundException, IOException {
        StringBuilder fileData = new StringBuilder(1000);
        try (BufferedReader reader = new BufferedReader(new FileReader(out))) {
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
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
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(data);
            fw.close();
        }
    }
}
