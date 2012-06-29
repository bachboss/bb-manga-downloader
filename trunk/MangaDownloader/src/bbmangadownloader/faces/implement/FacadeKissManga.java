/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.KissManga;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeKissManga extends AFacadeMangaServerPreLstImgLoad {

    public FacadeKissManga() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new KissManga();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "KissManga";
    }
}
