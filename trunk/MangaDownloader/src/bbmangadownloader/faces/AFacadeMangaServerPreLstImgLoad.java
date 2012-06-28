/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces;

import java.io.IOException;
import java.util.List;
import mangadownloader.bus.description.IBus;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Manga;
import mangadownloader.entity.Server;

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
