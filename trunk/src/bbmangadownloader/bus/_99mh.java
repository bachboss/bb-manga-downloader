/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.notmaintained.Cococomic;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.manager.HttpDownloadManager;
import bbmangadownloader.ult.DateTimeUtilities;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class _99mh extends Cococomic {

    private static final String BASED_URL = "http://99mh.com";
    private static final String URL_JS_FILE = "http://99mh.com/x/i.js";
    private static final String DEFAULT_TRANS = "99mh";
    private static final String DEFAULT_LIST_ABC = "http://99mh.com/ComicABC/";
    //
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.createConnection(url).charSet("UTF-8").getDocument();
    }

//    private static char[] getAllChars() {
//        char[] charArr = new char['Z' - 'A' + 2];
//        for (char c = 'A'; c <= 'Z'; c++) {
//            charArr[c - 'A'] = c;
//        }
//        charArr[charArr.length - 1] = '1';
//        return charArr;
//    }
//    private void getMangasFromPage(Document doc, Server s, List<Manga> lstManga) {
//        Elements elements = doc.select("div[class=m_list] ul[id=summary][class=list] li[class=list_small] a:eq(0)");
//        for (Element e : elements) {
//            lstManga.add(new Manga(s, e.attr("title"), getBaseUrl() + e.attr("href")));
//        }
//    }
    @Override
    public List<Manga> getAllMangas(final Server s) throws IOException {
//        final List<Manga> lstReturn = new ArrayList<Manga>();
//        final List<String> lstUrl = new ArrayList<String>();
//        List<Callable<Object>> lstTask = new ArrayList<Callable<Object>>();
//
//        char[] arrChar = getAllChars();
//        for (final char c : arrChar) {
//            lstTask.add(new Callable<Object>() {
//                @Override
//                public Object call() throws Exception {
//                    String url = getDefaultList() + c + "/";
//
//                    Document doc = getDocument(url);
//                    Elements elements = doc.select("script[language=javascript]");
//                    int numberOfPage = 1;
//                    if (elements.html().contains("var datas=")) {
//                        numberOfPage = NumberUtilities.getNumberInt(elements.html());
//                    }
//                    // Get First Page !
//                    getMangasFromPage(doc, s, lstReturn);
//                    // Pending for later !
//                    for (int i = 2; i < numberOfPage; i++) {
//                        lstUrl.add(url + i + ".htm?v=" + i);
//                    }
//                    return null;
//                }
//            });
//        }
//
//        MultitaskJob.doTask(lstTask);
//        // Do all the pending !
//        lstTask.clear();
//        for (final String url : lstUrl) {
//            lstTask.add(new Callable<Object>() {
//                @Override
//                public Object call() throws Exception {
//                    Document doc = getDocument(url);
//                    getMangasFromPage(doc, s, lstReturn);
//                    return null;
//                }
//            });
//        }
//        MultitaskJob.doTask(lstTask);
//
//        return lstReturn;
        throw new UnsupportedOperationException("...");
    }

    @Override
    protected String getBaseUrl() {
        return BASED_URL;
    }

    @Override
    protected String getJSUrl() {
        return URL_JS_FILE;
    }

    @Override
    protected String getDefaultTranslator() {
        return DEFAULT_TRANS;
    }

    protected String getDefaultList() {
        return DEFAULT_LIST_ABC;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("html body div#subBookListVol.cVolList div");
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        Element eA = htmlTag.select("a").first();
        String url = eA.attr("href");
        String sUpdateloadDate = eA.attr("title").trim();
        sUpdateloadDate = sUpdateloadDate.substring(sUpdateloadDate.indexOf(" ")).trim();
        MangaDateTime date = MangaDateTime.NOT_SUPPORT;
        try {
            Date d = DateTimeUtilities.getDate(sUpdateloadDate, DATE_FORMAT_UPLOAD);
            date = new MangaDateTime(d);
        } catch (Exception ex) {
        }
        return new Chapter(-1, eA.text(), getBaseUrl() + url, m,
                getDefaultTranslator(), date);
    }
}
