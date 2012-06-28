/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.ComicVN;
import mangadownloader.bus.description.IBus;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeComicVN extends AFacadeMangaServerPreLstImgLoad {

    public FacadeComicVN() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new ComicVN();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.PartlySupport;
    }

    @Override
    public String getServerName() {
        return "ComicVn";
    }
}
