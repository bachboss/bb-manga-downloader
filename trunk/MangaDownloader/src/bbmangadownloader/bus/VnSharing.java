/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mangadownloader.config.ConfigManager;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Manga;
import mangadownloader.ult.DateTimeUtilities;
import mangadownloader.ult.HttpDownloadManager;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class VnSharing extends KissManga { // Done

    private static final String CONFIG_PICASA = "vns.downloadFromPicasa";
    private static final String COOKIES_PICASA = "vns_cannotread";
    private static int fromPicasaCookieValue = -1;
    //
    private static final String BASED_URL_LIST_MANGA = "http://truyen.vnsharing.net/DanhSach?page=";
    private static final String URL_LIST_MANGA = "http://truyen.vnsharing.net/DanhSach";
    private static final String BASED_URL = "http://truyen.vnsharing.net";
    //
    private static final String DATE_FORMAT_UPLOAD = "dd/MM/yyyy";
    private static final String DEFAULT_TRANS = "VnSharing";

    @Override
    protected String getBasedUrl() {
        return BASED_URL;
    }

    @Override
    protected String getBasedUrlListManga() {
        return BASED_URL_LIST_MANGA;
    }

    @Override
    protected String getMangaListUrl() {
        return URL_LIST_MANGA;
    }

    @Override
    protected Document getDocument(String url) throws IOException {
        System.out.println("Get Document of VnSharing");

        if (fromPicasaCookieValue == -1) {
            fromPicasaCookieValue = (ConfigManager.getCurrentInstance().getBooleanProperty(CONFIG_PICASA))
                    ? 0 : 1;
        }
        if (fromPicasaCookieValue == 0) {
            return HttpDownloadManager.getDocument(url);
        } else {
            return HttpDownloadManager.getDocument(url, new HashMap<String, String>() {

                {
                    put(COOKIES_PICASA, Integer.toString(fromPicasaCookieValue));
                }
            });
        }
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNodes = doc.select("div[class=barContent chapterList] table[class=listing] tr");
        for (Element e : xmlNodes) {
            if (e.children().size() != 3) {
                continue;
            }
            if (e.child(0).tagName().equals("th")) {
                continue;
            }
            Element aTag = e.child(0).
                    child(0);

            Date date;
            try {
                date = DateTimeUtilities.getDate(e.child(1).text(), DATE_FORMAT_UPLOAD);
            } catch (ParseException ex) {
                Logger.getLogger(VnSharing.class.getName()).log(Level.SEVERE, null, ex);
                // Can not error;
                continue;
            }

            Chapter c = new Chapter(-1, aTag.html(), BASED_URL + aTag.attr("href"), manga,
                    DEFAULT_TRANS, date);
            lstChapter.add(c);
        }
        return lstChapter;
    }
}
