/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.entity.Manga;
import bbmangadownloader.ult.MultitaskJob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author Bach
 */
public class Watcher {

    private int id;
    private List<Manga> lstManga;
    private Map<Integer, Manga> mapManga;
    private String name;
    private WatcherStatus watcherStatus = WatcherStatus.None;

//    public Watcher(Watchers watcherEntity) {
//        this.id = watcherEntity.getWId();
//        this.name = watcherEntity.getWName();
//        this.lstManga = new ArrayList<>();
//        List<LinkWatcherManga> lstT = watcherEntity.getLinkWatcherMangaList();
//        for (LinkWatcherManga l : lstT) {
//            Manga m = new Manga(l.getLWmManga());
//            lstManga.add(m);
//        }
//    }
    public Watcher(String name) {
        this.name = name;
        lstManga = new ArrayList<>();
        mapManga = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public List<Manga> getLstManga() {
        return lstManga;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getNewestChapter() {
        if (watcherStatus == WatcherStatus.Loaded) {
            float f = -1;
            for (Manga m : lstManga) {
                m.doSortChapters(true);
                try {
                    float f2 = m.getListChapter().get(0).getChapterNumber();
                    f = Math.max(f, f2);
                } catch (NullPointerException | IndexOutOfBoundsException ex) {
                    System.out.println("Null: " + m.getUrl());
                }
            }
            return f;
        } else {
            return -1;
        }
    }

    public WatcherStatus getWatcherStatus() {
        return watcherStatus;
    }

    public void setWatcherStatus(WatcherStatus watcherStatus) {
        this.watcherStatus = watcherStatus;
    }

    public void forceLoadChapters() {
        this.watcherStatus = WatcherStatus.Loading;
        List<Manga> lstTemp = getLstManga();
        if (!lstTemp.isEmpty()) {
            List<Callable<Boolean>> lstTask = new ArrayList<>();

            for (final Manga m : lstTemp) {
                lstTask.add(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        m.forceLoadChapter();
                        return true;
                    }
                });
            }
            MultitaskJob.doTask(lstTask.size(), lstTask);
        }
    }

    public void loadChapers() {
//        final long start = System.nanoTime();
        this.watcherStatus = WatcherStatus.Loading;
        // Solution 1: Use Multi Thread: 11.5s (Naruto - 8 hosts)
        List<Manga> lstTemp = getLstManga();
        if (!lstTemp.isEmpty()) {
            List<Callable<Boolean>> lstTask = new ArrayList<>();

            for (final Manga m : lstTemp) {
                lstTask.add(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        m.loadChapter();
                        return true;
                    }
                });
            }
            MultitaskJob.doTask(lstTask.size(), lstTask);
        }
        // Solution 2: Use Multi Thread: 7.2s (Naruto - 8 hosts)
//        {
//            List<Manga> lstTemp = getLstManga();
//            for (final Manga m : lstTemp) {
//                Thread t = new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        m.loadChapter();
//                        long end = System.nanoTime();
//                        System.out.println(end - start);
//                    }
//                });
//                t.start();
//            }
//        }
        // Solution 3: Single Thread: 29s
//        {
//            List<Manga> lstTemp = getLstManga();
//            for (final Manga m : lstTemp) {
//                m.loadChapter();
//            }
//        }
        this.watcherStatus = WatcherStatus.Loaded;
//        long end = System.nanoTime();
//        System.out.println(end - start);
    }

    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<watcher>");
        sb.append("<id>").append(id).append("</id>");
        sb.append("<name>").append(name).append("</name>");
        sb.append("<mangas>");
        for (Manga m : lstManga) {
            if (m != null) {
                sb.append("<manga>").append(m.getHashId()).append("</manga>");
            }
        }
        sb.append("</mangas>");
        sb.append("</watcher>");
        return sb.toString();
    }
}
