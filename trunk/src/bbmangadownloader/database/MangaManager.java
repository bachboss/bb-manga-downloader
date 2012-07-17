/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database;

import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;
import java.io.File;
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
public class MangaManager {

    private static final List<Manga> listManga = new ArrayList<>();
    private static final Map<Integer, Manga> mapManga = new HashMap<>();
    private static final File FOLDER_CACHE = new File("F:\\Manga\\Cache");

    public static List<Manga> getListManga() {
        return listManga;
    }

    public static void loadOnStartup() {
        System.out.println("Loading Mangas...");
        ArrayList<File> listFile = new ArrayList<>();
        deepSearchFolder(FOLDER_CACHE, listFile);
        for (File f : listFile) {
            try {
                loadMangaFromFile(f, listManga);
            } catch (IOException ex) {
                Logger.getLogger(MangaManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Manga m : listManga) {
            mapManga.put(m.getHashId(), m);
        }
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

    public static void loadMangaFromFile(File f, final List<Manga> lstManga) throws IOException {

        Document doc = Jsoup.parse(f, "UTF-8");
        Elements elements = doc.select("manga");
        for (Element e : elements) {
            if (e.children().size() == 4) {
                int mangaID = Integer.parseInt(e.child(0).html());
                String mangaName = e.child(1).html();
                String serverName = e.child(2).html();
                String mangaUrl = e.child(3).html();
                Server s = ServerManager.getServerByName(serverName);
                Manga m = new Manga(mangaID, s, mangaName, mangaUrl);
                lstManga.add(m);
            } else {
                System.out.println(e);
            }

        }
    }
}
