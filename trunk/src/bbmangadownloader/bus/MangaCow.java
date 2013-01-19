/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ABusPageBasedDefaultChapImage;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Page;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class MangaCow extends ABusPageBasedDefaultChapImage {

    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MMM dd, yyyy");
    private static final String DEFAULT_TRANS = "NoManga";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
        // TODO: Later !
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) throws HtmlParsingException {
        return htmlTag.select("body div.wrap div#sct_col_l div#sct_content div.con div.wpm_pag ul.lst li.lng_");
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) throws HtmlParsingException {
        String dateStr = htmlTag.select(".dte").text();
        MangaDateTime date;
        try {
            date = new MangaDateTime(DateTimeUtilities.getDate(dateStr, DATE_FORMAT_UPLOAD));
        } catch (ParseException ex) {
            date = new MangaDateTime(dateStr);
        }

        Chapter c = new Chapter(-1, htmlTag.select(".val").first().text(),
                htmlTag.select("a").attr("href"),
                m,
                DEFAULT_TRANS,
                date);
        return c;
    }

    protected Elements getPageQuery(Element htmlTag) throws HtmlParsingException {
        return htmlTag.select("html body div#sct_col_l.full_width div#sct_content.full_width div.con div.wpm_pag div.wpm_nav ul.nav_pag li select.cbo_wpm_pag").
                first().select("option");

    }

    protected Page getPageFromTag(Element htmlTag, Chapter c) throws HtmlParsingException {
        return new Page(htmlTag.attr("value"), c, NumberUtilities.getNumberInt(htmlTag.text()),
                htmlTag.attributes().hasKey("selected"));
    }

    @Override
    protected Element getImageQuery(Element imgNode) throws HtmlParsingException {
        return imgNode.select("img#img_mng_enl").first();
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) throws HtmlParsingException {
        return new Image(p.getPageOrder(), imgNode.attr("src"), c);
    }

    @Override
    public List<Page> getAllPages(Chapter chapter, Document doc) throws IOException, HtmlParsingException {
        ArrayList<Page> lstPage = new ArrayList<Page>();

        String url = chapter.getUrl();
        String baseLink = url.substring(0, url.lastIndexOf("/") + 1);

        Elements xmlNode = getPageQuery(doc);
        Iterator<Element> iElement = xmlNode.iterator();
        while (iElement.hasNext()) {
            Element e = iElement.next();
            Page p = new Page(baseLink + e.attr("value") + ".html", chapter,
                    NumberUtilities.getNumberInt(e.text()),
                    e.attributes().hasKey("selected"));
            lstPage.add(p);
        }

        return lstPage;
    }
}
