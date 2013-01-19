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

    //<editor-fold>
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

    private static String getYesNoString(boolean value) {
        if (value) {
            return "yes";
        } else {
            return "no";
        }
    }

    private void setYesNo(String key, boolean value) {
        if (value) {
            setProperty(key, "yes");
        } else {
            setProperty(key, "no");
        }
    }

//    private void setYesNo(String key, String value) {
//        setYesNo(key, getYesNo(value));
//    }
    public String getConfig(Config config) {
        try {
            return getProperty(config.getConfigName());
        } catch (ConfigNotFoundException ex) {
            return config.getDefaultValue();
        }
    }

    /**
     *
     * @param <T>
     * @param config
     * @param o
     * @return null type o is not defined
     */
    public <T> T getConfig(Config config, T o) {
        if (o instanceof String) {
            return (T) getConfig(config);
        } else if (o instanceof Integer) {
            return (T) (Integer.valueOf(getConfig(config)));
        } else if (o instanceof Boolean) {
            return (T) (Boolean.valueOf(getYesNo(getConfig(config))));
        } else {
            return null;
        }
    }

    public void setConfig(Config config, String value) {
        setProperty(config.getConfigName(), value);
    }

    public void setConfig(Config config, int value) {
        setConfig(config, String.valueOf(value));
    }

    public void setConfig(Config config, boolean value) {
        setConfig(config, getYesNoString(value));
    }
    //</editor-fold>

    public String getProxyAddress() {
        return getConfig(Config.ProxyAddress);
    }

    public void setProxyAddress(String address) {
        setConfig(Config.ProxyAddress, address);
    }

    public int getProxyPort() {
        return getConfig(Config.ProxyPort, Integer.MAX_VALUE);
    }

    public void setProxyPort(int port) {
        setConfig(Config.Zip, port);
    }

    public String getOutputFolder() {
        File f = new File(getConfig(Config.OutputFolder));
        if (!f.exists()) {
            boolean isCreated = f.mkdir();
            if (!isCreated) {
                return Config.OutputFolder.getDefaultValue();
            }
        } else if (f.isDirectory()) {
            return f.getAbsolutePath();
        }
        return Config.OutputFolder.getDefaultValue();
    }

    public void setOutputFolder(String outputFolder) {
        setConfig(Config.OutputFolder, outputFolder);
    }

    public boolean getIsUsingProxy() {
        return ConfigManager.getYesNo(getConfig(Config.UseProxy));
    }

    public void setIsUsingProxy(boolean isUseProxy) {
        setYesNo(Config.UseProxy.getConfigName(), isUseProxy);
    }

    public String getWatcherFile() {
        File f = new File(getConfig(Config.WatcherFile));
        if (!f.exists()) {
            f.mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                return Config.WatcherFile.getDefaultValue();
            }
        }
        return f.getAbsolutePath();
    }

    public String getCacheFolder() {
        return getConfig(Config.CacheFolder);
    }

    public boolean isAdult() {
        return getYesNo(getConfig(Config.Aldult));
    }

    public boolean isZip() {
        return getYesNo(getConfig(Config.Zip));
    }

    public void setZip(boolean isZip) {
        setConfig(Config.Zip, isZip);
    }

    public boolean isDeleteAfterZip() {
        return getConfig(Config.DeleteAfterZip, Boolean.FALSE);
    }

    public void setDeleteAfterZip(boolean isDelete) {
        setConfig(Config.DeleteAfterZip, isDelete);
    }

    public boolean isGenerateHtml() {
        return getConfig(Config.GenerateHtml, Boolean.TRUE);
    }

    public void setGenerateHtml(boolean isGenerate) {
        setConfig(Config.GenerateHtml, isGenerate);
    }

    public void setCheckUpdateOnStartUp(boolean value) {
        setConfig(Config.UpdateOnStartUp, value);
    }

    public boolean isCheckUpdateOnStartUp() {
        return getConfig(Config.UpdateOnStartUp, Boolean.FALSE);
    }

    public static class ConfigNotFoundException extends Exception {

        public ConfigNotFoundException(String message) {
            super(message);
        }
    }

    public static enum Config {

        ProxyAddress("proxyAddress", ""),
        ProxyPort("proxyPort", "0"),
        OutputFolder("outputFolder", OSSupport.getDefaultOutputFolder().getAbsolutePath()),
        UseProxy("useProxy", "no"),
        WatcherFile("watcherFile", "watcher.xml"),
        CacheFolder("cacheFolder", "Cache"),
        Aldult("abc", "no"),
        Zip("zip", "no"),
        DeleteAfterZip("deleteAfterZip", "no"),
        GenerateHtml("generateHtml", "no"),
        UpdateOnStartUp("updateOnStartup", "yes");

        private Config(String configName, String defaultValue) {
            this.configName = configName;
            this.defaultValue = defaultValue;
        }
        private String configName;
        private String defaultValue;

        public String getConfigName() {
            return configName;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}
