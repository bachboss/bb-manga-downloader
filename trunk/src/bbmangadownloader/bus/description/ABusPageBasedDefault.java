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
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public abstract class ABusPageBasedDefault extends ADefaultBus implements IBusPageBased {

    @Override
    public List<Image> getAllImages(final Chapter chapter) throws IOException, HtmlParsingException {
        // TODO: Can Improve by using multi thread
        final Document doc = getDocument(chapter.getUrl());
        List<Page> lstPage = getAllPages(chapter, doc);
        final List<Image> lstImage = new ArrayList<Image>();

        List<Callable<Object>> lstTask = new ArrayList<Callable<Object>>();

        for (final Page p : lstPage) {
            lstTask.add(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {
                        Image img;
                        if (p.isIsChapterUrl()) {
                            img = getImage(p, doc);
                        } else {
                            Document pageDoc = getDocument(p.getUrl());
                            img = getImage(p, pageDoc);
                        }

                        if (img != null) {
                            if (img.getImgOrder() == -1) {
                                img.setImgOrder(p.getPageOrder());
                            }
                            lstImage.add(img);
                        }
                        return null;
                    } catch (Exception ex) {
                        return ex;
                    }
                }
            });
        }
        List<Future<Object>> lstFuture = MultitaskJob.doTask(lstTask);
        for (Future<Object> f : lstFuture) {
            Object o = null;
            try {
                o = f.get();
            } catch (Exception ex) {
                Logger.getLogger(ABusPageBasedDefault.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (o instanceof IOException) {
                throw (IOException) o;
            }
            if (o instanceof HtmlParsingException) {
                throw (HtmlParsingException) o;
            }
        }
        return lstImage;
    }
}
