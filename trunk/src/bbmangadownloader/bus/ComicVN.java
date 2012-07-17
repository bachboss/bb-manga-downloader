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
import bbmangadownloader.ult.HtmlUtilities;
import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;
import java.util.ArrayList;
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
public class ComicVN implements IBusOnePage {

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();

        Document doc = HttpDownloadManager.getDocument(manga.getUrl());
        Elements xmlNode = doc.select("div[class=tblog-post] center a[href*=.html]").not("a[href~=jpg|adf.ly]");

        Iterator<Element> iChapter = xmlNode.iterator();
        while (iChapter.hasNext()) {
            Element e = iChapter.next();
            Chapter c = new Chapter(-1, e.text(), e.attr("href"), manga);
            lstChapter.add(c);
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<>();

        Document doc = HttpDownloadManager.getDocument(chapter.getUrl());
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
