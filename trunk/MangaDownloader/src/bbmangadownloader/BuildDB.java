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
import bbmangadownloader.ult.MultitaskJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.EmbeddedSimpleDataSource;

/**
 *
 * @author Bach
 */
public class BuildDB {

    public static void createDatabase(String dbPath) throws SQLException {
        EmbeddedSimpleDataSource ds = new EmbeddedSimpleDataSource();
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
//        String dbPath = "D:\\Manga\\DB";
//        createDatabase(dbPath);
//        createDatabase(dbPath + "1");
//    }
//
    public static void main(String[] args) {
//        if (!new File(dbPath).exists()) {
//            createDatabase(dbPath);
//        }
//
        ConfigManager.loadOnStartUp();
        ServerManager.loadServer();

        String[] arrPU = new String[]{
            "MangaDownloaderDerbyPU", "MangaDownloaderSQLPU"
        };
        Database.persitenceUnitPU = arrPU[0];
        Database.getEntityManager();

        ServerTempData[] servers = new ServerTempData[]{
            new ServerTempData("http://www.batoto.net", true),
            new ServerTempData("http://eatmanga.com", true),
            new ServerTempData("http://kissmanga.com", true),
            new ServerTempData("http://mangafox.me", true),
            new ServerTempData("http://mangainn.com", true),
            new ServerTempData("http://www.mangareader.net", true),
            new ServerTempData("http://truyentranhtuan.com", true),
            new ServerTempData("http://truyen.vnsharing.net", true),
            new ServerTempData("http://mangastream.com/", true)
        };

        List<Callable<Object>> lstTask = new ArrayList<>();
        //        int mangaCounter = 0;
        for (final ServerTempData sEE : servers) {
            if (sEE.isDownload) {
                lstTask.add(new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        process(sEE);
                        return null;
                    }
                });

            }
        }
        startTime = System.nanoTime();
        MultitaskJob.doTask(1, lstTask);
    }
    private static int mangaCounter = 0;
    private static long startTime = 0;

    private static void process(ServerTempData sEE) {
        int numberOfNewManga = 0;
        Server server = ServerManager.getServerByUrl(sEE.serverUrl);
        String serverName = server.getServerName();

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Loading Server : " + server.getServerName());
        List<Manga> lstManga = getListMangaFromCache(serverName, server);
        if (lstManga == null || lstManga.isEmpty()) {
            return;
        }
        //List<Manga> lstManga = facade.getAllMangas(server);
        System.out.println("Got Data !");
        //
        Servers sE;

        if ((sE = Database.getServerByName(serverName)) == null) {
            sE = new Servers();
            sE.setSName(serverName);
            sE.setSUrl(sEE.serverUrl);
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
            if ((++mangaCounter) % 100 == 0) {
                System.out.format("Counter = %-4d\t%f\n", mangaCounter,
                        ((float) (System.nanoTime() - startTime)) / (1000000000));
            }
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
//                        System.out.println("Created Manga: ID = " + mE.getMId() + "\tName = " + mE.getMName());
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
//                        System.out.println("\tCreate Link Manga-Server: ID = " + lE.getLMsId());
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
//                    Date oldTime = lE.getLMsLastupdate();
                lE.setLMsLastupdate(now);
                lE.setLMsUrl(manga.getUrl());
                try {
                    Database.updateLinkMangaServer(lE);
//                        System.out.println("\tLink update: ID = " + lE.getLMsId() + "\t" + oldTime + " -> " + lE.getLMsLastupdate() + "\t"
//                                + manga.getMangaName());
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
