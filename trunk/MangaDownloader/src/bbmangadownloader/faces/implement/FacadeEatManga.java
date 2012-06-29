/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.EatManga;
import bbmangadownloader.bus.description.IBusPageBased;
import bbmangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeEatManga extends AFacadeMangaServerPreLstImgLoad {

    public FacadeEatManga() {
    }

    @Override
    protected IBusPageBased getCurrentBUS() {
        return new EatManga();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "EatManga";
    }
}
