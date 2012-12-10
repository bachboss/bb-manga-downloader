/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.DragonFlyScans;
import bbmangadownloader.bus._99770;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeDragonFly extends AFacadeDefault {

    public FacadeDragonFly() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new DragonFlyScans();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Scanner;
    }

    @Override
    public String getServerName() {
        return "DragonFly";
    }
}
