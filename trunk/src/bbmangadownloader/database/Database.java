/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database;

import bbmangadownloader.entity.Manga;
import bbmangadownloader.gui.model.Watcher;
import java.util.List;

/**
 *
 * @author Bach
 */
public class Database {

    public static List<Manga> getAllManga() {
        return MangaManager.getListManga();
    }

    public static List<Watcher> getAllWatcher() {
        return WatcherMangager.getListWatcher();
    }

    public static int createWatcher(Watcher watcher) {
        return WatcherMangager.addWatcher(watcher);
    }

    public static void addMangaToWatcher(int mangaId, int watcherId) {
        WatcherMangager.addMangaToWatcher(mangaId, watcherId);
    }

    public static void removeWatcher(int watcherId) {
        WatcherMangager.removeWatcher(watcherId);
    }

    public static void editWatcher(Watcher watcher) {
        WatcherMangager.editWatcher(watcher);
    }

    public static void removeMangaFromWacher(int mangaId, int watcherId) {
        WatcherMangager.removeMangaFromWacher(mangaId, watcherId);
    }
}
