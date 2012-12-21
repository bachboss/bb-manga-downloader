/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import bbmangadownloader.entity.Server;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Bach
 */
public class ServerManager {
    // Contains lower case character only !

    private static final HashMap<String, Server> mapServer = new HashMap<String, Server>();
    private static final HashMap<String, Server> mapScaner = new HashMap<String, Server>();

    public static void loadServer() {
        System.out.println("Loading servers...");
        FacadeManager.loadData();
        Set<Entry<String, IFacadeMangaServer>> set = FacadeManager.MAP_HOST.entrySet();
        for (Entry<String, IFacadeMangaServer> entry : set) {
            Server s = new Server(entry.getValue());
            mapServer.put(entry.getKey(), s);
            if (entry.getValue().getSupportType() == SupportType.Scanner) {
                mapScaner.put(entry.getKey(), s);
            }
        }
    }

    public static Server getServerByName(String name) {
        return mapServer.get(name.toLowerCase());
    }

    public static Server getServerByUrl(String url) {
        IFacadeMangaServer facade = FacadeManager.getServerFacadeByUrl(url);
        if (facade == null) {
            return null;
        }
        return getServerByName(facade.getServerName());
    }

    public static Map<String, Server> getMapScannerClone() {
        return (Map<String, Server>) mapScaner.clone();
    }

    public static Collection<Server> getAllServer() {
        return mapServer.values();
    }
}
