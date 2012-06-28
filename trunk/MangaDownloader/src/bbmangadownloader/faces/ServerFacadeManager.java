/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mangadownloader.config.ConfigManager;
import mangadownloader.faces.implement.*;

/**
 *
 * @author Bach
 */
public class ServerFacadeManager {

    public static final HashMap<String, IFacadeMangaServer> MAP_HOST = new HashMap<>();

    public static void loadData() {
        // Add Data
        MAP_HOST.put("vechai", new FacadeVeChai());
        MAP_HOST.put("eatmanga", new FacadeEatManga());
        MAP_HOST.put("truyentranhtuan", new FacadeTruyenTranhTuan());
        MAP_HOST.put("mangafox", new FacadeMangafox());
        MAP_HOST.put("mangareader", new FacadeManagerReader());
        MAP_HOST.put("batoto", new FacadeBatoto());
        MAP_HOST.put("vietboom", new FacadeVietBoom());
        MAP_HOST.put("comicvn", new FacadeComicVN());
        MAP_HOST.put("kissmanga", new FacadeKissManga());
        MAP_HOST.put("vnsharing", new FacadeVnSharing());
        MAP_HOST.put("mangainn", new FacadeMangaInn());

        if (ConfigManager.getCurrentInstance().isAdult()) {
            MAP_HOST.put("hentai2read", new FacadeHentai2Read());
        }
    }

    @Deprecated
    public static IFacadeMangaServer getServerFacadeByName(String name) {
        if (name == null) {
            return null;
        }
        return MAP_HOST.get(name.toLowerCase());
    }

    @Deprecated
    public static IFacadeMangaServer getServerFacadeByUrl(String url) {
        try {
            URL u = new URL(url);
            String checkingHost = u.getHost();
            String[] arr = checkingHost.split("\\.");
            if (arr.length > 1) {
                checkingHost = arr[arr.length - 2];
                return MAP_HOST.get(checkingHost);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServerFacadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
