/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.TenManga;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeTenManga extends AFacadeDefault {

    public FacadeTenManga() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new TenManga();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "TenManga";
    }
}
