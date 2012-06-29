/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.faces;

import bbmangadownloader.entity.Server;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Bach
 */
public class ServerManager {
    // Contains lower case character only !

    private static final HashMap<String, Server> mapServer = new HashMap<>();

    public static void loadServer() {
        System.out.println("Loading servers...");
        ServerFacadeManager.loadData();
        Set<Entry<String, IFacadeMangaServer>> set = ServerFacadeManager.MAP_HOST.entrySet();
        for (Entry<String, IFacadeMangaServer> entry : set) {
            Server s = new Server(entry.getValue());
            mapServer.put(entry.getKey(), s);
        }
    }

    public static Server getServerByName(String name) {
        return mapServer.get(name.toLowerCase());
    }

    public static Server getServerByUrl(String url) {
        IFacadeMangaServer facade = ServerFacadeManager.getServerFacadeByUrl(url);
        return getServerByName(facade.getServerName());
    }
}
