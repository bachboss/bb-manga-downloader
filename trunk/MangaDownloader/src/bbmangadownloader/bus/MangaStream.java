/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class MangaStream implements IBus {

    private static final String BASED_URL = "http://mangastream.com";
    private static final String URL_LIST_MANGA = "http://mangastream.com/manga";
    private static final String DEFAULT_TRANS = "MangaStream";

    private Document getDocument(String url) throws IOException {
        return HttpDownloadManager.getDocument(url);
    }

    private List<Manga> getMangas(Server s, String mangaName) throws IOException {
        List<Manga> lstReturn = new ArrayList<>();
        Document doc = getDocument(URL_LIST_MANGA);
        Elements xmlNodes = doc.select("td strong");
        for (Element e : xmlNodes) {
            String mN = e.text();
            if (mangaName == null ^ mN.equalsIgnoreCase(mangaName)) {
                Manga m = new Manga(s, mN, URL_LIST_MANGA);
                Element e2 = e.nextElementSibling();
                do {
                    if (e2.tagName().equals("a")) {
                        // This host does not have upload time
                        Chapter c = new Chapter(-1, e2.text(), BASED_URL + e2.attr("href"), m,
                                DEFAULT_TRANS, null);
                        m.addChapter(c);
                    }
                    e2 = e2.nextElementSibling();
                } while (e2 != null && !e2.tagName().equals("strong"));
                lstReturn.add(m);
                if (mangaName != null) {
                    return lstReturn;
                }
            }
        }
        return lstReturn;
    }

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        return getMangas(s, null);
    }

    @Override
    public List<Chapter> getAllChapters(Manga m) throws IOException {
        if (m.getListChapter().isEmpty()) {
            System.out.println("Get for manga: " + m.getMangaName());
            return getMangas(m.getServer(), m.getMangaName()).get(0).getListChapter();
        } else {
            return m.getListChapter();
        }
    }

    @Override
    public List<Image> getAllImages(Chapter c) throws IOException {
        // Get the image in first page, and others except the 1st one here !
        List<Image> lstImage = new ArrayList<>();
        Document doc = getDocument(c.getUrl());
        Elements xmlNodes = doc.select("div[id=controls] a").not("a[href~=javascript]");
        for (Element e : xmlNodes) {
            try {
                String url = BASED_URL + e.attr("href");
                int order = Integer.parseInt(e.html());
                Image img;
                if (order == 1) {
                    img = getImageFromChapter(doc, order);
                } else {
                    img = getImageFromChapter(url, order);
                }
                lstImage.add(img);
            } catch (NumberFormatException ex) {
            }
        }
        return null;
    }

    private Image getImageFromChapter(Document doc, int order) {
        Element e = doc.select("div[id=page] img").first();
        Image img = new Image(order, e.attr("src"), null);
        return img;
    }

    private Image getImageFromChapter(String url, int order) throws IOException {
        Document doc = getDocument(url);
        return getImageFromChapter(doc, order);
    }
}
