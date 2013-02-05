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
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public abstract class AFacadeDefault extends AFacadeMangaServer {

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws Exception {
        return getCurrentBUS().getAllChapters(manga);
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws Exception {
        return getCurrentBUS().getAllImages(chapter);
    }

    @Override
    public List<Manga> getAllMangas(Server server) throws Exception {
        return getCurrentBUS().getAllMangas(server);
    }

    @Override
    public Manga getManga(String mangaUrl) {
        return getCurrentBUS().getManga(mangaUrl);
    }

    @Override
    public Chapter getChapter(String chapterUrl, boolean isGetMangaInformation) {
        return getCurrentBUS().getChapter(chapterUrl, isGetMangaInformation);
    }

    @Override
    public UrlType getUrlType(String url) {
        return getCurrentBUS().getUrlType(url);
    }
}