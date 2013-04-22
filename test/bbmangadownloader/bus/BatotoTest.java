package bbmangadownloader.bus;

import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Bach
 */
public class BatotoTest {

    public static void main(String[] args) throws IOException, HtmlParsingException {
        Batoto host = new Batoto();
        Manga m = new Manga(Server.EMPTY_SERVER, "Beelzebub", "http://www.batoto.net/comic/_/comics/beelzebub-r4");

        List<Chapter> lstChapter = host.getAllChapters(m);
        for (Chapter c : lstChapter) {
            System.out.println(c.getDisplayName());
        }
    }
}