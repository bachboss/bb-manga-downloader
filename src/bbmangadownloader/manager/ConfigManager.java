/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager;

import bbmangadownloader.ult.OSSupport;
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

    public String getProperty(String propertyName) throws ConfigNotFoundException {
        String returnValue = softwareConfig.getProperty(propertyName);
        if (returnValue == null) {
            throw new ConfigNotFoundException("Config " + propertyName + " not found");

        } else {
            return returnValue;
        }
    }

    public boolean getBooleanProperty(String propertyName) throws ConfigNotFoundException {
        return ConfigManager.getYesNo(softwareConfig.getProperty(propertyName));
    }

    public void setProperty(String propertyName, String value) {
        softwareConfig.setProperty(propertyName, value);
    }

    private static boolean getYesNo(String value) {
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

    public String getProxyAddress() {
        try {
            return getProperty("proxyAddress");
        } catch (ConfigNotFoundException ex) {
            return "";
        }
    }

    public void setProxyAddress(String address) {
        setProperty("proxyAddress", address);
    }

    public int getProxyPort() {
        String port;
        try {
            port = getProperty("proxyPort");
            try {
                return Integer.parseInt(port);
            } catch (Exception ex) {
                return 0;
            }
        } catch (ConfigNotFoundException ex) {
            return 0;
        }
    }

    public void setProxyPort(int port) {
        setProperty("proxyPort", String.valueOf(port));
    }

    public String getOutputFolder() {
        try {
            File f = new File(getProperty("outputFolder"));
            if (f.isDirectory()) {
                return f.getAbsolutePath();
            } else {
                return OSSupport.getDefaultOutputFolder().getAbsolutePath();
            }
        } catch (ConfigNotFoundException ex) {
            return OSSupport.getDefaultOutputFolder().getAbsolutePath();
        }
    }

    public void setOutputFolder(String outputFolder) {
        setProperty("outputFolder", outputFolder);
    }

    public boolean getIsUsingProxy() {
        try {
            return ConfigManager.getYesNo(getProperty("useProxy"));
        } catch (ConfigNotFoundException ex) {
            return false;
        }
    }

    public void setIsUsingProxy(boolean isUseProxy) {
        setYesNo("useProxy", isUseProxy);
    }

    public String getWatcherFile() {
        try {
            return getProperty("watcherFile");
        } catch (ConfigNotFoundException ex) {
            return "watcher.xml";
        }
    }

    public String getCacheFolder() {
        try {
            return getProperty("cacheFolder");
        } catch (ConfigNotFoundException ex) {
            return "Cache";
        }
    }

    public boolean isAdult() {
        try {
            return ConfigManager.getYesNo(getProperty("abc"));
        } catch (ConfigNotFoundException ex) {
            return false;
        }
    }

    public boolean isZip() {
        try {
            return getYesNo(getProperty("zip"));
        } catch (ConfigNotFoundException ex) {
            return false;
        }
    }

    public void setZip(boolean isZip) {
        setYesNo("zip", isZip);
    }

    public boolean isDeleteAfterZip() {
        try {
            return getYesNo(getProperty("deleteAfterZip"));
        } catch (ConfigNotFoundException ex) {
            return false;
        }
    }

    public void setDeleteAfterZip(boolean isDelete) {
        setYesNo("deleteAfterZip", isDelete);
    }

    public boolean isGenerateHtml() {
        try {
            return getYesNo(getProperty("generateHtml"));
        } catch (ConfigNotFoundException ex) {
            return false;
        }
    }

    public void setGenerateHtml(boolean isGenerate) {
        setYesNo("generateHtml", isGenerate);
    }

    public void setCheckUpdateOnStartUp(boolean value) {
        setYesNo("updateOnStartup", value);
    }

    public boolean isCheckUpdateOnStartUp() {
        try {
            return getYesNo(getProperty("updateOnStartup"));
        } catch (ConfigNotFoundException ex) {
            return true;
        }
    }

    public static class ConfigNotFoundException extends Exception {

        public ConfigNotFoundException(String message) {
            super(message);
        }
    }
}
