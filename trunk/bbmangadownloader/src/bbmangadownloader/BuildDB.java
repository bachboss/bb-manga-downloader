/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

/**
 *
 * @author Bach
 */
public class BuildDB {
//    public static void createDatabase(String dbPath) throws SQLException {
//        EmbeddedSimpleDataSource ds = new EmbeddedSimpleDataSource();
//        ds.setDatabaseName(dbPath);
//        // tell Derby to create the database if it does not already exist
//        ds.setCreateDatabase("create");
//        try (Connection conn = ds.getConnection()) {
//            System.out.println("Connected to and created database !");
//        }
//    }
//
//    private static List<Manga> getListMangaFromCache(String serverName, Server server) {
//        try {
//            File folderCache = new File("D:\\Manga\\Cache\\");
//            File fileOutput = new File(new File(folderCache, serverName), "file.data");
//            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOutput))) {
//                Object o = ois.readObject();
//                List<Manga> l = (List<Manga>) o;
//                for (Manga m : l) {
//                    m.setServer(server);
//                }
//
//                System.out.println("Loaded: " + l.size() + " record(s)");
//                return l;
//            }
//        } catch (IOException | ClassNotFoundException ex) {
//            return null;
//        }
//    }
//
////    public static void main(String[] args) throws SQLException {
////        String dbPath = "D:\\Manga\\DB";
////        createDatabase(dbPath);
////        createDatabase(dbPath + "1");
////    }
////
//    public static void main(String[] args) {
////        if (!new File(dbPath).exists()) {
////            createDatabase(dbPath);
////        }
////
//        ConfigManager.loadOnStartUp();
//        ServerManager.loadServer();
//
//        String[] arrPU = new String[]{
//            "MangaDownloaderDerbyPU", "MangaDownloaderSQLPU"
//        };
//        Database.persitenceUnitPU = arrPU[0];
//        Database.getEntityManager();
//
//        ServerTempData[] servers = new ServerTempData[]{
//            new ServerTempData("http://www.batoto.net", true),
//            new ServerTempData("http://eatmanga.com", true),
//            new ServerTempData("http://kissmanga.com", true),
//            new ServerTempData("http://mangafox.me", true),
//            new ServerTempData("http://mangainn.com", true),
//            new ServerTempData("http://www.mangareader.net", true),
//            new ServerTempData("http://truyentranhtuan.com", true),
//            new ServerTempData("http://truyen.vnsharing.net", true),
//            new ServerTempData("http://mangastream.com/", true),
//            new ServerTempData("http://mangahere.com/", true),
//            new ServerTempData("http://cococomic.com/", true),
//            new ServerTempData("http://99770.cc/", true)
//        };
//
//        List<Callable<Object>> lstTask = new ArrayList<>();
//        //        int mangaCounter = 0;
//        for (final ServerTempData sEE : servers) {
//            if (sEE.isDownload) {
//                lstTask.add(new Callable<Object>() {
//
//                    @Override
//                    public Object call() throws Exception {
//                        process(sEE);
//                        return null;
//                    }
//                });
//
//            }
//        }
//        startTime = System.nanoTime();
//        MultitaskJob.doTask(1, lstTask);
//    }
//    private static int mangaCounter = 0;
//    private static long startTime = 0;
//
//    private static void process(ServerTempData sEE) {
//        int numberOfNewManga = 0;
//        Server server = ServerManager.getServerByUrl(sEE.serverUrl);
//        String serverName = server.getServerName();
//
//        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println("Loading Server : " + server.getServerName());
//        List<Manga> lstManga = getListMangaFromCache(serverName, server);
//        if (lstManga == null || lstManga.isEmpty()) {
//            return;
//        }
//        //List<Manga> lstManga = facade.getAllMangas(server);
//        System.out.println("Got Data !");
//        //
//        Servers sE;
//
//        if ((sE = Database.getServerByName(serverName)) == null) {
//            sE = new Servers();
//            sE.setSName(serverName);
//            sE.setSUrl(sEE.serverUrl);
//            try {
//                Database.createServer(sE);
//                System.out.println("Created Server: ID = " + sE.getSId());
//            } catch (PreexistingEntityException ex) {
//                Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (Exception ex) {
//                Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            System.out.println("Server existed: ID = " + sE.getSId());
//        }
//
//        Date now = new Date();
//        for (Manga manga : lstManga) {
//            if ((++mangaCounter) % 100 == 0) {
//                System.out.format("Counter = %-4d\t%f\n", mangaCounter,
//                        ((float) (System.nanoTime() - startTime)) / (1000000000));
//            }
//            Mangas mE = new Mangas();
//            List<Mangas> lstQuery = null;
//            try {
//                lstQuery = Database.getMangasByServerAndName(manga.getMangaName(), sE.getSId());
//            } catch (Exception ex) {
//                Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            if (lstQuery == null || lstQuery.isEmpty()) {
//                mE.setMName(manga.getMangaName());
//                mE.setMServer(sE);
//                mE.setMLastupdate(now);
//                mE.setMUrl(manga.getUrl());
//                try {
//                    Database.createManga(mE);
////                    System.out.println("\tCreate Link Manga-Server: ID = " + mE.getMId());
//                } catch (Exception ex) {
//                    Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } else {
////                    Date oldTime = lE.getLMsLastupdate();
//                if (lstQuery.size() > 1) {
//                    System.out.println("Find more than 1 record: " + manga.getMangaName() + "\t" + server.getServerName());
//                }
//
//                mE = lstQuery.get(0);
//                mE.setMLastupdate(now);
//                mE.setMUrl(manga.getUrl());
//                try {
//                    Database.updateManga(mE);
////                        System.out.println("\tLink update: ID = " + lE.getLMsId() + "\t" + oldTime + " -> " + lE.getLMsLastupdate() + "\t"
////                                + manga.getMangaName());
//                } catch (Exception ex) {
//                    Logger.getLogger(BuildDB.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println("New Manga: " + numberOfNewManga + " manga(s) !");
//        System.out.println("--------------------------------------------------------------------------------");
//    }
}
