/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database;

import bbmangadownloader.entity.Manga;
import bbmangadownloader.gui.model.Watcher;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bach
 */
public class Database {

    public static boolean isLoadedMangaFromFile() {
        return MangaManager.isLoaded();
    }

    public static List<Manga> getAllManga() {
        return new ArrayList<Manga>(MangaManager.getMangas());
    }

    public static List<Manga> getAllMangaFromFile() {
        MangaManager.lazyLoadAllMangas();
        return getAllManga();
    }

    public static List<Watcher> getAllWatcher() {
        return WatcherMangager.getListWatcher();
    }

    public static Watcher createWatcher(Watcher watcher) {
        return WatcherMangager.addWatcher(watcher);
    }

    public static void addMangaToWatcher(int mangaId, int watcherId) {
        WatcherMangager.addMangaToWatcher(mangaId, watcherId);
    }

    public static void addMangasToWatcher(List<Manga> listManga, int watcherId) {
        WatcherMangager.addMangasToWatcher(listManga, watcherId);
    }

    public static void removeWatcher(int watcherId) {
        WatcherMangager.removeWatcher(watcherId);
    }

    public static void editWatcher(Watcher watcher) {
        WatcherMangager.editWatcher(watcher);
    }

    public static void removeMangaFromWacher(Manga m, Watcher w) {
        WatcherMangager.removeMangaFromWacher(m, w);
    }

    public static Manga createManga(Manga manga) {
        return MangaManager.addManga(manga);
    }
}
