/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.ComicVN;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

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
