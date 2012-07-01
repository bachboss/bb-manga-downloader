/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.MangaInn;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.bus.description.IBusPageBased;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangaInn extends AFacadeDefault {

    public FacadeMangaInn() {
    }

    @Override
    protected IBus getCurrentBUS() {
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