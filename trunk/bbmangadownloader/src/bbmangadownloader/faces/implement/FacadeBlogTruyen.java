/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces.implement;

import bbmangadownloader.bus.BlogTruyen;
import bbmangadownloader.bus.description.IBus;
import bbmangadownloader.faces.AFacadeDefault;
import bbmangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeBlogTruyen extends AFacadeDefault {

    public FacadeBlogTruyen() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new BlogTruyen();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "BlogTruyen";
    }
}
