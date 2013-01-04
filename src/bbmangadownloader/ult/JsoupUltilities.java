/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/**
 *
 * @author Bach
 */
public class JsoupUltilities {

    private static void getTexts(Node node, ArrayList<String> list) {
        List<Node> lstNode = node.childNodes();
        for (Node n : lstNode) {
            if ("#text".equals(n.nodeName())) {
                String txt = n.toString().trim();
                if (!txt.isEmpty()) {
                    list.add(txt);
                }
            } else {
                getTexts(n, list);
            }
        }
    }

    /**
     * Get all text in an element
     * @param e Element input tag
     * @return List of String.trim() in that node, with the order of DFS
     */
    public static ArrayList<String> getTexts(Element e) {
        ArrayList<String> returnValue = new ArrayList<String>();
        getTexts(e, returnValue);
        return returnValue;
    }
}
