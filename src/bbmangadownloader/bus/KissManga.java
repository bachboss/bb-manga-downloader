/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ADefaultBus;
import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.HttpDownloadManager;
import bbmangadownloader.ult.MultitaskJob;
import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
public class KissManga extends ADefaultBus implements IBusOnePage {  // Done 
    // Thiss class have a subclass: VnSharing

    private static final NamedPattern PATTERN_IMAGE = NamedPattern.compile("lstImages.push\\(\"(http://.+)\"\\);");
    // 
    private static final String BASED_URL = "http://kissmanga.com";
    private static final String BASED_URL_LIST_MANGA = "http://kissmanga.com/MangaList?page=";
    private static final String URL_LIST_MANGA = "http://kissmanga.com/MangaList";
    //    
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MM/dd/yyyy");
    private static final String DEFAULT_TRANS = "KissManga";
    private static final String DEFAULT_COOKIE = "vns_Adult=yes;";

    @Override
    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.createConnection(url).
                cookie(DEFAULT_COOKIE).charSet("UTF-8").
                getDocument();
    }

    protected String getBasedUrl() {
        return BASED_URL;
    }

    protected String getBasedUrlListManga() {
        return BASED_URL_LIST_MANGA;
    }

    protected String getMangaListUrl() {
        return URL_LIST_MANGA;
    }

    protected Manga getMangaFromTag(Element e, Server server) {
        if (e.attr("class").equals("odd") || e.attributes().size() == 0) {
            Element el = e.children().first().child(0);
            Manga m = new Manga(server, el.text(), getBasedUrl() + el.attr("href"));
            return m;
        } else {
            return null;
        }
    }

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Callable<List<Manga>>> lstTask = new ArrayList<Callable<List<Manga>>>();
        Document doc = getDocument(getMangaListUrl());
        Elements xmlNodes = doc.select("div[class=pagination pagination-left] a");
        try {
            int page = Integer.parseInt(xmlNodes.last().attr("page"));

            for (int i = 1; i <= page; i++) {
                EatMangaMangaLoaderTask task = new EatMangaMangaLoaderTask(getBasedUrlListManga() + i, s);
                lstTask.add(task);
            }

            List<Future<List<Manga>>> lstLst = MultitaskJob.doTask(lstTask);
            List<Manga> lstReturn = new ArrayList<Manga>();

            for (Future<List<Manga>> f : lstLst) {
                if (f.isDone()) {
                    try {
                        lstReturn.addAll(f.get());
                    } catch (Exception ex) {
                        Logger.getLogger(KissManga.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("WTF ?");
                }
            }
            return lstReturn;
        } catch (NumberFormatException ex) {
            System.out.println("Can not get number page");
            return null;
        }
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException, HtmlParsingException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());

        Elements xmlNode = doc.select("div[class=barContent chapterList] table[class=listing] tr");

        if (xmlNode.isEmpty()) {
            throw new HtmlParsingException(manga);
        }

        xmlNode.remove(0);

        for (Element e : xmlNode) {
            Elements nodes = e.children();
            if (nodes.size() == 2) {
                Element aTag = nodes.get(0).select("a").first();
                MangaDateTime date;
                try {
                    date = new MangaDateTime(DateTimeUtilities.getDate(nodes.get(1).text(), DATE_FORMAT_UPLOAD));
                } catch (ParseException ex) {
                    // TODO: Rarely !
                    date = new MangaDateTime("-");
                }
                Chapter c;
                c = new Chapter(
                        -1,
                        aTag.text(), BASED_URL + aTag.attr("href"), manga,
                        DEFAULT_TRANS, date);
                lstChapter.add(c);

            }
        }
        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<Image>();
        Document doc = getDocument(chapter.getUrl());
        Elements xmlNodes = doc.select("script[type=text/javascript]").not("script[src]");

        for (Element e : xmlNodes) {
            if (e.html().contains("var lstImages = new Array()")) {
                NamedMatcher m = PATTERN_IMAGE.matcher(e.html());
                int i = 0;
                int start = 0;
                while (m.find(start)) {
                    lstImage.add(new Image(i++, m.group(1), chapter));
                    start = m.end();
                }
                break;
            }
        }

        return lstImage;


    }

    protected class EatMangaMangaLoaderTask implements Callable<List<Manga>> {

        private String url;
        private Server server;
//        private KissManga s;

        public EatMangaMangaLoaderTask(String url, Server server) {
            this.url = url;
            this.server = server;
        }

        @Override
        public List<Manga> call() throws Exception {
            List<Manga> lstReturn = new ArrayList<Manga>();
            Document doc = getDocument(url);
            Elements xmlNodes = doc.select("table[class=listing] tr");
            for (Element e : xmlNodes) {
                Manga m = getMangaFromTag(e, server);
                if (m != null) {
                    lstReturn.add(m);
                }
            }
            return lstReturn;
        }
    }
}
