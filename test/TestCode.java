
import bbmangadownloader.ult.HtmlUtilities;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bach
 */
public class TestCode {

    public static void main(String[] args) {
        String url = "http://media2.manga24h.com/data/2013-05-03/350cff5bac57d0bcd7755ea3462e708f/img000004+copy.JPG?imgmax=1600";
        try {
//            System.out.println(URLEncoder.encode(url, "UTF-8"));
            System.out.println(HtmlUtilities.encodeUrl(new URL(url), true));
            System.out.println(HtmlUtilities.encodeUrl(new URL(url), false));
        } catch (Exception ex) {
            Logger.getLogger(TestCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
