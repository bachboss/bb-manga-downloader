/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Page;
import bbmangadownloader.ult.HttpDownloadManager;
import bbmangadownloader.ult.MultitaskJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public abstract class ABusPageBasedDefault implements IBusPageBased {

    private static final int DEFAULT_POOL_SIZE = 4;

    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.getDocument(url);
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        // TODO: Can Improve by using multi thread
        List<Page> lstPage = getAllPages(chapter);
        final List<Image> lstImage = new ArrayList<>();

        List<Callable<Object>> lstTask = new ArrayList<>();

        for (final Page p : lstPage) {
            lstTask.add(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    Image img = getImage(p);
                    if (img != null) {
                        img.setImgOrder(p.getPageOrder());
                        lstImage.add(img);
                    }
                    return false;
                }
            });
        }
        MultitaskJob.doTask(DEFAULT_POOL_SIZE, lstTask);

        return lstImage;
    }
}
