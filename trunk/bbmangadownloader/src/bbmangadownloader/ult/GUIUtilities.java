/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

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

    public static void showException(Component parent, Exception ex) {
        ErrorInfo info = new ErrorInfo("Exception", "Exception", null, "Exception",
                ex, Level.ALL, null);
        JXErrorPane.showDialog(parent, info);
    }

    public static void showError(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showLog(String text) {
        if (bbmangadownloader.gui.MangaDownloadGUI.lblOutput != null) {
            bbmangadownloader.gui.MangaDownloadGUI.lblOutput.setText(text);
        }
    }

    /**
     * Brings up a dialog where the number of choices is determined by the
     * <code>optionType</code> parameter, where the
     * <code>messageType</code> parameter determines the icon to display. The
     * <code>messageType</code> parameter is primarily used to supply a default
     * icon from the Look and Feel.
     *
     * @param parentComponent determines the <code>Frame</code> in which the
     * dialog is displayed; if <code>null</code>, or if
     * the <code>parentComponent</code> has no <code>Frame</code>, a        default <code>Frame</code> is used.
     * @param message the <code>Object</code> to display
     * @param title the title string for the dialog
     * @param optionType an integer designating the options available on the
     * dialog: <code>YES_NO_OPTION</code>, <code>YES_NO_CANCEL_OPTION</code>,        or <code>OK_CANCEL_OPTION</code>
     * @param messageType an integer designating the kind of message this is;
     * primarily used to determine the icon from the pluggable Look and
     * Feel: <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     * <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,        or <code>PLAIN_MESSAGE</code>
     * @return an integer indicating the option selected by the user
     * @exception HeadlessException if
     * <code>GraphicsEnvironment.isHeadless</code> returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static int showConfirmError(Component parent, String message, String title, int optionType, int messageType) {
        int value = JOptionPane.showConfirmDialog(parent, message, title, optionType, messageType);
        return value;
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

    public static boolean isPopup(MouseEvent evt) {
        return SwingUtilities.isRightMouseButton(evt) || evt.isPopupTrigger();
    }
}
