/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.*;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class MangaHere extends ABusPageBasedDefaultChapPageImage {

    private static final String URL_LIST_MANGA = "http://www.mangahere.com/mangalist/";
    //
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    private static final String DEFAULT_TRANS = "MangaHere";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<>();
        Document doc = getDocument(URL_LIST_MANGA);

        Elements xmlNodes = doc.select("div[class=list_manga] li a");

        for (Element e : xmlNodes) {
            Manga m = new Manga(s, e.text(), e.attr("href"));
            lstReturn.add(m);
        }
        return lstReturn;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[class=detail_list] ul li");
    }

    @Override
    protected Elements getPageQuery(Element htmlTag) {
        return htmlTag.select("div[class=go_page clearfix] span[class=right] select").first().select("option");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[id=image]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) throws HtmlParsingException {
        Element pTag = htmlTag.select("span[class=left]").first();
        if (pTag != null) {
            Element aTag = pTag.select("a[href]").first();
            Element dateTag = htmlTag.select("span[class=right]").first();
            try {
                MangaDateTime date = new MangaDateTime(DateTimeUtilities.getDate(dateTag.html(), DATE_FORMAT_UPLOAD));
                String chapterName = pTag.text().substring(aTag.text().length());
                float chapterNumber = NumberUtilities.parseNumberFloat(aTag.text().substring((aTag.text().lastIndexOf(' '))));
                return new Chapter(chapterNumber, chapterName, aTag.attr("href"), m, DEFAULT_TRANS, date);
            } catch (ParseException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) {
        return new Page(htmlTag.attr("value"), c, NumberUtilities.getNumberInt(htmlTag.text()),
                htmlTag.attributes().hasKey("selected"));
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c) {
        return new Image(-1, imgNode.attr("src"), c);
    }
}
