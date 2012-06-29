/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.VietBoom;
import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeVietBoom extends AFacadeMangaServerPreLstImgLoad {

    public FacadeVietBoom() {
    }

    @Override
    protected IBusOnePage getCurrentBUS() {
        return new VietBoom();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "VietBoom";
    }
}
