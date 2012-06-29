/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import java.io.IOException;
import java.util.List;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;

/**
 *
 * @author Bach
 */
public abstract class AFacadeMangaServerPreLstImgLoad extends AFacadeMangaServer {

    @Override
    public List<Manga> getAllMangas(Server server) throws IOException {
        return getCurrentBUS().getAllMangas(server);
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        return getCurrentBUS().getAllChapters(manga);
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        lazyLoadImages(chapter);
        return chapter.getListImage();
    }

    @Override
    public int getNumberOfImages(Chapter chapter) throws IOException {
        lazyLoadImages(chapter);
        return chapter.getImagesCount();
    }

    private void lazyLoadImages(Chapter chapter) throws IOException {
        if (chapter.getImagesCount() == -1) {
            IBus bus = getCurrentBUS();
            chapter.clearImages();
            List<Image> lst = bus.getAllImages(chapter);
            chapter.addImages(lst);
        }
    }
}
