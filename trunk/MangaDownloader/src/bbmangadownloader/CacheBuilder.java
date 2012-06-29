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
import bbmangadownloader.ult.MyUtilities;
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

        String serverUrl = "http://truyen.vnsharing.net/";
        String serverName = "VnSharing";

        File folderCache = new File("D:\\Manga\\Cache\\", serverName);
        folderCache.mkdirs();
        File fileOutput = new File(folderCache, "file.data");

        ConfigManager.loadOnStartUp();

        Server server = ServerManager.getServerByUrl(serverUrl);
        IFacadeMangaServer facade = server.getMangaServer();
        if (facade.getSupportType() == SupportType.Support) {
            System.out.println("Loading Server : " + serverName);
            List<Manga> lstManga = facade.getAllMangas(server);
            System.out.println("Got Data !");

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
                for (int i = 0; i < 10; i++) {
                    System.out.println("Random record : " + l.get(MyUtilities.getRandom(0, l.size() - 1)).getUrl());
                }
            }
        } else {
            System.out.println("Host Support = " + facade.getSupportType().toString());
        }
    }
}
