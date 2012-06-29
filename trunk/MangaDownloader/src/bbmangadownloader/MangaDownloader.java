/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.database.Database;
import bbmangadownloader.database.entity.Mangas;
import bbmangadownloader.entity.Manga;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class MangaDownloader {

    public static void main(String[] args) {

        String[] arrPU = new String[]{
            "bbmangadownloaderDerbyPU", "bbmangadownloaderSQLPU"
        };
        Database.persitenceUnitPU = arrPU[1];

        String serverUrl = "http://www.batoto.net";
        String serverName = "Batoto";

        ConfigManager.loadOnStartUp();

        File folderCache = new File("D:\\Manga\\Cache\\", serverName);
        folderCache.mkdirs();
        File fileOutput = new File(folderCache, "file.data");
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileOutput));
            Object o = ois.readObject();
            List<Manga> l = (List<Manga>) o;
            System.out.println("Loaded: " + l.size() + " record(s)");

            Manga m = l.get(129);
            System.out.println("Checking Manga: " + m.getMangaName());


            List<Mangas> lstMangaE = Database.getMangaLikeName(m.getMangaName());
            if (lstMangaE != null && !lstMangaE.isEmpty()) {
                System.out.println("Duplicated ! Your job is done!");
            } else {
                Mangas eM = new Mangas();
                eM.setMName(m.getMangaName());
                try {
                    Database.createManga(eM);
                } catch (Exception ex) {
                    Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Create new record : ID = " + eM.getMId());
            }


        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}