/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus.description;

import java.io.IOException;
import java.util.List;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Manga;
import mangadownloader.entity.Server;

/**
 *
 * @author Bach
 */
public interface IBus {

    public List<Manga> getAllMangas(Server s) throws IOException;

    public List<Chapter> getAllChapters(Manga m) throws IOException;

    public List<Image> getAllImages(Chapter c) throws IOException;
}
