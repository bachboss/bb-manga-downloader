/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import java.util.ArrayList;
import java.util.List;
import bbmangadownloader.database.entity.LinkWatcherLinkms;
import bbmangadownloader.database.entity.Watchers;
import bbmangadownloader.entity.Manga;

/**
 *
 * @author Bach
 */
public class Watcher {

    private int id;
    private List<Manga> lstManga;
    private String name;

    public Watcher(Watchers watcherEntity) {
        this.id = watcherEntity.getWId();
        this.name = watcherEntity.getWName();
        this.lstManga = new ArrayList<>();
        List<LinkWatcherLinkms> lstT = watcherEntity.getLinkWatcherLinkmsList();
        for (LinkWatcherLinkms l : lstT) {
            Manga m = new Manga(l.getLWlLinms());
            lstManga.add(m);
        }
    }

    public Watcher(String name) {
        this.name = name;
        lstManga = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Manga> getLstManga() {
        return lstManga;
    }

    public void addManga(Manga m) {
        lstManga.add(m);
    }

    public void addMangas(List<Manga> mangas) {
        lstManga.addAll(mangas);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMangaCount() {
        return lstManga.size();
    }
}
