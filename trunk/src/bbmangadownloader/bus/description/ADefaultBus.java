/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;
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
}
