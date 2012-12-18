/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Page;
import bbmangadownloader.entity.Server;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class NoManga extends ABusPageBasedDefaultChapPageImage {

    @Override
    protected Elements getChapterQuery(Element htmlTag) throws HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) throws HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
        // TODO: Later !
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Elements getPageQuery(Element htmlTag) throws HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) throws HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Element getImageQuery(Element imgNode) throws HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c) throws HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
