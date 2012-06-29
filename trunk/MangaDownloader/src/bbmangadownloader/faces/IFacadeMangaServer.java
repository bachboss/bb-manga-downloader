/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import java.util.List;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;

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
