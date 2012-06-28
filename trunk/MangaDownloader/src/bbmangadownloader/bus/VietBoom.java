/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mangadownloader.bus.description.IBusOnePage;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Manga;
import mangadownloader.entity.Server;
import mangadownloader.ult.HttpDownloadManager;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class VietBoom implements IBusOnePage {

    public static final String BASED_URL = "http://truyen.vietboom.com";
    public static final String BASED_IMG_URL = "http://truyen.vietboom.com/Resources/Images/Pages/";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<>();

        Document doc = HttpDownloadManager.getDocument(manga.getUrl());
        Elements xmlNode = (Elements) doc.select("a[class=index]");
        Element node = xmlNode.get(0);
        String url = VietBoom.BASED_URL + node.attr("href");
        url = url.substring(0, url.length() - 1);

        int page = 0;
        boolean isHasNextPage = true;
        while (isHasNextPage) {
            page++;
            doc = HttpDownloadManager.getDocument(url + page);
            xmlNode = (Elements) doc.select("div[class=cellChapter] a");
            if (xmlNode.isEmpty()) {
                isHasNextPage = false;
            } else {
                for (Iterator<Element> it = xmlNode.iterator(); it.hasNext();) {
                    Element e = it.next();
                    Chapter c = new Chapter(-1, e.html(), BASED_URL + e.attr("href"), manga);
                    lstChapter.add(c);

                }
            }
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        List<Image> lstImage = new ArrayList<>();
        Document doc = HttpDownloadManager.getDocument(chapter.getUrl());

        Elements xmlNode = (Elements) doc.select("script[type=text/javascript]");
        Pattern p = Pattern.compile(".*,\"imageUrl\":\"(.+)\",\"position.*");
        for (Element e : xmlNode) {
            if (e.html().contains("ChapterPage.listPage")) {
                String text = e.html();
                text = text.substring(text.indexOf('[') + 2);
                text = text.substring(0, text.indexOf(']') - 1);
                String[] arr = text.split("\\},\\{");
                int i = 0;
                for (String str : arr) {
                    Matcher m = p.matcher(str);
                    if (m.matches()) {
                        Image img = new Image(i++, BASED_IMG_URL + m.group(1), chapter);
                        lstImage.add(img);
                    }
                }
                break;
            }
        }
        return lstImage;
    }
}
