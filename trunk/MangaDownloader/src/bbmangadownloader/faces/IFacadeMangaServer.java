/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces;

import java.util.List;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Manga;
import mangadownloader.entity.Server;

/**
 *
 * @author Bach
 */
public interface IFacadeMangaServer extends Cloneable {

    public List<Manga> getAllMangas(Server server) throws Exception;

    public List<Chapter> getAllChapters(Manga manga) throws Exception;

    public List<Image> getAllImages(Chapter chapter) throws Exception;

    public int getNumberOfImages(Chapter chapter) throws Exception;

    public IFacadeMangaServer clone();

    public SupportType getSupportType();

    public String getServerName();
}
