/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mangadownloader.config.ConfigManager;

/**
 *
 * @author Bach
 */
public class MangaDownloaderStartPoint {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
        }


        ConfigManager.loadOnStartUp();


        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MangaDownloadGUI().setVisible(true);
            }
        });

    }
}
