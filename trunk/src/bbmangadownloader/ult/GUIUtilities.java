/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.awt.Component;
import java.awt.Desktop;
import java.net.URI;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Bach
 */
public class GUIUtilities {

    private static final DecimalFormat DECIMAL_FORMATER = new DecimalFormat("#.#");

    public static String getStringFromFloat(float f) {
        return DECIMAL_FORMATER.format(f);
    }

    public static void showDialog(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text);
    }

    public static void showError(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showLog(String text) {
        if (bbmangadownloader.gui.MangaDownloadGUI.lblOutput != null) {
            bbmangadownloader.gui.MangaDownloadGUI.lblOutput.setText(text);
        }
    }

    public static String compressPath(String path) {
        if (path.length() > 40) {
            // get 10 first character and 25 last character;
            return path.substring(0, 10) + "..." + path.substring(path.length() - 25);
        } else {
            return path;
        }
    }

    public static boolean openLink(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(url));
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        }
        return false;
    }
}
