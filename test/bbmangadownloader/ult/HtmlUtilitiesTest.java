/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.net.URL;

/**
 *
 * @author Bach
 */
public class HtmlUtilitiesTest {

    public static void main(String[] args) throws Exception {
        String[] testURL = new String[]{
            "http://truyen2.vnsharing.net/Uploads2/Manga/6-20-2012/227589-505-1310-881/-MTO-%20ToLoveRu%20Darkness%20chap%2020.5%20-%2024.jpg",
            "http://truyen2.vnsharing.net/Uploads2/Manga/6-20-2012/227589-505-1310-881/-MTO-%2520ToLoveRu%2520Darkness%2520chap%252020.5%2520-%252024.jpg",
            "http://truyen.vnsharing.net/Truyen/To-Love-Ru-Darkness-MTO2T/Chapter-20-5-La%CC%80m-va%CC%A3y-lie%CC%A3u-co%CC%81-du%CC%81ng-khong?id=65172",
            "http://truyen2.vnsharing.net/Uploads2/Manga/6-20-2012/227589-505-1310-881/-MTO- ToLoveRu Darkness chap 20.5 - 24.jpg"
        };

        for (String url : testURL) {
            URL u = new URL(url);
            System.out.println(
                    HtmlUtilities.encodeUrl(u, true).toString());
            System.out.println(
                    HtmlUtilities.encodeUrl(u, false).toString());
        }
    }
}