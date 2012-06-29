/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.VeChai;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

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
