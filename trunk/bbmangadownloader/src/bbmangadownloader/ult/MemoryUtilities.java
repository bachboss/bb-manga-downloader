/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

/**
 *
 * @author Bach
 */
public class MemoryUtilities {

    private static final Runtime runTime = Runtime.getRuntime();

    public static long getFreeMemory() {
        return runTime.totalMemory() - runTime.freeMemory();
    }

    public static String getFreeMemoryString() {
        return convertByteToString(getFreeMemory());
    }

    public static String convertByteToString(long noB) {
        long x = 1024;
        if (noB < x) {
            return noB + "B";
        } else if (noB < (x *= 1024)) {
            return noB + "KB";
        } else if (noB < (x *= 1024)) {
            return noB + "MB";
        } else if (noB < (x *= 1024)) {
            return noB + "GB";
        } else {
            return "Too large ?";
        }
    }
}
