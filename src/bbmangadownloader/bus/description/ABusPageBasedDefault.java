/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Page;
import bbmangadownloader.ult.MultitaskJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Bach
 */
public abstract class ABusPageBasedDefault extends ADefaultBus implements IBusPageBased {

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException, HtmlParsingException {
        // TODO: Can Improve by using multi thread
        List<Page> lstPage = getAllPages(chapter);
        final List<Image> lstImage = new ArrayList<Image>();

        List<Callable<Object>> lstTask = new ArrayList<Callable<Object>>();

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
        MultitaskJob.doTask(lstTask);

        return lstImage;
    }
}
