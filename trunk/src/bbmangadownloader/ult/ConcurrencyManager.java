/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bach
 */
public class ConcurrencyManager {

    private static final DateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String getStringFromDate(Date date) {
        return DateTimeUtilities.getStringFromDate(date, DEFAULT_DATETIME_FORMAT);
    }
}
