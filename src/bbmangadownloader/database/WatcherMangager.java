/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database;

import bbmangadownloader.entity.Manga;
import bbmangadownloader.gui.model.Watcher;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.ult.DatabaseUtilities;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class WatcherMangager {

    private static final List<Watcher> listWatcher = new ArrayList<Watcher>();
    private static final Map<Integer, Watcher> mapWatcher = new HashMap<Integer, Watcher>();
    private static File FILE_WATCHER;

    public static List<Watcher> getListWatcher() {
        return listWatcher;
    }

    public static synchronized void loadOnStartup() {
        System.out.println("Loading Watchers...");
        try {
            FILE_WATCHER = new File(ConfigManager.getCurrentInstance().getWatcherFile());
            if (!FILE_WATCHER.exists()) {
            }
            loadWatcherFromFile(FILE_WATCHER, listWatcher);
            for (Watcher w : listWatcher) {
                mapWatcher.put(w.getId(), w);
            }
        } catch (Exception ex) {
            Logger.getLogger(WatcherMangager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Watcher getWatcher(int id) {
        return mapWatcher.get(id);
    }

    private static synchronized void loadWatcherFromFile(File f, final List<Watcher> listWatcher) throws Exception {
// <watcher><id></id><name></name><mangas><manga>MangaID<manga>..</mangas>
        Document doc = Jsoup.parse(f, "UTF-8");
        Elements elements = doc.select("watcher");

        for (Element e : elements) {
            Watcher w = getWatcherFromTag(e);
            listWatcher.add(w);
        }
    }

    public static synchronized Watcher addWatcher(Watcher w) {
        int id = DatabaseUtilities.getRandomId();
        w.setId(id);
        listWatcher.add(w);
        mapWatcher.put(id, w);
        flushDataToFile();
        return w;
    }

    public static synchronized void addMangaToWatcher(int mangaId, int watcherId) {
        Watcher watcher = mapWatcher.get(watcherId);
        watcher.addManga(MangaManager.getManga(mangaId));
        flushDataToFile();
    }

    public static synchronized void addMangasToWatcher(List<Manga> listManga, int watcherId) {
        Watcher watcher = mapWatcher.get(watcherId);
        watcher.addMangas(listManga);
        flushDataToFile();
    }

    private static synchronized void flushDataToFile() {
        FileWriter fw;
        try {
            fw = new FileWriter(FILE_WATCHER);
            fw.write("<root>\n");
            for (Watcher w : listWatcher) {
                fw.write(w.toXml() + "\n");
            }
            fw.write("</root>");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(WatcherMangager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized static void removeWatcher(int watcherId) {
        if (mapWatcher.containsKey(watcherId)) {
            Watcher w = mapWatcher.get(watcherId);
            listWatcher.remove(w);
            flushDataToFile();
        }
    }

    public synchronized static void editWatcher(Watcher watcher) {
        flushDataToFile();
    }

    static void removeMangaFromWacher(Manga m, Watcher w) {
        w.removeManga(m);
        flushDataToFile();
    }

    private static Watcher getWatcherFromTag(Element e) {
        if (e.children().size() >= 2) {
            int wID = Integer.parseInt(e.child(0).html());
            String wName = e.child(1).html();
            Watcher w = new Watcher(wName);
            w.setId(wID);
            Elements eManga = e.select("mangas manga");
            if (!eManga.isEmpty()) {
                for (Element e2 : eManga) {
                    Manga m = MangaManager.getMangaFromTag(e2);
                    m = Database.createManga(m);
                    w.addManga(m);
//                        Database.addMangaToWatcher(mID, wID);
                }
            }
            return w;
        } else {
            System.out.println("Tag Error: " + e);
            return null;
        }
    }
}
