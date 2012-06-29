/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.database.Database;
import bbmangadownloader.database.controller.exceptions.NonexistentEntityException;
import bbmangadownloader.database.controller.exceptions.PreexistingEntityException;
import bbmangadownloader.database.entity.LinkMangaServer;
import bbmangadownloader.database.entity.Mangas;
import bbmangadownloader.database.entity.Servers;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.EmbeddedSimpleDataSource;

/**
 *
 * @author Bach
 */
public class BuildDB {

    public static void createDatabase() throws SQLException {
        EmbeddedSimpleDataSource ds = new EmbeddedSimpleDataSource();
        String dbPath = "D:\\Manga\\DB";
        ds.setDatabaseName(dbPath);
        // tell Derby to create the database if it does not already exist
        ds.setCreateDatabase("create");
        try (Connection conn = ds.getConnection()) {
            System.out.println("Connected to and created database !");
        }
    }

    private static List<Manga> getListMangaFromCache(String serverName, Server server) {
        try {
            File folderCache = new File("D:\\Manga\\Cache\\");
            File fileOutput = new File(new File(folderCache, serverName), "file.data");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOutput))) {
                Object o = ois.readObject();
                List<Manga> l = (List<Manga>) o;
                for (Manga m : l) {
                    m.setServer(server);
                }

                System.out.println("Loaded: " + l.size() + " record(s)");
                return l;
            }
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

//    public static void main(String[] args) throws SQLException {
//        createDatabase();
//    }
    public static void main(String[] args) {

        String[] arrPU = new String[]{
            "bbmangadownloaderDerbyPU", "bbmangadownloaderSQLPU"
        };
        Database.persitenceUnitPU = arrPU[0];

        ConfigManager.loadOnStartUp();

        String[] serverUrls = {
            "http://www.batoto.net",
            "http://eatmanga.com",
            "http://kissmanga.com",
            "http://mangafox.me",
            "http://mangainn.com",
            "http://www.mangareader.net",
            "http://truyentranhtuan.com",
            "http://truyen.vnsharing.net"
        };
        String[] serverNames = {
            "Batoto",
            "EatManga",
            "KissManga",
            "MangaFox",
            "MangaInn",
            "MangaReader",
            "TruyenTranhTuan",
            "VnSharing"
        };
        int mangaCounter = 0;
        for (int i = 6; i <= 6; i++) {

            String serverUrl = serverUrls[i];
            String serverName = serverNames[i];

            int numberOfNewManga = 0;

            Server server = ServerManager.getServerByName(serverName);

            System.out.println("Loading Server : " + serverName);
            List<Manga> lstManga = getListMangaFromCache(serverName, server);
            if (lstManga == null) {
                return;
            }
            //List<Manga> lstManga = facade.getAllMangas(server);
            System.out.println("Got Data !");
            //
            Servers sE;

            if ((sE = Database.getServerByName(serverName)) == null) {
                sE = new Servers();
                sE.setSName(serverName);
                sE.setSUrl(serverUrl);
                try {
                    Database.createServer(sE);
                    System.out.println("Created Server: ID = " + sE.getSId());
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Server existed: ID = " + sE.getSId());
            }

            Date now = new Date();
            for (Manga manga : lstManga) {
//                if ((++mangaCounter) % 100 == 0) {
//                    System.out.println("Counter = " + mangaCounter);
//                }
                Mangas mE;
                List<Mangas> lstTemp = Database.getMangaLikeName(manga.getMangaName());
                if (lstTemp != null && !lstTemp.isEmpty()) {
                    if (lstTemp.size() > 1) {
                        System.out.println("\t\tMore than 1 record of " + manga.getMangaName());
                    }
                    mE = lstTemp.get(0);
                    //                System.out.println("Manga existed: ID = " + mE.getMId());
                } else {
                    numberOfNewManga++;
                    mE = new Mangas();
                    mE.setMName(manga.getMangaName());
                    try {
                        Database.createManga(mE);
                    } catch (PreexistingEntityException ex) {
                        Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Created Manga: ID = " + mE.getMId() + "\tName = " + mE.getMName());
                }

                LinkMangaServer lE;
                if ((lE = Database.getLinkMangaServerByMangaServer(mE, sE)) == null) {
                    lE = new LinkMangaServer();
                    lE.setLMsLastupdate(now);
                    lE.setLMsManga(mE);
                    lE.setLMsServer(sE);
                    lE.setLMsUrl(manga.getUrl());
                    try {
                        Database.createLinkMangaServer(lE);
                        //                    System.out.println("\tCreate Link Manga-Server: ID = " + lE.getLMsId());
                    } catch (PreexistingEntityException ex) {
                        Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Date oldTime = lE.getLMsLastupdate();
                    lE.setLMsLastupdate(now);
                    lE.setLMsUrl(manga.getUrl());
                    try {
                        Database.updateLinkMangaServer(lE);
                        //                    System.out.println("\tLink update: ID = " + lE.getLMsId() + "\t" + oldTime + " -> " + lE.getLMsLastupdate());
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("New Manga: " + numberOfNewManga + " manga(s) !");
            System.out.println("--------------------------------------------------------------------------------");
        }
    }
}
