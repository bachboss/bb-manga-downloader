/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

/**
 *
 * @author Bach
 */
public class ServerTempData {

    public String serverUrl;
    public boolean isDownload;

    public ServerTempData(String serverUrl, boolean isDownload) {
        this.serverUrl = serverUrl;
        this.isDownload = isDownload;
    }
}
