/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.ult.MultitaskJob;
import bbmangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class _99770 extends Cococomic {

    private static final String BASED_URL = "http://www.cococomic.com";
    private static final String URL_JS_FILE = "http://mh.99770.cc/x/i.js";
    private static final String DEFAULT_TRANS = "99770";
    private static final int DEFAULT_POOL_SIZE = 3;
    private static final String DEFAULT_LIST_ABC = "http://mh.99770.cc/ComicABC/";

    private static char[] getAllChars() {
        char[] charArr = new char['Z' - 'A' + 2];
        for (char c = 'A'; c <= 'Z'; c++) {
            charArr[c - 'A'] = c;
        }
        charArr[charArr.length - 1] = '1';
        return charArr;
    }

    private void getMangasFromPage(Document doc, Server s, List<Manga> lstManga) {
        Elements elements = doc.select("div[class=m_list] ul[id=summary][class=list] li[class=list_small] a:eq(0)");
        for (Element e : elements) {
            lstManga.add(new Manga(s, e.attr("title"), BASED_URL + e.attr("href")));
        }
    }

    @Override
    public List<Manga> getAllMangas(final Server s) throws IOException {
        final List<Manga> lstReturn = new ArrayList<>();
        final List<String> lstUrl = new ArrayList<>();
        List<Callable<Object>> lstTask = new ArrayList<>();

        char[] arrChar = getAllChars();
        for (final char c : arrChar) {
            lstTask.add(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    String url = DEFAULT_LIST_ABC + c + "/";

                    Document doc = getDocument(url);
                    Elements elements = doc.select("script[language=javascript]");
                    int numberOfPage = 1;
                    if (elements.html().contains("var datas=")) {
                        numberOfPage = NumberUtilities.getNumberInt(elements.html());
                    }
                    // Get First Page !
                    getMangasFromPage(doc, s, lstReturn);
                    // Pending for later !
                    for (int i = 2; i < numberOfPage; i++) {
                        lstUrl.add(url + i + ".htm?v=" + i);
                    }
                    return null;
                }
            });
        }

        MultitaskJob.doTask(DEFAULT_POOL_SIZE, lstTask);
        // Do all the pending !
        lstTask.clear();
        for (final String url : lstUrl) {
            lstTask.add(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    Document doc = getDocument(url);
                    getMangasFromPage(doc, s, lstReturn);
                    return null;
                }
            });
        }
        MultitaskJob.doTask(DEFAULT_POOL_SIZE, lstTask);

        return lstReturn;
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

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[id=content] div[class=vol] li a:eq(0)");
    }
}
