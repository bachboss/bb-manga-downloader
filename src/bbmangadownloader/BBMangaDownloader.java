package bbmangadownloader;

import bbmangadownloader.database.WatcherMangager;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.gui.MangaDownloadGUI;
import bbmangadownloader.gui.MangaWatcherGUI;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.ult.OSSupport;
import bbmangadownloader.ult.ReflectionUtilities;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 *
 * @author Bach
 */
public class BBMangaDownloader {

    public static final String APPLICATION_NAME = "BB Manga Watcher";
    //
    private static final int MODE_WATCHER = 0;
    private static final int MODE_DOWNLOADER = 1;
    //
    private static final int MODE = 1;

    public static void main(String[] args) {
        if (OSSupport.getOS() == OSSupport.OS.MAC_OS) {
            configForMacOS();
        }

        try {
            ConfigManager.loadOnStartUp();
            JFrame startUpPanel = new JFrame("Loading...");
            {
                startUpPanel.setUndecorated(true);
                JLabel label = new JLabel("Version: " + ConfigManager.getCurrentInstance().getCurrentVersion());
                Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
                label.setFont(font);
                startUpPanel.add(label);
                {
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    double width = dim.getWidth();
                    double height = dim.getHeight();
                    int pWidth = 400;
                    int pHeight = 150;
                    label.setSize(pWidth, pHeight);
                    label.setVerticalTextPosition(JLabel.CENTER);
                    label.setHorizontalTextPosition(JLabel.CENTER);
                    startUpPanel.setBounds(((int) (width - pWidth)) / 2, ((int) (height - pHeight)) / 2, pWidth, pHeight);
                }
            }
            startUpPanel.setVisible(true);
            
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
            ServerManager.loadServer();
            // Load Watcher...
            if (MODE == MODE_WATCHER) {
//                MangaManager.lazyLoadAllMangas();
                WatcherMangager.loadOnStartup();
            }
            // Invoke GUI !
            startUpPanel.setVisible(false);
            startUpPanel.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (MODE == MODE_WATCHER) {
                        new MangaWatcherGUI().setVisible(true);
                    } else {
                        new MangaDownloadGUI().setVisible(true);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static java.awt.Image getApplicationIcon() {
        URL imageURL = BBMangaDownloader.class.getClassLoader().getResource("bbmangadownloader/resources/icon/icon.png");
        java.awt.Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
        return image;
    }

    private static void configForMacOS() {

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", APPLICATION_NAME);
        try {
            Class classApplication = Class.forName("com.apple.eawt.Application");
            Object application = ReflectionUtilities.invokeStaticMethod(
                    ReflectionUtilities.getStaticMethod(classApplication, "getApplication"));
            java.awt.Image image = getApplicationIcon();
            ReflectionUtilities.invokeMethod(
                    application,
                    ReflectionUtilities.getMethod(
                    classApplication, "setDockIconImage", java.awt.Image.class),
                    image);
            System.out.println("Done !");
        } catch (Exception ex) {
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
