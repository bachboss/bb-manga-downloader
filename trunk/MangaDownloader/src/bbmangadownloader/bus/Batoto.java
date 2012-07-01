/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import bbmangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import bbmangadownloader.entity.*;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.NumberUtilities;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class Batoto extends ABusPageBasedDefaultChapPageImage { // Done

    private static final String URL_LIST_MANGA = "http://www.batoto.net/search_ajax?&p=";
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.US);

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        // TODO: Can improve by using multi thread loading
        List<Manga> lstReturn = new ArrayList<>();
        int i = 0;
        boolean isDone = false;
        do {
            i++;
            Document doc = getDocument(URL_LIST_MANGA + i);
            Elements xmlNodes = doc.select("tr").not("tr[class=header]");
            if (xmlNodes.select("tr[id=show_more_row]").isEmpty()) {
                isDone = true;
            } else {
                for (Element e : xmlNodes) {
                    if (!e.attr("id").equals("show_more_row")) {
                        Element el = e.select("a").first();
                        if (el != null) {
                            Manga m = new Manga(s, el.text(), el.attr("href"));
                            lstReturn.add(m);
                        }
                    }
                }
            }
        } while (!isDone);
        // Remote the i<5 avboce later !
        return lstReturn;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("table[class=ipb_table chapters_list] tr[class*=row]");
    }

    @Override
    protected Elements getPageQuery(Element htmlTag) {
        return htmlTag.select("select[id=page_select]").get(0).select("option");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[id=comic_page]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        Elements nodes = htmlTag.children();
        if (nodes.size() == 5) {
            Element aTag = nodes.first().select("a").first();
            String dateText = nodes.get(4).text();
            MangaDateTime uploadDate;
            try {
                uploadDate = new MangaDateTime(DateTimeUtilities.getDate(dateText, DATE_FORMAT_UPLOAD));
            } catch (ParseException ex) {
                //TODO:  Improve this later, some how like x hours ago :|
                uploadDate = new MangaDateTime(dateText);
            }

            return new Chapter(
                    -1F,
                    aTag.text(),
                    aTag.attr("href"),
                    m,
                    nodes.get(2).text(),
                    uploadDate);
        } else {
            return null;
        }
    }

    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) {
        return new Page(htmlTag.attr("value"), c, NumberUtilities.getNumber(htmlTag.text()),
                htmlTag.attributes().hasKey("selected"));
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c) {
        return new Image(-1, imgNode.attr("src"), c);
    }
}