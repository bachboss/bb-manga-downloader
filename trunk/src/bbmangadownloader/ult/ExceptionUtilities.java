/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bach
 */
public class ExceptionUtilities {

    private static final Pattern PATTERN_HTTP_ERROR = Pattern.compile("Server returned HTTP response code: (\\d+) for URL:");

    public static int getHttpErrorCode(IOException ex) {
        Matcher m;
        if ((m = PATTERN_HTTP_ERROR.matcher(ex.getMessage())).find()) {
            try {
                int httpError = Integer.parseInt(m.group(1));
                return httpError;
            } catch (Exception e) {
            }
        }
        return -1;
    }
}
