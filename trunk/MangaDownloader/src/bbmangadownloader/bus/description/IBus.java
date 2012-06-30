/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Bach
 */
public interface IBus {

    public List<Manga> getAllMangas(Server s) throws IOException;

    public List<Chapter> getAllChapters(Manga m) throws IOException;

    public List<Image> getAllImages(Chapter c) throws IOException;
}
