/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.bus.description;

import java.io.IOException;
import java.util.List;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Image;
import mangadownloader.entity.Page;

/**
 *
 * @author Bach
 */
public interface IBusPageBased extends IBus {

    public Image getImage(Page p) throws IOException;

    List<Page> getAllPages(Chapter c) throws IOException;
}
