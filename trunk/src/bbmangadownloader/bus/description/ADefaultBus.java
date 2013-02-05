/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.faces.IFacadeMangaServer.UrlType;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.manager.HttpDownloadManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public abstract class ADefaultBus implements IBus {

    @Deprecated
    protected Document getDocument(String url) throws IOException {
        return HttpDownloadManager.createConnection(url).getDocument();
    }

    protected Document getDocument(String url, String from) throws IOException {
        return HttpDownloadManager.createConnection(url).referer(from).getDocument();
    }

//    protected abstract String getChapterDisplayName(Document chapterDocument);
//
//    protected abstract String getTrans(Document chapterDocument);
//
//    protected abstract MangaDateTime getUploadDate(Document chapterDocument);
    protected String getChapterDisplayName(Document chapterDocument) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    protected String getTrans(Document chapterDocument) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    protected MangaDateTime getUploadDate(Document chapterDocument) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    private Chapter getChaperInformation(Document chapterDocument, String chapterUrl) {
        Chapter c = new Chapter(-1, getChapterDisplayName(chapterDocument),
                chapterUrl, Manga.EMPTY_MANGA, getTrans(chapterDocument), getUploadDate(chapterDocument));
        return c;
    }

    protected String getMangaName(Document chapterDocument) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected String getMangaUrl(Document chapterDocument) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Manga getMangaInformation(Document chapterDocument, String chapterUrl) {
        Server s = ServerManager.getServerByUrl(chapterUrl);
        Manga m = new Manga(s, getMangaName(chapterDocument), getMangaUrl(chapterDocument));
        return m;
    }

    @Override
    public UrlType getUrlType(String url) {
        // TODO: Later;
        return UrlType.Chapter;
    }

    @Override
    public Manga getManga(String mangaUrl) {
        // TODO: Later !
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Chapter getChapter(String chapterUrl, boolean isGetMangaInformation) {
        try {
            Document chapterDocument = getDocument(chapterUrl);
            Chapter c = getChaperInformation(chapterDocument, chapterUrl);
            if (isGetMangaInformation) {
                Manga m = getMangaInformation(chapterDocument, chapterUrl);
                c.setManga(m);
            }
            return c;
        } catch (IOException ex) {
            Logger.getLogger(ADefaultBus.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
