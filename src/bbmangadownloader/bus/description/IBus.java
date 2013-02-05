/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.IFacadeMangaServer;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Bach
 */
public interface IBus {

    /**
     *
     * @param s Server
     * @return <code>List<Manga></code>, which should not be null
     * @throws IOException
     * @throws HtmlParsingException
     */
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException;

    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException;

    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException;

    public Manga getManga(String mangaUrl);

    public Chapter getChapter(String chapterUrl, boolean isGetMangaInformation);

    public IFacadeMangaServer.UrlType getUrlType(String url);
}
