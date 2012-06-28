/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces.implement;

import mangadownloader.bus.TruyenTranhTuan;
import mangadownloader.bus.description.IBus;
import mangadownloader.faces.AFacadeMangaServerPreLstImgLoad;
import mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeTruyenTranhTuan extends AFacadeMangaServerPreLstImgLoad {

    public FacadeTruyenTranhTuan() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new TruyenTranhTuan();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "TruyenTranhTuan";
    }
}
