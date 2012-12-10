/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ABusPageBasedDefaultChapImage;
import bbmangadownloader.entity.*;
import bbmangadownloader.ult.NumberUtilities;
import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Hentai2Read extends ABusPageBasedDefaultChapImage {

    private static final NamedPattern PATTERN_CHAPTER = NamedPattern.compile("(?<cN>\\d+)");
    private static final NamedPattern PATTERN_URL = NamedPattern.compile("location.href='(?<url>.*)'.*this.value");

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        // TODO: MUST DO THIS, LATER !
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[class=wpm_pag mng_det] div[class=box] div[class=text] ul li a");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("div[class=prw] img").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        return new Chapter(getChapterNumber(htmlTag.text()), htmlTag.text(), htmlTag.attr("href"), m);
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c) {
        return new Image(-1, imgNode.attr("src"), c);
    }

    @Override
    public List<Page> getAllPages(Chapter chapter) throws IOException {
        List<Page> lstPage = new ArrayList<Page>();
        Document doc = getDocument(chapter.getUrl());
        Element xmlNode = doc.select("select[class=cbo_wpm_pag]").first();
        String url = xmlNode.attr("onchange");
        NamedMatcher m = PATTERN_URL.matcher(url);
        if (m.find()) {
            url = m.group(1);
        } else {
            return lstPage;
        }
        Elements xmlNodes = xmlNode.select("option");
        for (Element e : xmlNodes) {
            Page p = new Page(url + e.attr("value") + "/", chapter, NumberUtilities.getNumberInt(e.text()),
                    e.attributes().hasKey("selected"));
            lstPage.add(p);
        }

        return lstPage;
    }

    private float getChapterNumber(String name) {
        NamedMatcher m = PATTERN_CHAPTER.matcher(name);
        if (m.find()) {
            try {
                return Float.parseFloat(m.group(1));
            } catch (Exception ex) {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
