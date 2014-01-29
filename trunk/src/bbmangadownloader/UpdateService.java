/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.gui.UpdateDialog;
import bbmangadownloader.manager.HttpDownloadManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Bach
 */
public class UpdateService implements Runnable {

    private UpdateService() {
    }
    //
    private static final String VERSION_URL = "https://dl.dropbox.com/u/5695926/Manga%20Download%20Tool/Update.xml";
    private static final int DEFAULT_TIME = 60 * 1000; // 60s
    private static Version VERSION;

    private static void doCheckVersion() throws MalformedURLException, IOException {
        synchronized (UpdateService.class) {
            Document doc = HttpDownloadManager.createConnection(VERSION_URL).getDocument();
            Version v = new Version();
            Element eType;
            if (BBMangaDownloader.isModeDownloader()) {
                eType = doc.select("downloader").first();
            } else {
                eType = doc.select("watcher").first();
            }
            v.lastUpdate = new Date();
            v.version = eType.select("version").text();
            v.updateUrl = eType.select("url").text();
            VERSION = v;
        }
    }

    private static void lazyLoad() throws MalformedURLException, IOException {
        synchronized (UpdateService.class) {
            if (VERSION == null || VERSION.isShouldUpdate()) {
                doCheckVersion();
            }
        }
    }

    public static Version getNewestVersion() throws MalformedURLException, IOException {
        lazyLoad();
        return VERSION;
    }

    static void loadOnStartUp() {
        SwingUtilities.invokeLater(new UpdateService());
    }

    @Override
    public void run() {
        try {
            Version version = getNewestVersion();
            if (version != null && !version.isNewestVersion()) {
                new UpdateDialog(null, false).setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static class Version {

        public String version = null;
        private Date lastUpdate = null;
        public String updateUrl = null;

        public boolean isNewestVersion() throws MalformedURLException, IOException {
            String currentVersion = BBMangaDownloader.getCurrentVersion();
            if (currentVersion.compareTo(version) < 0) {
                return false;
            } else {
                return true;
            }
        }

        private boolean isShouldUpdate() {
            return (System.currentTimeMillis() - lastUpdate.getTime()) > DEFAULT_TIME;
        }

        public String getVersion() {
            return version;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }
    }
}
