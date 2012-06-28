/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.MangaReader;
import mangadownloader.bus.description.IBusPageBased;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeManagerReader extends AFacadeMangaServerPreLstImgLoad {

    public FacadeManagerReader() {
    }

    @Override
    protected IBusPageBased getCurrentBUS() {
        return new MangaReader();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "MangaReader";
    }
}