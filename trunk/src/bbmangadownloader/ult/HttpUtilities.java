/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Bach
 */
public class HttpUtilities {

    public static String getStringFromPostForms(Map<String, String> postForm) throws UnsupportedEncodingException {
        if (postForm != null && !postForm.isEmpty()) {
            Set<Map.Entry<String, String>> data = postForm.entrySet();
            StringBuilder content = new StringBuilder();
            boolean isFirst = true;
            for (Map.Entry<String, String> entry : data) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    content.append('&');
                }
                content.append(entry.getKey()).append('=').
                        append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return content.toString();
        } else {
            return "";
        }
    }

    public static String getStringFromCookies(Map<String, String> cookies) {
        if (cookies != null && !cookies.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : cookies.entrySet()) {
                sb.append(item.getKey()).append("=").append(item.getValue()).append(";");
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
