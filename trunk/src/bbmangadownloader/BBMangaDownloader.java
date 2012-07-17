package bbmangadownloader;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.database.MangaManager;
import bbmangadownloader.database.WatcherMangager;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.gui.MangaWatcherGUI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Bach
 */
public class BBMangaDownloader {

    public static void main(String[] args) {
        JDialog startUpPanel = new JDialog();
        startUpPanel.add(new JLabel("Application Loading..."));
        startUpPanel.pack();
        startUpPanel.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConfigManager.loadOnStartUp();
        ServerManager.loadServer();
        // Load Database....
        MangaManager.loadOnStartup();
        WatcherMangager.loadOnStartup();
        // Invoke GUI !
        startUpPanel.setVisible(false);
        startUpPanel.dispose();

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
//                new MangaDownloadGUI().setVisible(true);
                new MangaWatcherGUI().setVisible(true);
            }
        });
    }
}
