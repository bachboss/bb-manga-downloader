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
import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class Cococomic extends ADefaultBus implements IBusOnePage {  // Done

    private static NamedPattern PATTERN_SERVER = NamedPattern.compile("\\?.*s=(\\d+)");
    private static NamedPattern PATTERN_PAGE = NamedPattern.compile("ServerList\\[(\\d+)\\]=\"(.+)\";");
    //
    private static final String BASED_URL = "http://www.cococomic.com";
    private static String URL_JS_FILE = "http://www.cococomic.com/o/o.js";
    private static final String URL_LIST_MANGA = "http://www.cococomic.com/sitemap/";
    // 
    private static final String DEFAULT_TRANS = "Cococomic";

    @Override
    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.createConnection(url).charSet("gb2312").getDocument();
    }

    protected String getBaseUrl() {
        return BASED_URL;
    }

    protected String getJSUrl() {
        return URL_JS_FILE;
    }

    protected String getDefaultTranslator() {
        return DEFAULT_TRANS;
    }

    protected String getListMangaUrl() {
        return URL_LIST_MANGA;
    }

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(getListMangaUrl());
        Elements xmlNodes = doc.select("div[id=all]>div:eq(1) a");
        for (Element e : xmlNodes) {
            Manga m = new Manga(s, e.text(), getBaseUrl() + e.attr("href"));
            lstReturn.add(m);
        }
        return lstReturn;
    }

    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[id=main] div[class=c_right] div[class=c_right_content] div[class=c_right_comic] ul li a[target=_blank]");
    }

    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        String url = htmlTag.attr("href");
        MangaDateTime date;
        date = MangaDateTime.NOT_SUPPORT;
        return new Chapter(-1, htmlTag.text(), getBaseUrl() + url, m,
                getDefaultTranslator(), date);
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNodes = getChapterQuery(doc);

        for (Element e : xmlNodes) {
            Chapter c = getChapterFromTag(e, manga);
            if (c != null) {
                lstChapter.add(c);
            }
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<Image>();
        String baseImgUrl = getImageBaseUrl(chapter.getUrl());
        Document doc = getDocument(chapter.getUrl());
        Elements xmlNodes = doc.select("script[language=javascript]");
        for (Element e : xmlNodes) {
            if (e.html().contains("var PicListUrl")) {
                String html = e.html();
                //html = html.substring(html.indexOf("var PicListUrl = \""));
                html = html.substring(html.indexOf("\"") + 1, html.lastIndexOf("\""));
                String[] arrImgLink = html.split("\\|");
                for (int i = 0; i < arrImgLink.length; i++) {
                    lstImage.add(new Image(i, baseImgUrl + arrImgLink[i], chapter, chapter.getUrl()));
                }

                break;
            }
        }
        return lstImage;
    }

    private static String getImageBaseUrl(String chapterUrl) throws IOException {
        String baseUrl = null;
        int server = 1;
        {
            NamedMatcher m = PATTERN_SERVER.matcher(chapterUrl);
            if (m.find()) {
                server = Integer.parseInt(m.group(1));
            }
        }
        {
            InputStream is = HttpDownloadManager.getInputStreamFromUrl(URL_JS_FILE);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            do {
            } while (!(line = br.readLine()).startsWith("ServerList"));

            do {
                NamedMatcher m = PATTERN_PAGE.matcher(line);
                if (m.find()) {
                    int sN = Integer.parseInt(m.group(1));
                    if (sN == (server - 1)) {
                        baseUrl = m.group(2);
                        break;
                    }
                }
            } while ((line = br.readLine()).startsWith("ServerList"));

            try {
                br.close();
                is.close();
            } catch (IOException ex) {
            }
        }
        return baseUrl;
    }
}
