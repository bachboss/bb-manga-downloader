/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.IFacadeMangaServer.UrlType;
import bbmangadownloader.faces.implement.*;
import bbmangadownloader.manager.ConfigManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        MAP_HOST.put("mangacow", new FacadeMangaCow());
//        MAP_HOST.put("cxcscans", new FacadeCxcScans());
        MAP_HOST.put("99mh", new Facade99mh());

        if (ConfigManager.getCurrentInstance().isAdult()) {
            MAP_HOST.put("hentai2read", new FacadeHentai2Read());
            MAP_HOST.put("fakku", new FacedeFakku());
        }

        if (bbmangadownloader.BBMangaDownloader.TEST) {
            MAP_HOST.put("test", new FacadeTest());
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
            Logger.getLogger(FacadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static final IFacadeMangaServer FACADE_EMPTY = new IFacadeMangaServer() {
        @Override
        public List<Manga> getAllMangas(Server server) throws Exception {
            return Collections.emptyList();
        }

        @Override
        public List<Chapter> getAllChapters(Manga manga) throws Exception {
            return Collections.emptyList();
        }

        @Override
        public List<Image> getAllImages(Chapter chapter) throws Exception {
            return Collections.emptyList();
        }

        @Override
        public IFacadeMangaServer clone() {
            return this;
        }

        @Override
        public SupportType getSupportType() {
            return SupportType.Support;
        }

        @Override
        public String getServerName() {
            return "Empty";
        }

        @Override
        public Manga getManga(String mangaUrl) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Chapter getChapter(String chapterUrl, boolean isGetMangaInformation) {
            return Chapter.EMPTY_CHAPTER;
        }

        @Override
        public UrlType getUrlType(String url) {
            return UrlType.Unknow;
        }
    };
}