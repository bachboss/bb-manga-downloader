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
import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class VietBoom extends ADefaultBus implements IBusOnePage {

    private static final String BASED_URL = "http://truyen.vietboom.com";
    private static final String BASED_IMG_URL = "http://truyen.vietboom.com/Resources/Images/Pages/";
    //
    private static final String DEFAULT_TRANS = "VietBoom";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        // TODO: Later
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNode = (Elements) doc.select("a[class=index]");
        Element node = xmlNode.get(0);
        String url = VietBoom.BASED_URL + node.attr("href");
        url = url.substring(0, url.length() - 1);

        int page = 0;
        boolean isHasNextPage = true;
        while (isHasNextPage) {
            page++;
            doc = getDocument(url + page);
            xmlNode = (Elements) doc.select("div[class=cellChapter] a");
            if (xmlNode.isEmpty()) {
                isHasNextPage = false;
            } else {
                for (Iterator<Element> it = xmlNode.iterator(); it.hasNext();) {
                    Element e = it.next();
                    Chapter c = new Chapter(-1, e.text(), BASED_URL + e.attr("href"), manga,
                            DEFAULT_TRANS, MangaDateTime.NOT_SUPPORT);
                    lstChapter.add(c);

                }
            }
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<Image>();
        Document doc = getDocument(chapter.getUrl());

        Elements xmlNode = (Elements) doc.select("script[type=text/javascript]");
        NamedPattern p = NamedPattern.compile(".*,\"imageUrl\":\"(.+)\",\"position.*");
        for (Element e : xmlNode) {
            if (e.html().contains("ChapterPage.listPage")) {
                String text = e.html();
                text = text.substring(text.indexOf('[') + 2);
                text = text.substring(0, text.indexOf(']') - 1);
                String[] arr = text.split("\\},\\{");
                int i = 0;
                for (String str : arr) {
                    NamedMatcher m = p.matcher(str);
                    if (m.matches()) {
                        Image img = new Image(i++, BASED_IMG_URL + m.group(1), chapter);
                        lstImage.add(img);
                    }
                }
                break;
            }
        }
        return lstImage;
    }
}
