/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;

/**
 *
 * @author Bach
 */
public class TextUtilities {

    public static String trim(String text) {
        StringBuilder t = new StringBuilder(text);

        while (t.charAt(0) == 160) {
            t.deleteCharAt(0);
        }

        while (t.charAt(t.length() - 1) == 160) {
            t.deleteCharAt(0);
        }

        return t.toString();
    }

    public static String getText(String text, String pattern, int getGroupNumber) {
        NamedPattern p = NamedPattern.compile(pattern);
        return getText(text, p, getGroupNumber);
    }

    public static String getText(String text, NamedPattern pattern, int getGroupNumber) {
        NamedMatcher m = pattern.matcher(text);
        if (m.find()) {
            return m.group(getGroupNumber);
        } else {
            return null;
        }
    }

    public static String getText(String text, String pattern, String getGroupName) {
        NamedPattern p = NamedPattern.compile(pattern);
        return getText(text, p, getGroupName);
    }

    public static String getText(String text, NamedPattern pattern, String getGroupName) {
        NamedMatcher m = pattern.matcher(text);
        if (m.find()) {
            return m.group(getGroupName);
        } else {
            return null;
        }
    }
}
