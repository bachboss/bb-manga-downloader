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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class Fakku extends ADefaultBus {

    private static final String BASED_URL = "http://www.fakku.net";
//    private static final NamedPattern PATTERN_PAGE = NamedPattern.compile("var window.params.thumbs");
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
        Document doc = getDocument(m.getUrl(), m.getUrl());
        String displayName = doc.select("div[id=content] div[id=right] div[class=wrap] div[class=content-name]").first().text();
        String chapterUrl = doc.select("div[id=left] div[class=wrap] ul[class=content-navigation] li a").first()
                .attr("href");
        Element eUpload = doc.select("div#right div.wrap div.row.small div.right").first();
        String uploader = eUpload.select("a").text();
        String d
                = eUpload.select("b").text();
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
            Document doc = getDocument(c.getUrl() + "/read#page=0", c.getUrl());
            Elements eScripts = doc.select("script:not([src])");
            for (Element eScript : eScripts) {
                String script = eScript.html();

                String data = null;
                if (script.contains("window.params.thumbs")) {
                    data = script;
                    int i1 = data.indexOf("window.params.thumbs");
                    data = data.substring(i1, data.indexOf("];", i1) - 1);
                    String[] arrUrl = data.split(",");
                    numberOfPage = arrUrl.length;

                    basedImg = script.substring(script.indexOf("function imgpath(x)"), script.length());
                    i1 = basedImg.indexOf("return");
                    basedImg = basedImg.substring(basedImg.indexOf("'", i1) + 1, basedImg.indexOf('}'));
                    basedImg = basedImg.substring(0, basedImg.indexOf('\''));
                    //                    return'http://t.fakku.net/images/manga/y/[Hanamaki_Kaeru]_Original_Work_-_Yousei-san_ni_Onegai!/images/'+x+'.jpg';}
                    basedImg = basedImg.trim();
                    for (int i = 1; i <= numberOfPage; i++) {
                        Image image = new Image(i, imgPath(i, basedImg), c, c.getUrl());
                        returnValue.add(image);
                    }
                    return returnValue;

                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    private static String imgPath(int x, String basedImgUrl) {
        String s;
        if (x < 10) {
            s = "00" + x;
        } else if (x < 100) {
            s = "0" + x;
        } else {
            s = Integer.toString(x);
        }
        return basedImgUrl + s + ".jpg";
    }
//    @Override
//    protected Elements getPageQuery(Element htmlTag) throws HtmlParsingException {
//        return htmlTag.select("html body div#wrap div#content div.chapter div.right div.page select.drop").first().children();
//    }
//
//    @Override
//    protected Page getPageFromTag(Element htmlTag, Chapter c) throws HtmlParsingException {
//        return new Page(c.getUrl() + "/read#page=1", c);
//    }
//
//    @Override
//    protected Element getImageQuery(Element imgNode) throws HtmlParsingException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) throws HtmlParsingException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    protected Elements getChapterQuery(Element htmlTag) throws HtmlParsingException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    protected Chapter getChapterFromTag(Element htmlTag, Manga m) throws HtmlParsingException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
