/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.gui.UpdateDialog;
import bbmangadownloader.manager.HttpDownloadManager;
import bbmangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//    private static final String VERSION_URL = "http://192.168.1.38/Update.xml";
    private static final int DEFAULT_TIME = 60 * 1000; // 60s
    private static Version VERSION;

    private static void doCheckVersion() throws MalformedURLException, IOException, Exception {
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
            v.setVersion(eType.select("version").text());
            v.updateUrl = eType.select("url").text();
            VERSION = v;
        }
    }

    private static void lazyLoad() throws MalformedURLException, IOException, Exception {
        synchronized (UpdateService.class) {
            if (VERSION == null || VERSION.isShouldUpdate()) {
                doCheckVersion();
            }
        }
    }

    public static Version getNewestVersion() throws MalformedURLException, IOException, Exception {
        lazyLoad();
        return VERSION;
    }

    static void loadOnStartUp() {
        new Thread(new UpdateService()).start();
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

    private static class VersionInfor implements Comparable<VersionInfor> {

        private int[] versions;

        private VersionInfor(String versionName) throws Exception {
            String[] data = versionName.split("\\.");
            if (data.length != 3) {
                throw new Exception("Version parameter wrong !");
            }

            versions = new int[3];
            for (int i = 0; i < 3; i++) {
                versions[i] = NumberUtilities.getNumberInt(data[i]);
            }
        }

        @Override
        public int compareTo(VersionInfor o) {
            int x = 0;
            for (int i = 0; i < 3; i++) {
                x = o.versions[i] - versions[i];
                if (x != 0) {
                    return x;
                }
            }
            return 0;
        }

        private String toVersionString() {
            StringBuilder sb = new StringBuilder();
            sb.append(versions[0]);
            for (int i = 1; i < versions.length; i++) {
                sb.append('.').append(versions[i]);
            }
            return sb.toString();
        }
    }

    public static class Version {

        private VersionInfor currentVersion = null;
        private Date lastUpdate = null;
        public String updateUrl = null;

        public boolean isNewestVersion() throws MalformedURLException, IOException, Exception {
            VersionInfor newVersion = new VersionInfor(BBMangaDownloader.getCurrentVersion());
//            if (currentVersion < newVersion)
            if (currentVersion.compareTo(newVersion) < 0) {
                return false;
            } else {
                return true;
            }
        }

        private boolean isShouldUpdate() {
            return (System.currentTimeMillis() - lastUpdate.getTime()) > DEFAULT_TIME;
        }

        public String getVersion() {
            return currentVersion.toVersionString();
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        private void setVersion(String text) throws Exception {
            this.currentVersion = new VersionInfor(text);
        }
    }
}
