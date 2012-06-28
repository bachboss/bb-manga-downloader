/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.VnSharing;
import mangadownloader.bus.description.IBus;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeVnSharing extends AFacadeMangaServerPreLstImgLoad {

    public FacadeVnSharing() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new VnSharing();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "VnSharing";
    }
}
