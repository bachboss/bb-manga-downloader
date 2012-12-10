/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.MangaStream;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangaStream extends AFacadeDefault {

    public FacadeMangaStream() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new MangaStream();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Scanner;
    }

    @Override
    public String getServerName() {
        return "MangaStream";
    }
}
