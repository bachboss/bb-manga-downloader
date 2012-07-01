/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class TruyenTranhTuan implements IBusOnePage { // Done

    private static final String BASED_URL = "http://truyentranhtuan.com";
    private static final String URL_LIST_MANGA = "http://truyentranhtuan.com/danh-sach-truyen/";
    private static final String DATE_FORMAT_UPLOAD = "dd-MM-yyyy";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<>();
        Document doc = HttpDownloadManager.getDocument(URL_LIST_MANGA);

        Elements xmlNodes = doc.select("div[id=content-main] table tr[class]");

        for (Element e : xmlNodes) {
            Element node = e.child(0).child(0);
            if (node.nodeName().equals("a")) {
                Manga m = new Manga(s, node.text(), node.attr("href"));
                lstReturn.add(m);
            }
        }
        return lstReturn;
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();

        Document doc = HttpDownloadManager.getDocument(manga.getUrl());
        Elements xmlNodes = doc.select("div[id=content-main] tr[class]");

        for (Element e : xmlNodes) {
            Elements children = e.children();
            if (children.size() == 3) {
                Element aTag = children.get(0).select("a").first();
                Chapter c;
                try {
                    c = new Chapter(-1, aTag.html(), BASED_URL + aTag.attr("href"), manga,
                            children.get(1).text(),
                            new MangaDateTime(DateTimeUtilities.getDate(children.get(2).text(), DATE_FORMAT_UPLOAD)));
                    lstChapter.add(c);
                } catch (ParseException ex) {
                    Logger.getLogger(TruyenTranhTuan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return lstChapter;
    }

    public int getNumberOfImages(Chapter chapter) throws IOException {
        Document doc = HttpDownloadManager.getDocument(chapter.getUrl());
        Elements xmlNode = doc.select("select[class=m]").get(0).select("option");
        Iterator<Element> iElement = xmlNode.iterator();
        int pages = -1;
        while (iElement.hasNext()) {
            Element e = iElement.next();
            int x;
            try {
                if ((x = Integer.parseInt(e.html())) > pages) {
                    pages = x;
                }
            } catch (NumberFormatException ex) {
            }
        }
        return pages;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        String[] arrStr;
        {
            Document doc = HttpDownloadManager.getDocument(chapter.getUrl());
            Elements xmlNode = doc.select("script[type=text/javascript]");
            Element selectE = null;
            for (Element e : xmlNode) {
                if (e.html().contains("slides2")) {
                    selectE = e;
                    break;
                }
            }
            String text = selectE.html();
            arrStr = text.split("\n");
            for (String str : arrStr) {
                if (!str.startsWith("//") && str.startsWith("var slide")) {
                    text = str;
                    break;
                }
            }

            text = text.substring(text.indexOf("[") + 1, text.indexOf("]"));
            arrStr = text.split("\",\"");
            arrStr[0] = arrStr[0].substring(1);
            String s = arrStr[arrStr.length - 1];
            arrStr[arrStr.length - 1] = s.substring(0, s.length() - 1);
            Arrays.sort(arrStr);
        }
        List<Image> lstImage = new ArrayList<>();
        if (arrStr != null) {
            for (int i = 0; i < arrStr.length; i++) {
                lstImage.add(new Image(i, BASED_URL + arrStr[i], chapter));
            }
        }

        return lstImage;
    }
}
