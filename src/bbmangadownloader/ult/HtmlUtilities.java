/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

    public static URL encodeUrl(String url)
            throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
        URL u = new URL(url);
        return encodeUrl(u);
    }

    public static URL encodeUrl(URL u, boolean useEncodeString)
            throws MalformedURLException, URISyntaxException {
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

    public static URL encodeUrl(URL u)
            throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        return encodeUrl(u, false);
    }

    private static String encodeString(String text) {
//        if (URL_CODEC == null) {
//            URL_CODEC = new URLCodec();
//        }        
//        String str = URL_CODEC.encode(text);
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (Exception ex) {
            return text;
        }
    }

    private static String encodeStringInURL(String text) {
        if (text.length() == 0) {
            return text;
        }
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
//    private static URLCodec URL_CODEC;

    public static void doGenerate(String title, File folderImages) throws IOException {
        if (folderImages.isDirectory()) {
            File[] lstFile = folderImages.listFiles(FileUtilities.ImageFileNameFilter.getInstance());
            Arrays.sort(lstFile);
            Document doc = new Document("");
            Element eHtml = doc.appendElement("html");
            Element eHead = eHtml.appendElement("head");
            Element eTitle = eHead.appendElement("title");
            eTitle.html(title);
            Element eBody = eHtml.appendElement("body");
            eBody.attr("style", "text-align:center;background-color:black;");
            for (File f : lstFile) {
                eBody.append("<image src=\"" + f.getName() + "\"/>");
//                Element eImage = eBody.appendElement("image");
//                eImage.attr("src", f.getName());
            }

            FileUtilities.writeStringToFile(doc.toString(), new File(folderImages, "index.html"));
        }
    }
}
