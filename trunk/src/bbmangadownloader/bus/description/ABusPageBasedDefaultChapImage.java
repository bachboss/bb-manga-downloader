/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Page;
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
public abstract class ABusPageBasedDefaultChapImage extends ABusPageBasedDefault {

    protected abstract Element getImageQuery(Element imgNode) throws HtmlParsingException;

    protected abstract Image getImageFromTag(Element imgNode, Chapter c, Page p) throws HtmlParsingException;

    protected abstract Elements getChapterQuery(Element htmlTag) throws HtmlParsingException;

    protected abstract Chapter getChapterFromTag(Element htmlTag, Manga m) throws HtmlParsingException;

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException, HtmlParsingException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

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
    public Image getImage(Page page, Document doc) throws IOException, HtmlParsingException {
        Element xmlNode = getImageQuery(doc);
        if (xmlNode != null) {
            return getImageFromTag(xmlNode, page.getChapter(), page);
        } else {
            return null;
        }
    }
}
