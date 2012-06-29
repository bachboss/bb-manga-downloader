/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Page;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public abstract class ABusPageBasedDefaultChapPageImage extends ABusPageBasedDefaultChapImage {

    protected abstract Elements getPageQuery(Element htmlTag);

    protected abstract Page getPageFromTag(Element htmlTag, Chapter c);

    @Override
    public List<Page> getAllPages(Chapter chapter) throws IOException {
        List<Page> lstPage = new ArrayList<>();

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
