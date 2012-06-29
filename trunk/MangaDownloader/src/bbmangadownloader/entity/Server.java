/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import java.io.Serializable;
import bbmangadownloader.faces.IFacadeMangaServer;

/**
 *
 * @author Bach
 */
public class Server extends HtmlDocument implements Serializable {

    private IFacadeMangaServer mangaServer;
    private String serverName;

    public Server(IFacadeMangaServer mangaServer) {
        this.mangaServer = mangaServer;
        this.serverName = mangaServer.getServerName();
    }

    public Server(String serverName) {
        this.serverName = serverName;
    }

    public IFacadeMangaServer getMangaServer() {
        return mangaServer;
    }

    public void setMangaServer(IFacadeMangaServer mangaServer) {
        this.mangaServer = mangaServer;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
