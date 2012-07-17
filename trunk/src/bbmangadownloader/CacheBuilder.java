/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.faces.SupportType;
import bbmangadownloader.ult.NumberUtilities;
import java.io.*;
import java.util.List;

/**
 *
 * @author Bach
 */
public class CacheBuilder {

    public static void main(String[] args) throws Exception {
        ConfigManager.loadOnStartUp();
        ServerManager.loadServer();

        ServerTempData[] servers = new ServerTempData[]{
            new ServerTempData(true, "http://www.batoto.net"),
            new ServerTempData(true, "http://eatmanga.com"),
            new ServerTempData(true, "http://kissmanga.com"),
            new ServerTempData(true, "http://mangafox.me"),
            new ServerTempData(true, "http://mangainn.com"),
            new ServerTempData(true, "http://www.mangareader.net"),
            new ServerTempData(true, "http://truyentranhtuan.com"),
            new ServerTempData(true, "http://truyen.vnsharing.net"),
            new ServerTempData(true, "http://mangastream.com/"),
            new ServerTempData(true, "http://mangahere.com/"),
            new ServerTempData(true, "http://cococomic.com/"),
            new ServerTempData(true, "http://99770.cc/")
        };

        for (ServerTempData sEE : servers) {
            if (sEE.isDownload) {
                Server server = ServerManager.getServerByUrl(sEE.serverUrl);
                IFacadeMangaServer facade = server.getMangaServer();
                if (facade.getSupportType() == SupportType.Support) {
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.println("Loading Server : " + server.getServerName());

                    File folderCache = new File("D:\\Manga\\Cache\\", server.getServerName());
                    folderCache.mkdirs();
                    File fileOutput = new File(folderCache, "file.data");

                    List<Manga> lstManga = facade.getAllMangas(server);
                    System.out.println("Parsing Done!");

                    try (FileWriter fw = new FileWriter(new File(folderCache, "cache.xml"))) {
                        fw.write("<roots>\n");
                        for (Manga m : lstManga) {
                            fw.write("<manga>");
                            fw.write(m.toXml());
                            fw.write("</manga>\n");
                            m.setServer(null);
                        }
                        fw.write("</roots>");
                        fw.close();
                    }

                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileOutput))) {
                        System.out.println("Writed: " + lstManga.size() + " record(s)");
                        oos.writeObject(lstManga);
                    }
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOutput))) {
                        Object o = ois.readObject();
                        List<Manga> l = (List<Manga>) o;
                        System.out.println("Loaded: " + l.size() + " record(s)");
                        for (int j = 0; j < 10; j++) {
                            Manga m = l.get(NumberUtilities.getRandom(0, l.size() - 1));
                            System.out.println("Random record : "
                                    + m.getUrl() + "\t" + m.getMangaName());
                        }
                    }
                } else {
                    System.out.println("Host Support = " + facade.getSupportType().toString());
                }
            }
        }
    }
}
