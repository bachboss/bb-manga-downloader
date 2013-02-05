/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import bbmangadownloader.faces.FacadeManager;
import bbmangadownloader.faces.IFacadeMangaServer;
import java.io.Serializable;

/**
 *
 * @author Bach
 */
public class Server extends HtmlDocument implements Serializable {

    public static final Server EMPTY_SERVER = new Server(FacadeManager.FACADE_EMPTY);
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

    public int getIdHashCode() {
        if (serverNameHashCode == -1) {
            synchronized (this) {
                if (serverNameHashCode == -1) {
                    serverNameHashCode = serverName.hashCode();
                }
            }
        }
        return serverNameHashCode;
    }
    int serverNameHashCode = -1;

    @Override
    public String toString() {
        return serverName;
    }

    @Override
    public int hashCode() {
        return getIdHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Server other = (Server) obj;
        if (this.mangaServer != other.mangaServer && (this.mangaServer == null || !this.mangaServer.equals(other.mangaServer))) {
            return false;
        }
        if ((this.serverName == null) ? (other.serverName != null) : !this.serverName.equals(other.serverName)) {
            return false;
        }
        return true;
    }
}
