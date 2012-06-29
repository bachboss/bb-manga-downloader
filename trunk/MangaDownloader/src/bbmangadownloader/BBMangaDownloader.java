package bbmangadownloader;

import bbmangadownloader.cache.CacheLoader;
import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.gui.MangaWatcherGUI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Bach
 */
public class BBMangaDownloader {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConfigManager.loadOnStartUp();
        ServerManager.loadServer();
        // TODO: Remove later;
//        CacheLoader.loadMangas();

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MangaWatcherGUI().setVisible(true);
            }
        });
    }
}
