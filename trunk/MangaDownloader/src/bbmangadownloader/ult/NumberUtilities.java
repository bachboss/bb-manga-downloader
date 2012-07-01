/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bach
 */
public class NumberUtilities {

    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    private static final Pattern PATTERN_NUMBER = Pattern.compile("(\\d+)");

    public static int getNumber(String text) {
        Matcher m = PATTERN_NUMBER.matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }
}
