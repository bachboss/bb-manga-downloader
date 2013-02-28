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
import bbmangadownloader.ult.HtmlUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class ComicVN extends ADefaultBus implements IBusOnePage {

    private static final String BASED_URL = "http://comicvn.net/";
    private static final String DEFAULT_TRANS = "Not Support";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        // TODO: Do later!
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNode = doc.select("div[id=list_table_truyen] table[class=listing] tr[class=table_body]");

        Iterator<Element> iChapter = xmlNode.iterator();
        while (iChapter.hasNext()) {
            Element e = iChapter.next();
            long date = Long.valueOf(e.select("span[class=up_date chap_moi_time]").attr("rel"));
            date *= 1000;
            Element eAtag = e.select("td[class=chap_title] a").first();
            Chapter c = new Chapter(-1, eAtag.text(), BASED_URL + eAtag.attr("href"), manga, DEFAULT_TRANS,
                    new MangaDateTime(new Date(date)));
            lstChapter.add(c);
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<Image>();

        Document doc = getDocument(chapter.getUrl());
        Elements xmlNodes = doc.select("textarea[id=txtarea]");
        String text = HtmlUtilities.unescapeHtml4(xmlNodes.get(0).html());
        doc = Jsoup.parse(text);
        xmlNodes = doc.select("img");
        for (int i = 0; i < xmlNodes.size(); i++) {
            Image img = new Image(i, xmlNodes.get(i).attr("src"), chapter);
            lstImage.add(img);
        }

        return lstImage;
    }
}
