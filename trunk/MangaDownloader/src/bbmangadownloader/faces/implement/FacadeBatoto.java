/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.Batoto;
import bbmangadownloader.bus.description.IBusPageBased;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeBatoto extends AFacadeMangaServerPreLstImgLoad {

    public FacadeBatoto() {
    }

    @Override
    protected IBusPageBased getCurrentBUS() {
        return new Batoto();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "Batoto";
    }
}
