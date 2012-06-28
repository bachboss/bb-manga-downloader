/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import java.util.List;
import mangadownloader.database.Database;
import mangadownloader.database.entity.Mangas;

/**
 *
 * @author Bach
 */
public class TestCode {

    public static void main(String[] args) throws Exception {

        List<Mangas> lstMangs = Database.getAllManga();
        System.out.println("Find: " + lstMangs.size() + " record(s)!");

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
        //        Element node = xmlNode.get(0);
        //        String url = VietBoom.BASED_URL + node.attr("href");
        //        url = url.substring(0, url.length() - 1);
        //
        //        int page = 0;
        //        boolean isHasNextPage = true;
        //        while (isHasNextPage) {
        //            page++;
        //            doc = DownloadManager.getDocument(url + page);
        //            xmlNode = (Elements) doc.select("div[class=cellChapter] a");
        //            if (xmlNode.isEmpty()) {
        //                isHasNextPage = false;
        //            } else {
        //                for (Iterator<Element> it = xmlNode.iterator(); it.hasNext();) {
        //                    Element e = it.next();
        //                    System.out.println(e);
        //                }
        //            }
        //        }
        //        System.out.println(xmlNode);
        //
        //            for (String str : arrStr) {
        //                System.out.println(str);
        //            }
        //            Iterator<Element> iElement = xmlNode.iterator();
        //            while (iElement.hasNext()) {
        //                Element e = iElement.next();
        //                System.out.println(e);
        //            }
        //        } catch (IOException ex) {
        //            Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        //        }
    }
}
