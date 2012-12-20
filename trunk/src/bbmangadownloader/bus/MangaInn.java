/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ABusPageBasedDefaultChapImage;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class MangaInn extends ABusPageBasedDefaultChapImage { // Done

    private static final String PAGE_URL_FORMAT = "http://www.mangainn.com/manga/chapter/%s/page_%s";
    private static final String URL_LIST_MANGA = "http://www.mangainn.com/MangaList";
    //
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MM.dd.yyyy");
    private static final String DEFAULT_TRANS = "MangaInn";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(URL_LIST_MANGA);

        Elements xmlNodes = doc.select("ul[class=mangalist] li a");

        for (Element e : xmlNodes) {
            Manga m = new Manga(s, e.text(), e.attr("href"));
            lstReturn.add(m);
        }
        return lstReturn;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[class~=divThickBorder] table[width=100%] tr");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[id=imgPage]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        //td span a
        if (htmlTag.children().size() == 2) {
            Elements aTags = htmlTag.select("td span a");
            if (aTags.isEmpty()) {
                return null;
            }
            try {
                MangaDateTime date = new MangaDateTime(DateTimeUtilities.getDate(htmlTag.child(1).text(), DATE_FORMAT_UPLOAD));
                Element aTag = aTags.first();
                return new Chapter(-1, aTag.text(), aTag.attr("href"), m,
                        DEFAULT_TRANS, date);
            } catch (ParseException ex) {
            }
        }
        return null;
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) {
        return new Image(p.getPageOrder(), imgNode.attr("src"), c);
    }

    @Override
    public final List<Page> getAllPages(Chapter chapter, Document doc) throws IOException {
        ArrayList<Page> lstPage = new ArrayList<Page>();

        // s='http://www.mangainn.com/manga/chapter/'+$('#chapters :selected').val()+'/page_'+id;        
        Elements xmlNodes = doc.select("select[id~=chapters] option[selected]");
        String chapterValue = xmlNodes.first().attr("value");

        xmlNodes = doc.select("select[id=cmbpages] option");
        for (Element e : xmlNodes) {
            Page p = new Page(String.format(PAGE_URL_FORMAT, chapterValue, e.attr("value")), chapter,
                    NumberUtilities.getNumberInt(e.text()),
                    e.attributes().hasKey("selected"));
            lstPage.add(p);
        }
        return lstPage;
    }
}
