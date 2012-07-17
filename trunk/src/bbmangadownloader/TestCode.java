/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public class TestCode {

    public static Document getDocument(String url) throws IOException {
        return HttpDownloadManager.getDocument(url);
    }

    public static void main(String[] args) throws Exception {
//        ConfigManager.loadOnStartUp();
//        ServerManager.loadServer();
//
//        Element e = doc.select("div[id=page] img").first();
//        System.out.println(e);
//        System.out.println("--------------------------------------------------------------------------------");
//        Server server = ServerManager.getServerByName("VnSharing");
//        IFacadeMangaServer facade = server.getMangaServer()  ;
//
//        String[] lstUrl = new String[]{
//            "http://truyen.vnsharing.net/Truyen/Bakuman"
//        };
//        List<Chapter> lstC = new ArrayList<>();
//
//        for (String str : lstUrl) {
//            List<Chapter> lstChapter = facade.getAllChapters(new Manga(server, "",
//                    str));
//            lstC.addAll(lstChapter);
//        }
//        System.out.println("--------------------------------------------------");
//        for (Chapter c : lstC) {
//            System.out.format("%-60s\t%-15s\t%s\n", c.getDisplayName(), c.getTranslator(), c.getUrl());
//        }
//        Database.getMangaLikeName("Naruto");
//        System.out.println("--------------------------------------------------");
//        System.out.println("        START QUERY ");
//        System.out.println("--------------------------------------------------");
//        Scanner s = new Scanner(System.in);
//        String name = "";
//        while (true) {
//            System.out.print("Enter Query: ");
//            name = s.nextLine();
//            if (name.equals("!")) {
//                break;
//            }
//            List<Mangas> lstMangas = Database.getMangaLikeName(name);
//            int x = 0;
//            for (Mangas m : lstMangas) {
//                List<LinkMangaServer> lstM = m.getLinkMangaServerList();
//                x += lstM.size();
//            }
//            System.gc();
//            System.out.format("Found: %-3d record(s) for %s\n", x, name);
//            System.out.println("--------------------------------------------------");
//        }
//        Cococomic m = new Cococomic();
//        List<Chapter> lst = m.getAllChapters(new Manga(
//                new Server(new FacadeCococomic()), "", "http://www.cococomic.com/comic/6613/"));
//        System.out.println("--------------------------------------------------------------------------------");
//        for (Chapter c : lst) {
//            System.out.println(c.getDisplayName() + "\t" + c.getUploadDate() + "\t" + c.getUrl());
//        }
//        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println("Total: " + lst.size());
//        System.out.println(xmlNodes.first());
//
//        System.out.println(xmlNodes.size());
//        System.out.println("Total: " + xmlNodes.size());
    }
}
