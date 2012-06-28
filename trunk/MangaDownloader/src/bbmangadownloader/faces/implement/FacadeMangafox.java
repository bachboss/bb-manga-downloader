/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.MangaFox;
import mangadownloader.bus.description.IBusPageBased;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangafox extends AFacadeMangaServerPreLstImgLoad {

    public FacadeMangafox() {
    }

    @Override
    protected IBusPageBased getCurrentBUS() {
        return new MangaFox();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "MangaFox";
    }
}
