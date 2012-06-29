/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Bach
 */
public class DateTimeUtilities {

    public static Date getDate(String text, DateFormat formatter) throws ParseException {
        Date returnValue = (Date) formatter.parse(text);
        return returnValue;
    }

    public static Date getDate(String text, String dateTimeFormat, Locale locale) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(dateTimeFormat, locale);
        return getDate(text, formatter);
    }

    public static Date getDate(String text, String dateTimeFormat) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        return getDate(text, formatter);
    }

    public static String getStringFromDate(Date date, DateFormat formatter) {
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }

    public static String getStringFromDate(Date date, String dateTimeFormat) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        return getStringFromDate(date, formatter);
    }
}
