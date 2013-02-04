package bbmangadownloader;

import bbmangadownloader.database.WatcherMangager;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.gui.IMangaInterface;
import bbmangadownloader.gui.MangaDownloadGUI;
import bbmangadownloader.gui.MangaWatcherGUI;
import bbmangadownloader.gui.StartUpPannel;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.ult.OSSupport;
import bbmangadownloader.ult.ReflectionUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import nanohttpd.MyHttpdServer;

/**
 *
 * @author Bach
 */
public final class BBMangaDownloader {

    private static final String CURR_VERSION = "1.2.7";
    //
    private static final String[] APPLICATION_NAMES = new String[]{"BB Manga Watcher", "BB Manga Downloader"};
    private static final int MODE_WATCHER = 0;
    private static final int MODE_DOWNLOADER = 1;
    //
    private static final int MODE = MODE_DOWNLOADER;
    public static final String APPLICATION_NAME = APPLICATION_NAMES[MODE];
    public static final boolean TEST = true;

    public static boolean isModeDownloader() {
        return MODE == MODE_DOWNLOADER;
    }
    // 
    private static JFrame mainFrame;
    private static MyHttpdServer customServer;

    private BBMangaDownloader() {
    }

    public static void main(String[] args) {
        if (OSSupport.getOS() == OSSupport.OS.MAC_OS) {
            configForMacOS();
        }

        try {
            final JFrame startUpPanel = new JFrame("Loading...");
            //<editor-fold defaultstate="collapsed" desc="Startup Pannel">
            StartUpPannel panel = new StartUpPannel();
            {
                startUpPanel.setUndecorated(true);
                panel.setVersion(getCurrentVersion());
                startUpPanel.add(panel);
                {
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    double width = dim.getWidth();
                    double height = dim.getHeight();
                    Dimension preferDimension = startUpPanel.getPreferredSize();
                    double pWidth = preferDimension.getWidth();
                    double pHeight = preferDimension.getHeight();
                    startUpPanel.setBounds(((int) (width - pWidth)) / 2,
                            ((int) (height - pHeight)) / 2, (int) pWidth, (int) pHeight);
                }
            }
            startUpPanel.setVisible(true);
            //</editor-fold>
            panel.setProgressString("Loading Configuration");
            panel.setProgressValue(20);
            ConfigManager.loadOnStartUp();

            //
            panel.setProgressString("Setting Look & Feel");
            panel.setProgressValue(35);
            //<editor-fold defaultstate="collapsed" desc="Set Look&Feel">
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
            //</editor-fold>

            panel.setProgressString("Loading Update Service");
            panel.setProgressValue(55);
            //<editor-fold defaultstate="collapsed" desc="Update Service">
            if (ConfigManager.getCurrentInstance().isCheckUpdateOnStartUp()) {
                UpdateService.loadOnStartUp();
            }
            //</editor-fold>

            panel.setProgressString("Loading Data");
            panel.setProgressValue(75);
            ServerManager.loadServer();
            // Load Watcher...
            if (!isModeDownloader()) {
//                MangaManager.lazyLoadAllMangas();
                WatcherMangager.loadOnStartup();
            }

            panel.setProgressValue(85);
            panel.setProgressString("Create http listenner");
            //<editor-fold defaultstate="collapsed" desc="Browser's Extension handler">

            try {
                initHttpServer();
            } catch (IOException ex) {
                System.out.println("Can not create http server. Browser's Extension can not work !");
                // TODO: Handler code here !
            }
            //</editor-fold>

            panel.setProgressValue(100);
            panel.setProgressString("Done");
//            Thread.sleep(1000);

            // Invoke GUI !  
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // 1. Main GUI !
                    if (isModeDownloader()) {
                        mainFrame = new MangaDownloadGUI();
                    } else {
                        mainFrame = new MangaWatcherGUI();
                    }
                    mainFrame.setVisible(true);
                    startUpPanel.setVisible(false);
                    startUpPanel.dispose();
                    // 2. Tray Icon
                    loadSystemTray();
                    // 3. 
                    customServer.setIm((IMangaInterface) mainFrame);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static URL getResource(String resourceUrl) {
        return BBMangaDownloader.class.getClassLoader().getResource(resourceUrl);
    }

    public static java.awt.Image getApplicationIcon() {
        URL imageURL = getResource("bbmangadownloader/resources/icon/icon.png");
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

    public static String getCurrentVersion() {
        return CURR_VERSION;
    }

    public static synchronized void setVisibleMainWindows(boolean flag) {
        mainFrame.setVisible(flag);
    }

    private static void loadSystemTray() {
        bbmangadownloader.gui.SystemTray.loadSystemTray();
    }

    private static void initHttpServer() throws IOException {
        customServer = new MyHttpdServer();
        System.out.println("Created server on port " + MyHttpdServer.HTTP_PORT + ".");
    }
}
