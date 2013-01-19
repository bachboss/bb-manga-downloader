/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ADefaultBus;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Bach
 */
public class Fakku extends ADefaultBus {

    private static final String BASED_URL = "http://www.fakku.net";
    private static final NamedPattern PATTERN_PAGE = NamedPattern.compile("var data = \\{.*\\};");
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
        Document doc = getDocument(m.getUrl(), m.getUrl());
        String displayName = doc.select("div[id=content] div[id=right] div[class=wrap] div[class=content-name]").first().text();
        String chapterUrl = doc.select("div[id=left] div[class=wrap] ul[id=content-navigation] li[id=last] a").first()
                .attr("href");
        Element eUpload = doc.select("div#right div.wrap div.row.small div.right").first();
        String uploader = eUpload.select("a").text();
        String d =
                eUpload.select("b").text();
        MangaDateTime date;
        try {
            date = new MangaDateTime(DateTimeUtilities.getDate(d, DATE_FORMAT_UPLOAD));
        } catch (Exception ex) {
            date = new MangaDateTime(d);
        }

        final Chapter c = new Chapter(0F, displayName, BASED_URL + chapterUrl, m, uploader, date);
        return new ArrayList<Chapter>() {
            {
                add(c);
            }
        };
    }

    @Override
    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException {
        List<Image> returnValue = new ArrayList<Image>();

        int numberOfPage = 0;
        String basedImg = null;
        {
            Document doc = getDocument(c.getUrl(), c.getUrl());
            Element eScript = doc.select("script:not([src])").first();
            String script = eScript.html();

            String data = null;
            NamedMatcher m = PATTERN_PAGE.matcher(script);
            if (m.find()) {
                data = m.group();
                data = data.substring(data.indexOf(':') + 2, data.indexOf("};") - 1);
            }
            String[] arrUrl = data.split(",");
            numberOfPage = arrUrl.length;

            basedImg = script.substring(script.indexOf("function imgpath(x)"), script.length());
            basedImg = basedImg.substring(basedImg.indexOf("return \'") + 8, basedImg.length());
            basedImg = basedImg.substring(0, basedImg.indexOf('\''));
        }
        {
            for (int i = 0; i < numberOfPage; i++) {
                Image image = new Image(i, imgPath(String.valueOf(i), basedImg), c, c.getUrl());
                returnValue.add(image);
            }
        }
        return returnValue;
    }

    private static String imgPath(String x, String basedImgUrl) {
        while (x.length() < 3) {
            x = '0' + x;
        }
        return basedImgUrl + x + ".jpg";
    }
}