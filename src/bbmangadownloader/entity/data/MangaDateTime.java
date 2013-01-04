/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity.data;

import bbmangadownloader.ult.DateTimeUtilities;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bach
 */
public class MangaDateTime {//implements Comparable<MangaDateTime> {

    private static final DateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
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
            return DateTimeUtilities.getStringFromDate(date, DEFAULT_DATETIME_FORMAT);
        } else if (relativeTime != null) {
            return relativeTime;
        } else {
            return null;
        }
    }
    public static final MangaDateTime NOT_SUPPORT = new MangaDateTime("Not support");
    public static final MangaDateTime NOT_AVAIABLE = new MangaDateTime("Not avaiable");
//    @Override
//    public int compareTo(MangaDateTime o) {
////        a negative integer, zero, or a positive integer as this object is less than, 
////        equal to, or greater than the specified object.
//        
//    }
}
