/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.faces.SupportType;
import bbmangadownloader.ult.NumberUtilities;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class CacheBuilder {

    public static void main(String[] args) throws Exception {
        ConfigManager.loadOnStartUp();
        ServerManager.loadServer();

        ServerTempData[] servers = new ServerTempData[]{
            new ServerTempData(false, "http://www.batoto.net"),
            new ServerTempData(false, "http://eatmanga.com"),
            new ServerTempData(false, "http://kissmanga.com"),
            new ServerTempData(false, "http://mangafox.me"),
            new ServerTempData(false, "http://mangainn.com"),
            new ServerTempData(false, "http://www.mangareader.net"),
            new ServerTempData(false, "http://truyentranhtuan.com"),
            new ServerTempData(false, "http://truyen.vnsharing.net"),
            new ServerTempData(false, "http://mangastream.com/"),
            new ServerTempData(false, "http://mangahere.com/"),
            new ServerTempData(false, "http://cococomic.com/"),
            new ServerTempData(false, "http://99770.cc/"),
            new ServerTempData(true, "http://manga24h.com/"),
            new ServerTempData(false, "http://blogtruyen.com/")
        };

        for (ServerTempData sEE : servers) {
            if (sEE.isDownload) {
                Server server = ServerManager.getServerByUrl(sEE.serverUrl);
                IFacadeMangaServer facade = server.getMangaServer();
                if (facade.getSupportType() == SupportType.Support) {
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.println("Loading Server : " + server.getServerName());

                    File folderCache = new File("D:\\Manga\\Cache\\", server.getServerName());
                    folderCache.mkdirs();
                    File fileOutput = new File(folderCache, "file.data");

                    List<Manga> lstManga = facade.getAllMangas(server);
                    System.out.println("Parsing Done!");
                    Map<Integer, Manga> mapManga = new HashMap<Integer, Manga>();
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(new File(folderCache, "cache.xml"));

                        fw.write("<roots>\n");
                        for (Manga m : lstManga) {
                            if (m != null) {
                                Manga m2 = mapManga.put(m.getHashId(), m);
                                if (m2 == null) {
                                    fw.write("<manga>");
                                    fw.write(m.toXml());
                                    fw.write("</manga>\n");
                                }
                                m.setServer(null);
                            }
                        }
                        fw.write("</roots>");
                    } catch (IOException ex) {
                        Logger.getLogger(CacheBuilder.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fw.close();
                        } catch (Exception ex) {
                        }
                    }
                    ObjectOutputStream oos = null;
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(fileOutput));
                        System.out.println("Writed: " + lstManga.size() + " record(s)");
                        oos.writeObject(lstManga);
                    } finally {
                        try {
                            oos.close();
                        } catch (Exception ex) {
                        }
                    }
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOutput));
                        Object o = ois.readObject();
                        List<Manga> l = (List<Manga>) o;
                        System.out.println("Loaded: " + l.size() + " record(s)");
                        for (int j = 0; j < 10; j++) {
                            Manga m = l.get(NumberUtilities.getRandom(0, l.size() - 1));
                            System.out.println("Random record : "
                                    + m.getUrl() + "\t" + m.getMangaName());
                        }
                    } finally {
                        try {
                            oos.close();
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    System.out.println("Host Support = " + facade.getSupportType().toString());
                }
            }
        }
    }
}
