/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ADefaultBus;
import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.manager.HttpDownloadManager;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.HtmlUtilities;
import bbmangadownloader.ult.TextUtilities;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class Manga24h extends ADefaultBus implements IBusOnePage { // Done

    private static final String BASED_URL = "http://manga24h.com/";
    private static final String URL_LIST_MANGA = "http://manga24h.com/manga/list";
    private static final String URL_LIST_MANGA_BASED = "http://manga24h.com/manga/list/page/";
    private static final String DATE_FORMAT_UPLOAD = "yyyy-MM-dd";
    private static final String DEFAULT_TRANS = "Manga24h";
    //
    private static final String COOKIE_DEFAULT = "location.href;";

    @Override
    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.createConnection(url).cookie(COOKIE_DEFAULT).getDocument();
    }

    private void getMangas(Server s, Document doc, List<Manga> listManga) {
        Elements elements = doc.select("div[id=left_content] div[class=post]");
        for (Element eManga : elements) {
            Element aTag = eManga.select("a").first();
            String mangaName = aTag.text();
            String mangaUrl = BASED_URL + aTag.attr("href");
            listManga.add(new Manga(s, mangaName, mangaUrl));
        }
    }

    @Override
    public List<Manga> getAllMangas(final Server s) throws IOException {
        final List<Manga> lstReturn = new ArrayList<Manga>();

        final Document doc = getDocument(URL_LIST_MANGA);
        // get number of page
//        int numberOfPage = 1;
//        {
//            Elements xmlNodes = doc.select("div[id=left_content] div[class=nav] span[class=inactive] a");
//            Element aTag = xmlNodes.get(xmlNodes.size() - 1);
//            String number = aTag.attr("href");
//            number = number.substring(number.lastIndexOf('/') + 1);
//            System.out.println("Number = " + number);
//            numberOfPage = Math.max(NumberUtilities.getNumberInt(number), numberOfPage);
//        }
//
//        List<Callable<Object>> lstTask = new ArrayList<>();
//        lstTask.add(new Callable<Object>() {
//
//            @Override
//            public Object call() throws Exception {
//                getMangas(s, doc, lstReturn);
//                return null;
//            }
//        });
//
//        for (int i = 2; i <= numberOfPage; i++) {
//            lstTask.add(new CallableImp(s, URL_LIST_MANGA_BASED + i, lstReturn));
//        }
//
//        MultitaskJob.doTask(lstTask);
        getMangas(s, doc, lstReturn);

        return lstReturn;
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNodes = doc.select("div[class=post] table[class=mytable] tr:gt(0)");
        for (Element e : xmlNodes) {
            Elements children = e.children();
            Element aTag = children.first().select("a").first();
            Element eDate = children.last();
            Chapter c;
            String dateString = TextUtilities.trim(HtmlUtilities.unescapeHtml4(eDate.text()));
            MangaDateTime date;
            try {
                date = new MangaDateTime(
                        DateTimeUtilities.getDate(dateString, DATE_FORMAT_UPLOAD));
            } catch (ParseException ex) {
                date = new MangaDateTime(dateString);
            }
            c = new Chapter(-1, aTag.text(), BASED_URL + aTag.attr("href"), manga, DEFAULT_TRANS, date);
            lstChapter.add(c);
        }
        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<Image>();
        Document doc = getDocument(chapter.getUrl());
        Elements xmlNode = doc.select("img[class=m_picture][alt]");

        for (int i = 0; i < xmlNode.size(); i++) {
            lstImage.add(new Image(i, xmlNode.get(i).attr("src"), chapter));
        }

        return lstImage;
    }
//    private class CallableImp implements Callable<Object> {
//
//        private List<Manga> listManga;
//        private String url;
//        private Server server;
//
//        public CallableImp(Server s, String url, List<Manga> listManga) {
//            this.server = s;
//            this.url = url;
//            this.listManga = listManga;
//        }
//
//        @Override
//        public Object call() throws Exception {
//            Document doc = getDocument(url);
//            getMangas(server, doc, listManga);
//            return null;
//        }
//    }
}
