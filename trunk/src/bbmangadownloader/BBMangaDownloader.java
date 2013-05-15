package bbmangadownloader;

import bbmangadownloader.database.WatcherMangager;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.gui.MangaDownloadGUI;
import bbmangadownloader.gui.MangaWatcherGUI;
import bbmangadownloader.gui.StartUpPannel;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.ult.OSSupport;
import bbmangadownloader.ult.ReflectionUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import nanohttpd.FileBrowserHttpdServer;

/**
 *
 * @author Bach
 */
public final class BBMangaDownloader {

    private static final String CURR_VERSION = "1.3.3";
    //
    private static final String[] APPLICATION_NAMES =
            new String[]{"BB Manga Watcher", "BB Manga Downloader"};
    private static final int MODE_WATCHER = 0;
    private static final int MODE_DOWNLOADER = 1;
    //
    private static final int MODE = MODE_DOWNLOADER;
    public static final String APPLICATION_NAME = APPLICATION_NAMES[MODE];
    public static final boolean TEST = false;
    public static FileBrowserHttpdServer BROWSER_SERVER;

    public static boolean isModeDownloader() {
        return MODE == MODE_DOWNLOADER;
    }
    // 
    private static JFrame mainFrame;
//    private static MyHttpdServer customServer;

    public static void main(String[] args) {
        if (TEST) {
            Logger.getLogger(BBMangaDownloader.class.getName()).
                    log(Level.WARNING,
                    "DEVELOPING MODE - TURN OFF BEFORE RELEASING !", (Object) null);
        }
        if (OSSupport.getOS() == OSSupport.OS.MAC_OS) {
            configForMacOS();
        }

        try {
            final JFrame startUpPanel = new JFrame("Loading...");
            //<editor-fold defaultstate="collapsed" desc="Startup Pannel">
            StartUpPannel panel = new StartUpPannel();
            startUpPanel.setUndecorated(true);
            panel.setVersion(getCurrentVersion());
            startUpPanel.add(panel);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            double width = dim.getWidth();
            double height = dim.getHeight();
            Dimension preferDimension = startUpPanel.getPreferredSize();
            double pWidth = preferDimension.getWidth();
            double pHeight = preferDimension.getHeight();
            startUpPanel.setBounds(((int) (width - pWidth)) / 2,
                    ((int) (height - pHeight)) / 2, (int) pWidth, (int) pHeight);
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
//              Un-comment line ~147
//            try {
//                initHttpServer();

//            } catch (IOException ex) {
//                Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE,
//                        "Can not create http server. Browser's Extension can not work !",
//                        ex);
//            }
            //</editor-fold>

            panel.setProgressValue(100);
            panel.setProgressString("Done");
//            Thread.sleep(1000);

            // Invoke GUI !  
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (isModeDownloader()) {
                        mainFrame = new MangaDownloadGUI();
                    } else {
                        mainFrame = new MangaWatcherGUI();
                    }
                    mainFrame.setVisible(true);
                    startUpPanel.setVisible(false);
                    startUpPanel.dispose();
                    // 2. Tray Icon
//                    loadSystemTray();
                    // 3. 
//                    customServer.setIm((IMangaInterface) mainFrame);

                    // 4. HTTPD File Browser
                    ConfigManager config = ConfigManager.getCurrentInstance();
                    if (config.isHttpdServer()) {
                        try {
                            int port = config.getHttpdServerPort();
                            File directory = new File(config.getOutputFolder());
                            BROWSER_SERVER = new FileBrowserHttpdServer(port, directory);
                        } catch (IOException ex) {
                            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
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
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.CONFIG,
                    "Config for Mac OS Done", (Object) null);
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
//    private static void loadSystemTray() {
//        bbmangadownloader.gui.SystemTray.loadSystemTray();
//    }
//
//    private static void initHttpServer() throws IOException {
//        customServer = new MyHttpdServer();
//        Logger.getLogger(BBMangaDownloader.class.getName()).
//                log(Level.FINE,
//                "Created server on port " + MyHttpdServer.HTTP_PORT, (Object) null);
//    }
}
