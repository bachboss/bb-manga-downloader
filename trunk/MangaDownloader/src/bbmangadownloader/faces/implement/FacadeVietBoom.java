/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.VietBoom;
import mangadownloader.bus.description.IBusOnePage;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

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
