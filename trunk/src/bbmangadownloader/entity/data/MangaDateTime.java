/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity.data;

import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.ult.Heuristic;
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
    private boolean isRelative = false;

    public MangaDateTime(Date date) {
        this.date = date;
    }

    private MangaDateTime(String text, boolean isRelativeTime) {
        if (!isRelativeTime) {
            this.isRelative = false;
            this.relativeTime = text;
        }
    }

    /**
     *
     * Construct datetime
     *
     * @param relativeTimeString Text to display as string
     */
    public MangaDateTime(String relativeTimeString) {
        isRelative = true;
        this.relativeTime = relativeTimeString;
        Date d = Heuristic.getDate(relativeTime);
        if (d != null) {
            this.date = d;
        } else {
            this.relativeTime = relativeTimeString;
        }
    }

    @Override
    public String toString() {
        if (date != null) {
            String str = DateTimeUtilities.getStringFromDate(date, DEFAULT_DATETIME_FORMAT);
            if (isRelative) {
                return str + " (~)";
            } else {
                return str;
            }
        } else if (relativeTime != null) {
            return relativeTime;
        } else {
            return null;
        }
    }
    public static final MangaDateTime NOT_SUPPORT = new MangaDateTime("Not support", false);
    public static final MangaDateTime NOT_AVAIABLE = new MangaDateTime("Not avaiable", false);
//    @Override
//    public int compareTo(MangaDateTime o) {
////        a negative integer, zero, or a positive integer as this object is less than, 
////        equal to, or greater than the specified object.
//        
//    }
}
