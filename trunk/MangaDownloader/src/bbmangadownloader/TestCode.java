/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.faces.ServerManager;

/**
 *
 * @author Bach
 */
public class TestCode {

    public static void main(String[] args) throws Exception {
        ConfigManager.loadOnStartUp();
        ServerManager.loadServer();
//        Server server = ServerManager.getServerByName("VnSharing");
//        IFacadeMangaServer facade = server.getMangaServer();
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


        //        MangaInn m = new MangaInn();
        //        List<Chapter> lst = m.getAllChapters(new Manga(
        //                new Server(new FacadeMangaInn()), "", "http://localhost/mangainn.hunter.html"));
        //        System.out.println("--------------------------------------------------------------------------------");
        //        for (Chapter c : lst) {
        //            System.out.println(c.getDisplayName() + "\t" + c.getUploadDate());
        //        }
        //        System.out.println("--------------------------------------------------------------------------------");
        //        System.out.println("Total: " + lst.size());
        //        String url = "http://localhost/truyentranhtuan.list.html";
        //
        //        Document doc = HttpDownloadManager.getDocument(url);
        //
        //        Elements xmlNodes = doc.select("div[id=content-main] table tr[class]");
        //        for (Element e : xmlNodes) {
        ////            System.out.println(e);
        ////            System.out.println("--------------------------------------------------------------------------------");
        //        }
        //        System.out.println(xmlNodes.first());
        //
        //        System.out.println(xmlNodes.size());
        //        System.out.println("Total: " + xmlNodes.size());
        //       
    }
}
