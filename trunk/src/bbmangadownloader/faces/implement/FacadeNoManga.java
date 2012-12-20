/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.NoManga;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeNoManga extends AFacadeDefault {

    public FacadeNoManga() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new NoManga();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "NoManga";
    }
}
