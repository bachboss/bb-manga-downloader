/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ADefaultBus;
import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
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
public class DragonFlyScans extends ADefaultBus implements IBusOnePage {

    private static final String URL_LIST_MANGA = "http://dragonflyscans.org";
    private static final String BASED_URL = "http://dragonflyscans.org";
    private static final String DEFAULT_TRANS = "Dragon & Fly";

    private List<Manga> getMangas(Server s, String mangaName) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(URL_LIST_MANGA);
        Elements xmlNodes = doc.select("div#section.rightcol h3");
        for (Element e : xmlNodes) {
            String mN = e.text();
            if (mangaName == null || mN.equalsIgnoreCase(mangaName)) {
                Manga m = new Manga(s, mN, URL_LIST_MANGA);
                Element eSibling = e.nextElementSibling();
                Elements els2 = eSibling.select("li a");
                for (Element e2 : els2) {
                    Chapter c = new Chapter(-1, e2.text(), e2.attr("href"), m,
                            DEFAULT_TRANS, null);
                    m.addChapter(c);
                }
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
    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
        if (m.getListChapter().isEmpty()) {
            List<Manga> lstManga = getMangas(m.getServer(), m.getMangaName());
            if (lstManga.isEmpty()) {
                throw new HtmlParsingException(m);
            } else {
                return lstManga.get(0).getListChapter();
            }
        } else {
            return m.getListChapter();
        }
    }

    @Override
    public List<Image> getAllImages(Chapter c) throws IOException {
        // Get the image in first page, and others except the 1st one here !
        List<Image> lstImage = new ArrayList<Image>();
        Document doc = getDocument(c.getUrl());

        Elements xmlNodes = doc.select("div.controls[style~=width] div.wrapper").first().select("a");
        for (Element e : xmlNodes) {
            try {
                String url = e.attr("href");
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
        return lstImage;
    }

    private Image getImageFromChapter(Document doc, int order) {
        Element e = doc.select("div#page a img").first();
        Image img = new Image(order, e.attr("src"), null);
        return img;
    }

    private Image getImageFromChapter(String url, int order) throws IOException {
        Document doc = getDocument(url);
        return getImageFromChapter(doc, order);
    }
}
