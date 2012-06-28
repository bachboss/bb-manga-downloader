/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.EatManga;
import mangadownloader.bus.description.IBusPageBased;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

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
