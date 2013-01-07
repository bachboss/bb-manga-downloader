/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import bbmangadownloader.entity.*;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.MultitaskJob;
import bbmangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class Batoto extends ABusPageBasedDefaultChapPageImage { // Done

    private static final int DEFAULT_LOAD_PAGE = 3;
    private static final String URL_LIST_MANGA = "http://www.batoto.net/search_ajax?&p=";
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.US);

    @Override
    public List<Manga> getAllMangas(final Server s) throws IOException {
        // TODO: Can improve by using multi thread loading
        final List<Manga> lstReturn = new ArrayList<Manga>();
        int i = 0;
        boolean isDone = false;
        do {
            List<Callable<Boolean>> lstTask = new ArrayList<Callable<Boolean>>();
            for (int x = 0; x <= DEFAULT_LOAD_PAGE; x++) {
                i++;
                lstTask.add(new CallableImpl(i, s, lstReturn));
            }

            List<Future<Boolean>> lstF = MultitaskJob.doTask(DEFAULT_LOAD_PAGE, lstTask);
            for (Future<Boolean> f : lstF) {
                if (f.isDone()) {
                    try {
                        if (f.get() == Boolean.TRUE) {
                            isDone = true;
                            continue;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Batoto.class.getName()).log(Level.SEVERE, null, ex);
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
        return new Page(htmlTag.attr("value"), c, NumberUtilities.getNumberInt(htmlTag.text()),
                htmlTag.attributes().hasKey("selected"));
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) {
        return new Image(p.getPageOrder(), imgNode.attr("src"), c);
    }

    private class CallableImpl implements Callable<Boolean> {

        private final Server s;
        int i;
        List<Manga> lstManga;

        private CallableImpl(int i, Server s, List<Manga> lstReturn) {
            this.i = i;
            this.s = s;
            this.lstManga = lstReturn;
        }

        @Override
        public Boolean call() throws Exception {
            Document doc = getDocument(URL_LIST_MANGA + i);
            Elements xmlNodes = doc.select("tr").not("tr[class=header]");
            if (xmlNodes.select("tr[id=show_more_row]").isEmpty()) {
                return Boolean.TRUE;
//                                isDone = true;
            } else {
                for (Element e : xmlNodes) {
                    if (!e.attr("id").equals("show_more_row")) {
                        Element el = e.select("a").first();
                        if (el != null) {
                            Manga m = new Manga(s, el.text(), el.attr("href"));
                            lstManga.add(m);
                        }
                    }
                }
            }
            return Boolean.FALSE;
        }
    }
}