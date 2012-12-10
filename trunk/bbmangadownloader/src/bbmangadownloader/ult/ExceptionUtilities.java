/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.IOException;

/**
 *
 * @author Bach
 */
public class ExceptionUtilities {

    private static final NamedPattern PATTERN_HTTP_ERROR =
            NamedPattern.compile("Server returned HTTP response code: (\\d+) for URL:");

    public static int getHttpErrorCode(IOException ex) {
        NamedMatcher m;
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
