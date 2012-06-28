/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.VeChai;
import mangadownloader.bus.description.IBus;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeVeChai extends AFacadeMangaServerPreLstImgLoad {

    public FacadeVeChai() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new VeChai();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.PartlySupport;
    }

    @Override
    public String getServerName() {
        return "VeChai";
    }
}
