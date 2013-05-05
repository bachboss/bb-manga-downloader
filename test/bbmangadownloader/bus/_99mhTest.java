/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.faces.ServerManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class _99mhTest {

    public static void main(String[] args) {
        try {
            ServerManager.loadServer();
            String mangaUrl = "http://99mh.com/comic/7350/";
            Server s = ServerManager.getServerByUrl(mangaUrl);
            IFacadeMangaServer facade = s.getMangaServer();
            Manga m = new Manga(s, "Name", mangaUrl);

            List<Chapter> lstChapter = facade.getAllChapters(m);
            System.out.println("Got: " + lstChapter.size());
            for (Chapter c : lstChapter) {
                System.out.println(c.getDisplayName());
            }
        } catch (Exception ex) {
            Logger.getLogger(_99mhTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}