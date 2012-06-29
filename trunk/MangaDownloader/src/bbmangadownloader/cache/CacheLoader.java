/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bbmangadownloader.database.Database;
import bbmangadownloader.database.entity.LinkMangaServer;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;

/**
 *
 * @author Bach
 */
public class CacheLoader {

    private static final String NAME_MANGA_CACHE = "mangas.";
    private static final CacheLoader cI = new CacheLoader();
    //
    private boolean isLoadedManga = false;

    //
    private CacheLoader() {
    }

    public static List<Manga> getMangas() {
        if (cI.isLoadedManga) {
            try {
                Object o = CacheManager.getObjectFromCache(NAME_MANGA_CACHE);
                List<Manga> l = (List<Manga>) o;
                for (Manga m : l) {
                    Server s = ServerManager.getServerByName(m.getServer().getServerName());
                    m.setServer(s);
                }
                return l;
            } catch (IOException ex) {
                Logger.getLogger(CacheLoader.class.getName()).log(Level.SEVERE, null, ex);
                List<Manga> lst = loadMangaFromDatabase();
                return lst;
            } catch (ClassCastException ex) {
                Logger.getLogger(CacheLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void loadMangas() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (cI) {
                    List<Manga> lst = loadMangaFromDatabase();
                    for (Manga m : lst) {
                        m.setServer(new Server(m.getServer().getServerName()));
                    }
                    System.out.println("Loaded Mangas !");
                    CacheManager.writeObjectToCache(NAME_MANGA_CACHE, lst);
                    System.out.println("Wrote to cache done !");
                    cI.isLoadedManga = true;
                }
                System.gc();
            }
        }).start();
    }

    private static List<Manga> loadMangaFromDatabase() {
        System.out.println("Load Mangas from DB !");
        List<Manga> lstManga = new ArrayList<>();
        List<LinkMangaServer> tempList = Database.getAllLinkMangaServer();
        for (LinkMangaServer l : tempList) {
            Manga m = new Manga(l);
            lstManga.add(m);
        }
        return lstManga;
    }
}
