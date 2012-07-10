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
            new ServerTempData("http://www.batoto.net", false),
            new ServerTempData("http://eatmanga.com", false),
            new ServerTempData("http://kissmanga.com", false),
            new ServerTempData("http://mangafox.me", false),
            new ServerTempData("http://mangainn.com", false),
            new ServerTempData("http://www.mangareader.net", false),
            new ServerTempData("http://truyentranhtuan.com", false),
            new ServerTempData("http://truyen.vnsharing.net", false),
            new ServerTempData("http://mangastream.com/", false),
            new ServerTempData("http://mangahere.com/", true)
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

                    for (Manga m : lstManga) {
                        m.setServer(null);
                    }

                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileOutput))) {
                        System.out.println("Writed: " + lstManga.size() + " record(s)");
                        oos.writeObject(lstManga);
                    }
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOutput))) {
                        Object o = ois.readObject();
                        List<Manga> l = (List<Manga>) o;
                        System.out.println("Loaded: " + l.size() + " record(s)");
//                    for (int j = 0; j < 10; j++) {
//                        System.out.println("Random record : " + l.get(NumberUtilities.getRandom(0, l.size() - 1)).getUrl());
//                    }
                    }
                } else {
                    System.out.println("Host Support = " + facade.getSupportType().toString());
                }
            }
        }
    }
}
