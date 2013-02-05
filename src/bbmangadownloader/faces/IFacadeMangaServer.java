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

    public List<Manga> getAllMangas(final Server server) throws Exception;

    public List<Chapter> getAllChapters(final Manga manga) throws Exception;

    public List<Image> getAllImages(final Chapter chapter) throws Exception;

    public IFacadeMangaServer clone();

    public SupportType getSupportType();

    public String getServerName();

    /**
     *
     * @param mangaUrl Manga's url
     * @return Manga with fully manga information. Server should be auto-search
     */
    public Manga getManga(String mangaUrl);

    public Chapter getChapter(String chapterUrl, boolean isGetMangaInformation);

    public UrlType getUrlType(String url);

    public static enum UrlType {

        Manga, Chapter, Unknow;
    }
}
