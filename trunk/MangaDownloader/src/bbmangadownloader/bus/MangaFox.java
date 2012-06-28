/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import mangadownloader.bus.description.ABusPageBasedDefaultChapImage;
import mangadownloader.entity.*;
import mangadownloader.ult.DateTimeUtilities;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class MangaFox extends ABusPageBasedDefaultChapImage { // Done

    private static final String URL_LIST_MANGA = "http://mangafox.me/manga/";
    //
    private static final String DATE_FORMAT_UPLOAD = "MMMM dd, yyyy";
    private static final String DEFAULT_TRANS = "MangaFox";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<>();
        Document doc = getDocument(URL_LIST_MANGA);

        Elements xmlNodes = doc.select("div[class=manga_list] li>a");

        for (Element e : xmlNodes) {
            Manga m = new Manga(s, e.text(), e.attr("href"));
            lstReturn.add(m);
        }
        return lstReturn;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("ul[class=chlist] li");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[id=image]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        Elements aTags = htmlTag.select("a[class=tips]");
        if (aTags != null && !aTags.isEmpty()) {
            Element aTag = aTags.first();

            Date date = null;
            try {
                date = DateTimeUtilities.getDate(
                        htmlTag.select("span[class=date]").first().text(), DATE_FORMAT_UPLOAD, Locale.US);
            } catch (ParseException ex) {
                // TODO: Improve: x Hours ago, fuck !;
                date = new Date();
            }

            return new Chapter(-1, aTag.html(), aTag.attr("href"), m, DEFAULT_TRANS, date);
        } else {
            return null;
        }
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c) {
        return new Image(-1, imgNode.attr("src"), c);
    }

    @Override
    public List<Page> getAllPages(Chapter chapter) throws IOException {
        ArrayList<Page> lstPage = new ArrayList<>();

        String url = chapter.getUrl();
        String baseLink = url.substring(0, url.lastIndexOf("/") + 1);

        Document doc = getDocument(chapter.getUrl());
        Elements xmlNode = doc.select("select[class=m]").first().select("option");
        Iterator<Element> iElement = xmlNode.iterator();
        while (iElement.hasNext()) {
            Element e = iElement.next();
            Page p = new Page(baseLink + e.attr("value") + ".html", chapter);
            lstPage.add(p);
        }

        return lstPage;
    }
}