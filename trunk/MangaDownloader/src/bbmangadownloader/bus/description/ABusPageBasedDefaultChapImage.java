/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus.description;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Manga;
import mangadownloader.entity.Page;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public abstract class ABusPageBasedDefaultChapImage extends ABusPageBasedDefault {

    protected abstract Element getImageQuery(Element imgNode);

    protected abstract Image getImageFromTag(Element imgNode, Chapter c);

    protected abstract Elements getChapterQuery(Element htmlTag);

    protected abstract Chapter getChapterFromTag(Element htmlTag, Manga m);

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNodes = getChapterQuery(doc);

        for (Element e : xmlNodes) {
            Chapter c = getChapterFromTag(e, manga);
            if (c != null) {
                lstChapter.add(c);
            }
        }

        return lstChapter;
    }

    @Override
    public Image getImage(Page p) throws IOException {
        Document doc = getDocument(p.getUrl());
        Element xmlNode = getImageQuery(doc);
        if (xmlNode != null) {
            return getImageFromTag(xmlNode, p.getChapter());
        } else {
            return null;
        }
    }
}
