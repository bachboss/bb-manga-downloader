/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.MangaInn;
import mangadownloader.bus.description.IBusPageBased;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangaInn extends AFacadeMangaServerPreLstImgLoad {

    public FacadeMangaInn() {
    }

    @Override
    protected IBusPageBased getCurrentBUS() {
        return new MangaInn();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "MangaInn";
    }
}