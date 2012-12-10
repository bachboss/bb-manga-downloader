/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;
import java.io.File;
import java.io.IOException;
import java.util.*;
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
public class MangaManager {

//    private static final int DEFAULT_EACH_FILE_COUNT = 50000;
//    private static ArrayList<Manga> listManga;
    private static final Map<Integer, Manga> mapManga = new HashMap<Integer, Manga>();
    private static final File FOLDER_CACHE = new File(ConfigManager.getCurrentInstance().getCacheFolder());
    private static boolean isLoaded = false;

    public static Collection<Manga> getMangas() {
        return mapManga.values();
    }

    public synchronized static void lazyLoadAllMangas() {
        if (!isLoaded) {
            synchronized (mapManga) {
                if (!isLoaded) {
                    loadAllMangas();
                }
            }
        }
    }

    public synchronized static void loadAllMangas() {
        isLoaded = true;
//        System.out.println(System.currentTimeMillis());
        System.out.println("Loading Mangas...");
        ArrayList<File> listFile = new ArrayList<File>();
        deepSearchFolder(FOLDER_CACHE, listFile);
//        List<Callable<Object>> lstTask = new ArrayList<Callable<Object>>();
        for (final File f : listFile) {
//            lstTask.add(new Callable<Object>() {
//
//                @Override
//                public Object call() throws Exception {
            try {
                loadMangaFromFile(f);
            } catch (IOException ex) {
                Logger.getLogger(MangaManager.class.getName()).log(Level.SEVERE, null, ex);
            }
//                    return null;
        }
//            });
//        }
//        MultitaskJob.doTask(2, lstTask);
//        System.out.println(System.currentTimeMillis());

    }

    public static Manga getManga(int id) {
        return mapManga.get(id);
    }

    private static void deepSearchFolder(File folder, List<File> lstFile) {
        if (folder.isDirectory()) {
            File[] arrFile = folder.listFiles();
            for (File f : arrFile) {
                if (f.isDirectory()) {
                    deepSearchFolder(f, lstFile);
                } else if (f.isFile()) {
                    if (f.getName().endsWith("cache.xml")) {
                        lstFile.add(f);
                    }
                }
            }
        }
    }

    private static void loadMangaFromFile(File f) throws IOException {
        Document doc = Jsoup.parse(f, "UTF-8");
        System.out.println("\tLoading from file: " + f.getAbsolutePath());
        Elements elements = doc.select("manga");
        for (Element e : elements) {
            Manga m = getMangaFromTag(e);
            if (m != null) {
                Manga m2 = mapManga.put(m.getHashId(), m);
                if (m2 != null && m2 != m) {
                    System.out.format("Duplicated: %-50s %-10s\t%-50s %-10s\n", m2.getMangaName(), m2.getServer().getServerName(),
                            m.getMangaName(), m.getServer().getServerName());
                }
            }
        }
    }

    public static Manga getMangaFromTag(Element eTag) {
        if (eTag.children().size() == 4) {
            int mangaID = Integer.parseInt(eTag.child(0).html());
            String mangaName = eTag.child(1).html();
            String serverName = eTag.child(2).html();
            String mangaUrl = eTag.child(3).html();
            Server s = ServerManager.getServerByName(serverName);
            Manga m = new Manga(mangaID, s, mangaName, mangaUrl);
            return m;
        } else {
            System.out.println("Tag Error: " + eTag);
            return null;
        }
    }

    public static Manga addManga(Manga manga) {
        Manga m = mapManga.get(manga.getHashId());
        if (m == null) {
            mapManga.put(manga.getHashId(), manga);
            return manga;
        } else {
            return m;
        }
    }

    static boolean isLoaded() {
        return isLoaded;
    }
}
