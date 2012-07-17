/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import java.util.List;

/**
 *
 * @author Bach
 */
public interface IFacadeMangaServer extends Cloneable {

    public List<Manga> getAllMangas(Server server) throws Exception;

    public List<Chapter> getAllChapters(Manga manga) throws Exception;

    public List<Image> getAllImages(Chapter chapter) throws Exception;

    public IFacadeMangaServer clone();

    public SupportType getSupportType();

    public String getServerName();
}
