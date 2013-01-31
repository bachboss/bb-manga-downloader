/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus._99mh;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class Facade99mh extends AFacadeDefault {

    public Facade99mh() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new _99mh();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "99mh";
    }
}
