/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (fromPicasaCookieValue == -1) {
            fromPicasaCookieValue = (ConfigManager.getCurrentInstance().getBooleanProperty(CONFIG_PICASA))
                    ? 0 : 1;
        }
        if (fromPicasaCookieValue == 0) {
            return super.getDocument(url);
        } else {
            return HttpDownloadManager.getDocumentWithCookie(url, new HashMap<String, String>() {

                {
                    put(COOKIES_PICASA, Integer.toString(fromPicasaCookieValue));
                    put("vns_Adult", "yes");
                }
            });
        }
    }

    private Document getDocumentInPostForm(final String seoValue) throws IOException {
        return HttpDownloadManager.getDocumentWithCookieAndPostForm("http://truyen.vnsharing.net/Manga/GetChapterListOfManga",
                null,
                new HashMap<String, String>() {

                    {
                        put("mangaSeoName", seoValue);
                    }
                }, null);
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();
        Document doc = getDocument(manga.getUrl());
        {
            // Get Translator;
            String translator = DEFAULT_TRANS;
            {
                Elements xmlNodes = doc.select("span[class=info]");
                for (Element e : xmlNodes) {
                    if (e.text().contains("Nhóm dịch")) {
                        translator = e.nextElementSibling().text();
                        break;
                    }
                }
            }
            // First, parse data in the table. 
            Elements xmlNodes = doc.select("div[class=barContent chapterList] table[class=listing] tr");
            lstChapter.addAll(getChapterInTable(xmlNodes, manga, translator));
        }
        {
            // Get all the others in POST form...
            String[] seoTmpArr;
            String[] trans;
            {
                Elements xmlNodes = doc.select(" a[seoName]");
                seoTmpArr = new String[xmlNodes.size()];
                trans = new String[seoTmpArr.length];
                for (int i = 0; i < xmlNodes.size(); i++) {
                    Element e = xmlNodes.get(i);
                    seoTmpArr[i] = e.attr("seoName");
                    trans[i] = e.parent().parent().parent().firstElementSibling().child(0).text();
                }
            }

            for (int i = 0; i < seoTmpArr.length; i++) {
                doc = getDocumentInPostForm(seoTmpArr[i]);
                Elements xmlNodes = doc.select("table[class=listing] tr");
                lstChapter.addAll(getChapterInTable(xmlNodes, manga, trans[i]));
            }
        }
        return lstChapter;
    }

    private ArrayList<Chapter> getChapterInTable(Elements xmlNodes, Manga manga, String translator) {
        ArrayList<Chapter> lstChapter = new ArrayList<>();
        for (Element e : xmlNodes) {
            if (e.children().size() != 3) {
                continue;
            }
            if (e.child(0).tagName().equals("th")) {
                continue;
            }
            Element aTag = e.child(0).child(0);
            try {
                MangaDateTime date = new MangaDateTime(DateTimeUtilities.getDate(e.child(1).text(), DATE_FORMAT_UPLOAD));
                Chapter c = new Chapter(-1, aTag.html(), BASED_URL + aTag.attr("href"), manga,
                        translator, date);
                lstChapter.add(c);
            } catch (ParseException ex) {
                Logger.getLogger(VnSharing.class.getName()).log(Level.SEVERE, null, ex);
                // Can not error;
                continue;
            }
        }
        return lstChapter;
    }
}
