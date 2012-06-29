/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.description;

import java.io.IOException;
import java.util.List;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Page;

/**
 *
 * @author Bach
 */
public interface IBusPageBased extends IBus {

    public Image getImage(Page p) throws IOException;

    List<Page> getAllPages(Chapter c) throws IOException;
}
