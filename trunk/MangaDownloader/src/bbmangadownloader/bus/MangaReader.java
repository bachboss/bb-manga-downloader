/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import mangadownloader.entity.*;
import mangadownloader.ult.DateTimeUtilities;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class MangaReader extends ABusPageBasedDefaultChapPageImage { // Done

    private static final String BASED_URL = "http://www.mangareader.net";
    private static final String URL_LIST_MANGA = "http://www.mangareader.net/alphabetical";
    //
    private static final String DATE_FORMAT_UPLOAD = "MM/dd/yyyy";
    private static final String DEFAULT_TRANS = "MangaReader";
    
    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<>();
        Document doc = getDocument(URL_LIST_MANGA);
        
        Elements xmlNodes = doc.select("div[class=series_alpha] ul[class=series_alpha] li a");
        
        for (Element e : xmlNodes) {
            Manga m = new Manga(s, e.text(), BASED_URL + e.attr("href"));
            lstReturn.add(m);
        }
        return lstReturn;
    }
    
    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[id=chapterlist] tr");
    }
    
    @Override
    protected Elements getPageQuery(Element htmlTag) {
        return htmlTag.select("div[id=selectpage]").first().select("option");
    }
    
    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[id=img]").first();
    }
    
    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        if (htmlTag.child(0).tagName().equals("th")) {
            return null;
        }
        
        Element aTag = htmlTag.child(0).select("a").first();
        
        Date date;
        try {
            date = DateTimeUtilities.getDate(htmlTag.child(1).text(), DATE_FORMAT_UPLOAD);
        } catch (ParseException ex) {
            return null;
        }
        return new Chapter(-1, aTag.html(), BASED_URL + aTag.attr("href"), m, DEFAULT_TRANS, date);
    }
    
    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) {
        return new Page(BASED_URL + htmlTag.attr("value"), c);
    }
    
    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c) {
        return new Image(-1, imgNode.attr("src"), c);
    }
}
