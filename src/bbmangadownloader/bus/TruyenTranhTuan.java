/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ADefaultBus;
import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class TruyenTranhTuan extends ADefaultBus implements IBusOnePage { // Done

    private static final String BASED_URL = "http://truyentranhtuan.com";
    private static final String URL_LIST_MANGA = "http://truyentranhtuan.com/danh-sach-truyen/";
    private static final String DATE_FORMAT_UPLOAD = "dd.MM.yyyy";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(URL_LIST_MANGA);

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
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNodes = doc.select("html body div#nos-web.wrapper div.container div.inner div.content div#container6 div#container5 "
                + "div#container4 div#container3 div#container2 div#container1"
                + " div#main-content div#manga-chapter span.chapter-name");

        for (Element e : xmlNodes) {
            Element aTag = e.select("a").first();
            Chapter c;
            Element eTrans = e.nextElementSibling();
            Element eDate = eTrans.nextElementSibling();
            String strDate = eDate.text();
            MangaDateTime date;
            try {
                date = new MangaDateTime(DateTimeUtilities.getDate(strDate, DATE_FORMAT_UPLOAD));
            } catch (ParseException ex) {
                date = MangaDateTime.NOT_AVAIABLE;
            }
            c = new Chapter(-1, aTag.text(), aTag.attr("href"), manga,
                    eTrans.text(),
                    date);
            lstChapter.add(c);
        }

        return lstChapter;
    }

    public int getNumberOfImages(Chapter chapter) throws IOException {
        Document doc = getDocument(chapter.getUrl());
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
            Document doc = getDocument(chapter.getUrl());
            Elements xmlNode = doc.select("script[type=text/javascript]");
            Element selectE = null;
            for (Element e : xmlNode) {
                if (e.html().contains("var slides_page")) {
                    selectE = e;
                    break;
                }
            }
            String text = selectE.html();
            arrStr = text.split("\n");
            for (String str : arrStr) {
                if (str.contains("var slides_page")) {
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
        List<Image> lstImage = new ArrayList<Image>();
        if (arrStr != null) {
            for (int i = 0; i < arrStr.length; i++) {
                lstImage.add(new Image(i, BASED_URL + arrStr[i], chapter));
            }
        }

        return lstImage;
    }
}
