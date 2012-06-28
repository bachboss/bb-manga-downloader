/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.Hentai2Read;
import mangadownloader.bus.description.IBus;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

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
