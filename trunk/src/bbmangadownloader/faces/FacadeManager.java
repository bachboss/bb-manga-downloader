/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import bbmangadownloader.faces.implement.*;
import bbmangadownloader.manager.ConfigManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class FacadeManager {

    // Dump here, public is a litle dangerous
    public static final HashMap<String, IFacadeMangaServer> MAP_HOST = new HashMap<String, IFacadeMangaServer>();

    public static void loadData() {
        System.out.println("Loading Facade...");
        // Add Data
        MAP_HOST.put("vechai.info", new FacadeVeChai());
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
        MAP_HOST.put("mangastream", new FacadeMangaStream());
        MAP_HOST.put("mangahere", new FacadeMangaHere());
        MAP_HOST.put("cococomic", new FacadeCococomic());
        MAP_HOST.put("99770", new Facade99770());
        MAP_HOST.put("manga24h", new FacadeManga24h());
//        MAP_HOST.put("veryim", new FacadeVeryim());
        MAP_HOST.put("blogtruyen", new FacadeBlogTruyen());
        MAP_HOST.put("tenmanga", new FacadeTenManga());
        MAP_HOST.put("dragonfly", new FacadeDragonFly());
        MAP_HOST.put("nomanga", new FacadeNoManga());
//        MAP_HOST.put("cxcscans", new FacadeCxcScans());

        if (ConfigManager.getCurrentInstance().isAdult()) {
            MAP_HOST.put("hentai2read", new FacadeHentai2Read());
            MAP_HOST.put("fakku", new FacedeFakku());
        }

        if (bbmangadownloader.BBMangaDownloader.TEST) {
            MAP_HOST.put("test", new FacadeTest());
        }
    }

//    public static void loadData() {
//        System.out.println("Loading Facade...");
//        // Add Data
//        MAP_HOST.put("vechai.info", new FacadeVeChai());
//        MAP_HOST.put("eatmanga.com", new FacadeEatManga());
//        MAP_HOST.put("truyentranhtuan.com", new FacadeTruyenTranhTuan());
//        MAP_HOST.put("mangafox.me", new FacadeMangafox());
//        MAP_HOST.put("www.mangareader.net", new FacadeManagerReader());
//        MAP_HOST.put("www.batoto.net", new FacadeBatoto());
//        MAP_HOST.put("truyen.vietboom.com", new FacadeVietBoom());
//        MAP_HOST.put("truyen.comicvn.net", new FacadeComicVN());
//        MAP_HOST.put("kissmanga.com", new FacadeKissManga());
//        MAP_HOST.put("truyen.vnsharing.net", new FacadeVnSharing());
//        MAP_HOST.put("www.mangainn.com", new FacadeMangaInn());
//        MAP_HOST.put("mangastream.com", new FacadeMangaStream());
//        MAP_HOST.put("www.mangahere.com", new FacadeMangaHere());
//        MAP_HOST.put("www.cococomic.com", new FacadeCococomic());
//        MAP_HOST.put("mh.99770.com", new Facade99770());
//        MAP_HOST.put("manga24h.com", new FacadeManga24h());
////        MAP_HOST.put("comic.veryim.com", new FacadeVeryim());
//        MAP_HOST.put("blogtruyen.com", new FacadeBlogTruyen());
//
//        if (ConfigManager.getCurrentInstance().isAdult()) {
//            MAP_HOST.put("hentai2read.com", new FacadeHentai2Read());
//        }
//    }
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
            Logger.getLogger(FacadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}