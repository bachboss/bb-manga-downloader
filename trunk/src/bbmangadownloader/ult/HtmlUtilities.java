/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Bach
 */
public class HtmlUtilities {

    public static String fixHTMLCharacters(String text) {
        return unescapeHtml4(unescapeHtml3(text));
    }

    public static String unescapeHtml4(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }

    public static String unescapeHtml3(String text) {
        return StringEscapeUtils.unescapeHtml3(text);
    }

    public static URL encodeUrl(String url) throws MalformedURLException, URISyntaxException, EncoderException {
        URL u = new URL(url);
        return encodeUrl(u);
    }

    public static URL encodeUrl(URL u, boolean useEncodeString) throws MalformedURLException, URISyntaxException, EncoderException {
        if (useEncodeString) {
            URL returnValue = new URI(
                    u.getProtocol(),
                    u.getAuthority(),
                    HtmlUtilities.encodeStringInURL(u.getPath()),
                    u.getQuery(),
                    u.getRef()).toURL();
            return returnValue;
        } else {
            URL returnValue = new URI(
                    u.getProtocol(),
                    u.getAuthority(),
                    u.getPath(),
                    u.getQuery(),
                    u.getRef()).toURL();
            return returnValue;
        }
    }

    public static URL encodeUrl(URL u) throws URISyntaxException, MalformedURLException, EncoderException {
        return encodeUrl(u, false);
    }

    private static String encodeString(String text) throws EncoderException {
        if (URL_CODEC == null) {
            URL_CODEC = new URLCodec();
        }
        String str = URL_CODEC.encode(text);
        return str;
    }

    private static String encodeStringInURL(String text) throws EncoderException {
        StringBuilder returnValue = new StringBuilder();
        String[] arr = text.split("/");
        for (int i = 0; i < arr.length - 1; i++) {
            returnValue.append(encodeString(arr[i])).append('/');
        }
        returnValue.append(encodeString(arr[arr.length - 1]));
        if (text.charAt(text.length() - 1) == '/') {
            returnValue.append('/');
        }
        return returnValue.toString();
    }
    private static URLCodec URL_CODEC;
//    public static void main(String[] args) throws Exception {
//        //http://truyen2.vnsharing.net/Uploads2/Manga/6-20-2012/227589-505-1310-881/-MTO-%20ToLoveRu%20Darkness%20chap%2020.5%20-%2024.jpg
//        //http://truyen2.vnsharing.net/Uploads2/Manga/6-20-2012/227589-505-1310-881/-MTO-%2520ToLoveRu%2520Darkness%2520chap%252020.5%2520-%252024.jpg
//
//
//        String url = "http://truyen.vnsharing.net/Truyen/To-Love-Ru-Darkness-MTO2T/Chapter-20-5-La%CC%80m-va%CC%A3y-lie%CC%A3u-co%CC%81-du%CC%81ng-khong?id=65172";
//        //"http://truyen2.vnsharing.net/Uploads2/Manga/6-20-2012/227589-505-1310-881/-MTO- ToLoveRu Darkness chap 20.5 - 24.jpg";
//        URL u = encodeUrl(new URL(url));
//        String text = u.toString();
//        System.out.println(text);
////        for (int i = 0; i < text.length(); i++) {
////            System.out.println(text.charAt(i));
////        }
//
//    }
}
