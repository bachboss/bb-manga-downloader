/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.MangaReader;
import bbmangadownloader.bus.description.IBusPageBased;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

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