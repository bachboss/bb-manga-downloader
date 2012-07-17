/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity.data;

import bbmangadownloader.ult.ConcurrencyManager;
import java.util.Date;

/**
 *
 * @author Bach
 */
public class MangaDateTime {

    private Date date;
    private String relativeTime;

    public MangaDateTime(Date date) {
        this.date = date;
    }

    public MangaDateTime(String strRelativeTime) {
        this.relativeTime = strRelativeTime;
    }

    @Override
    public String toString() {
        if (date != null) {
            return ConcurrencyManager.getStringFromDate(date);
        } else if (relativeTime != null) {
            return relativeTime;
        } else {
            return null;
        }
    }
    public static final MangaDateTime NOT_SUPPORT = new MangaDateTime("Not support");
    public static final MangaDateTime NOT_AVAIABLE = new MangaDateTime("Not avaiable");
}
