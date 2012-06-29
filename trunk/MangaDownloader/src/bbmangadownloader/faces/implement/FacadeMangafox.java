/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.MangaFox;
import bbmangadownloader.bus.description.IBusPageBased;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

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
