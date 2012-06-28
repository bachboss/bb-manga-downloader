/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.ult;

/**
 *
 * @author Bach
 */
public class OSSupport {

    private static String getOs() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        String os = getOs();
        return (os.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        String os = getOs();
        return (os.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        String os = getOs();
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
    }

    public static boolean isSolaris() {
        String os = getOs();
        return (os.indexOf("sunos") >= 0);
    }
}
