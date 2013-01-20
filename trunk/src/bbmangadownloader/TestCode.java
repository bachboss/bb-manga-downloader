/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.entity.Image;
import bbmangadownloader.manager.HttpDownloadManager;
import java.io.IOException;
import java.io.InputStream;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Bach
 */
public class TestCode {

    private static String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0";

    private static Document getDocument(String url, String from) throws IOException {
        return HttpDownloadManager.createConnection(url).referer(from).getDocument();
    }

    private static Document getDocument(String url) throws IOException {
        return HttpDownloadManager.createConnection(url).getDocument();
    }

    private static Image getImageFromChapter(Document doc, int order) {
        Element e = doc.select("div[id=page] img").first();
        Image img = new Image(order, e.attr("src"), null);
        return img;
    }

    private static Image getImageFromChapter(String url, int order) throws IOException {
        Document doc = getDocument(url);
        return getImageFromChapter(doc, order);
    }

    public static void main(String[] args) throws Exception {
        String url = "http://manga24h.com/944/Toriko.html";
        Document doc = HttpDownloadManager.createConnection(url).
                cookie("location.href").getDocument();
        System.out.println("OK !");
        


//        Document doc = getDocument("http://manga.cxcscans.com/reader/read/bamboo_blade/en/14/102/page/4");
//        Elements elements = doc.select("script[type=text/javascript]");
////        for (Element e : elements) {
////            try {
////                String url = e.attr("href");
////                int order = Integer.parseInt(e.html());
////                Image img;
////                if (order == 1) {
////                    System.out.println("Current doc " + order);
//////                    img = getImageFromChapter(doc, order);
////                } else {
////                    System.out.println(url + "\t" + order);
//////                    img = getImageFromChapter(url, order);
////                }
//////                System.out.println(img.get);
//////                lstImage.add(img);
////            } catch (NumberFormatException ex) {
////            }
////        }
//        System.out.println(elements.size());
//        for (Element e : elements) {
//            if (e.html().contains("var pages =")) {
//                String text = e.html();
//                text = text.substring(text.indexOf("var pages = [") + 13);
//                text = text.substring(0, text.indexOf("];"));
//                String[] strs = text.split("\\},\\{");
//                for (String str : strs) {
//                    str = str.substring(str.indexOf("\"url\":") + 7);
//                    str = str.substring(0, str.indexOf('"'));
//                    str = str.replaceAll("\\\\/", "/");
//                    System.out.println(str);
//                }
//                break;
//            }
//        }



//        Elements els = doc.getElementById("listChapter").select("[class=content_cacchuong link_chapter] a");
//        System.out.println("--------------------------------------------------------------------------------");
//        for (Element e : els) {
//            System.out.println(e);
//            System.out.println("--------------------------------------------------------------------------------");
//        }
//        String str = doc.select("div[class=paging] span[class=page]").last().child(0).attr("href");
//        str = str.substring(str.indexOf('(') + 1, str.indexOf(')'));
//        System.out.println(str);
//        String url = "http://manga24h.com/952/Cage-of-Eden.html";
//        Document doc = getDocument(url);
//
//        Elements els = doc.select("div[class=post] table[class=mytable] tr:gt(0)");
//        for (Element e : els) {
//            System.out.println("--------------------------------------------------------------------------------");
//            Element eLink = e.children().first();
//            Element eDate = e.children().last();
//            System.out.format("%-40s \"%s\"\n", eLink.select("a").first().attr("href"), TextUtilities.trim(HtmlUtilities.unescapeHtml4(eDate.html())));
//        }
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
