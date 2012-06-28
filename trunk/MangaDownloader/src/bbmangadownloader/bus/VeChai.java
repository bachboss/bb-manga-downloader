/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mangadownloader.bus.description.IBusOnePage;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Manga;
import mangadownloader.entity.Server;
import mangadownloader.ult.HttpDownloadManager;
import mangadownloader.ult.HtmlUtilities;
import mangadownloader.ult.Heuristic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class VeChai implements IBusOnePage {

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();

        Document doc = HttpDownloadManager.getDocument(manga.getUrl());
        Elements xmlNode = doc.select("textarea[id=vcfix]");
        String text = xmlNode.html();
        text = HtmlUtilities.unescapeHtml4(text);
        doc = Jsoup.parse(text);
        xmlNode = doc.select("a[target=_blank]");
        for (Element e : xmlNode) {
            StringBuilder name = new StringBuilder();
            if (Heuristic.doCheck(e, manga, name)) {
                lstChapter.add(new Chapter(-1, name.toString(), e.attr("href"), manga));
            }
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        URL url = chapter.getURL();
        String host = url.getHost();
        ArrayList<Image> lstReturn = new ArrayList<>();
        Iterator<Element> iElement = null;
        Document doc = HttpDownloadManager.getDocument(chapter.getUrl());
        Elements xmlNode;
        if (host.startsWith("vc1")) {
            xmlNode = doc.select("textarea[id=vcfix]");
            String text = xmlNode.html();
            text = HtmlUtilities.unescapeHtml4(text);
            text = Heuristic.repairXML(text);
            doc = Jsoup.parse(text);
            xmlNode = doc.select("img");
            iElement = xmlNode.iterator();
        } else if (host.startsWith("doctruyen")) {
            xmlNode = doc.select("div[class=entry2]").select("img");
            iElement = xmlNode.iterator();
        } else if (host.startsWith("vechai.info")) {
            xmlNode = doc.select("textarea[id=vcfix]");
            String text = xmlNode.html();
            text = HtmlUtilities.unescapeHtml4(text);
            text = Heuristic.repairXML(text);
            doc = Jsoup.parse(text);
            xmlNode = doc.select("img");
            iElement = xmlNode.iterator();
        }
        if (iElement != null) {
            int i = 0;
            while (iElement.hasNext()) {
                i++;
                Element e = iElement.next();
                lstReturn.add(new Image(i, e.attr("src"), chapter));
            }
        }

        return lstReturn;
    }
}
