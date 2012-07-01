/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.TruyenTranhTuan;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeTruyenTranhTuan extends AFacadeDefault {

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
