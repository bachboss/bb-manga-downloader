/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
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
public abstract class ABusPageBasedDefaultChapPageImage extends ABusPageBasedDefaultChapImage {

    protected abstract Elements getPageQuery(Element htmlTag) throws HtmlParsingException;

    protected abstract Page getPageFromTag(Element htmlTag, Chapter c) throws HtmlParsingException;

    @Override
    public List<Page> getAllPages(Chapter chapter) throws IOException, HtmlParsingException {
        List<Page> lstPage = new ArrayList<Page>();

        Document doc = getDocument(chapter.getUrl());
        Elements xmlNodes = getPageQuery(doc);

        for (Element e : xmlNodes) {
            Page p = getPageFromTag(e, chapter);
            if (p != null) {
                lstPage.add(p);
            }
        }
        return lstPage;
    }
}
