/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.*;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;

/**
 *
 * @author Bach
 */
public class MyUtilities {

    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

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

    public static String getFileNameViaUrl(URL url) {
        String s = url.getPath();
        s = s.substring(s.lastIndexOf("/") + 1);
        s.replaceAll("\\?", "_");
        return s;
    }

    public static String getStacktrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String getStringFromFloat(float f) {
        return formatter.format(f);
    }
    private static final DecimalFormat formatter = new DecimalFormat("#.#");
}
