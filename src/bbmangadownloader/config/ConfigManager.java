/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.config;

import bbmangadownloader.ult.FileManager;
import bbmangadownloader.ult.HttpDownloadManager;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class ConfigManager {

    private static ConfigManager cI;
    private Properties softwareConfig;
    private static final String CONFIG_PATH = "config.properties";

    private ConfigManager() {
        softwareConfig = new Properties();
    }

    public static ConfigManager getCurrentInstance() {
        return cI;
    }

    public static void loadOnStartUp() {
        cI = new ConfigManager();

        Properties p = cI.softwareConfig;
        try {
            File configFile = new File(CONFIG_PATH);
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            p.load(new FileInputStream(CONFIG_PATH));
        } catch (Exception ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        synchronized (cI) {
            // Load Proxy
            if (cI.getIsUsingProxy()) {
                HttpDownloadManager.getCurrentInstance().setProxy(cI.getProxyAddress(), cI.getProxyPort());
            }
            // Load Save Path
            {
                String outputFolder = cI.getOutputFolder();
                if (outputFolder != null) {
                    FileManager.setDownloadFolder(new File(outputFolder));
                }
            }
        }
    }

    public void save() throws FileNotFoundException, IOException {
        softwareConfig.store(new FileOutputStream(CONFIG_PATH), null);
    }

    public String getProperty(String propertyName) {
        return softwareConfig.getProperty(propertyName);
    }

    public boolean getBooleanProperty(String propertyName) {
        return ConfigManager.getYesNo(softwareConfig.getProperty(propertyName));
    }

    public void setProperty(String propertyName, String value) {
        softwareConfig.setProperty(propertyName, value);
    }

    public String getProxyAddress() {
        return getProperty("proxyAddress");
    }

    public void setProxyAddress(String address) {
        setProperty("proxyAddress", address);
    }

    public int getProxyPort() {
        String port = getProperty("proxyPort");
        if (port == null) {
            return 0;
        } else {
            try {
                return Integer.parseInt(port);
            } catch (Exception ex) {
                return 0;
            }
        }
    }

    public void setProxyPort(int port) {
        setProperty("proxyPort", String.valueOf(port));
    }

    public String getOutputFolder() {
        return getProperty("outputFolder");
    }

    public void setOutputFolder(String outputFolder) {
        setProperty("outputFolder", outputFolder);
    }

    public boolean getIsUsingProxy() {
        return ConfigManager.getYesNo(getProperty("useProxy"));
    }

    public void setIsUsingProxy(boolean isUseProxy) {
        setYesNo("useProxy", isUseProxy);
    }

    public String getWatcherFile() {
        String watcherFile = getProperty("watcherFile");
        if (watcherFile == null) {
            return "watcher.xml";
        } else {
            return watcherFile;
        }
    }

    public String getCacheFolder() {
        String cacheFolder = getProperty("cacheFolder");
        if (cacheFolder == null) {
            return "Cache";
        } else {
            return cacheFolder;
        }
    }

    public boolean isAdult() {
        return ConfigManager.getYesNo(getProperty("abc"));
    }

    public String getCurrentVersion() {
        return getProperty("currentVersion");
    }

    public boolean isZip() {
        return getYesNo(getProperty("zip"));
    }

    public void setZip(boolean isZip) {
        setYesNo("zip", isZip);
    }

    public boolean isDeleteAfterZip() {
        return getYesNo(getProperty("deleteAfterZip"));
    }

    public void setDeleteAfterZip(boolean isDelete) {
        setYesNo("deleteAfterZip", isDelete);
    }

    public boolean isGenerateHtml() {
        return getYesNo(getProperty("generateHtml"));
    }

    public void setGenerateHtml(boolean isGenerate) {
        setYesNo("generateHtml", isGenerate);
    }

    private static boolean getYesNo(String value) {
        if (value == null) {
            System.out.println("Get Yes No of " + value + " return null !");
            return false;
        }
        if ("yes".equals(value)) {
            return true;
        }
        return false;
    }

    private void setYesNo(String key, boolean value) {
        if (value) {
            setProperty(key, "yes");
        } else {
            setProperty(key, "no");
        }
    }
}
