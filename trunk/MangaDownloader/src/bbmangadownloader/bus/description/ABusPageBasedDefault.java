/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Page;
import bbmangadownloader.ult.HttpDownloadManager;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public abstract class ABusPageBasedDefault implements IBusPageBased {

    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.getDocument(url);
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        // Can Improve by using multi thread
        List<Page> lstPage = getAllPages(chapter);

        List<Image> lstImage = new ArrayList<>();

        for (int i = 0; i < lstPage.size(); i++) {
            Image img = getImage(lstPage.get(i));
            if (img != null) {
                img.setImgOrder(i);
                lstImage.add(img);
            }
        }
        return lstImage;
    }
}
