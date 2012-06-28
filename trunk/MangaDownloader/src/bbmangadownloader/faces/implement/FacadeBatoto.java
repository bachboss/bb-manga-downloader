/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.Batoto;
import mangadownloader.bus.description.IBusPageBased;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

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
