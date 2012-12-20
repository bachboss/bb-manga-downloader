/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class _Test implements IBus {

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException {
        List<Image> returnValue = new ArrayList<Image>();
        int max = 20 + new Random().nextInt(30);
        for (int i = 0; i < max; i++) {
            returnValue.add(new Image(i + 1, "http://localhost:80/Temp/" + (i + 1) + ".png", c));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(_Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return returnValue;
    }
}
