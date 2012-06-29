/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.Hentai2Read;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeHentai2Read extends AFacadeMangaServerPreLstImgLoad {

    public FacadeHentai2Read() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new Hentai2Read();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "H2R";
    }
}
