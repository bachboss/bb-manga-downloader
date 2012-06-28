package mangadownloader.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import mangadownloader.database.controller.*;
import mangadownloader.database.controller.exceptions.NonexistentEntityException;
import mangadownloader.database.controller.exceptions.PreexistingEntityException;
import mangadownloader.database.entity.*;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Bach
 */
public class Database {

    private static EntityManagerFactory emf;
    public static String persitenceUnitPU = "MangaDownloaderDerbyPU";

    public synchronized static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            System.out.println("Loading Entity Mangager Factory of " + persitenceUnitPU);
            emf = Persistence.createEntityManagerFactory(persitenceUnitPU);
        }
        return emf;
    }

    public synchronized static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void createServer(Servers s) throws PreexistingEntityException, Exception {
        ServersJpaController sC = new ServersJpaController(getEntityManagerFactory());
        sC.create(s);
    }

    public static Servers getServerByName(String name) {
        EntityManager em = getEntityManager();
        List lst = em.createNamedQuery("Servers.findBySName").setParameter("sName", name).getResultList();
        if (lst != null && !lst.isEmpty()) {
            return (Servers) lst.get(0);
        } else {
            return null;
        }
    }

    public static Mangas getMangaByName(String name) {
        EntityManager em = getEntityManager();
        List lst = em.createNamedQuery("Mangas.findByMName").setParameter("mName", name).getResultList();
        if (lst != null && !lst.isEmpty()) {
            return (Mangas) lst.get(0);
        } else {
            return null;
        }
    }

    public static List<Mangas> getMangaLikeName(String name) {
        EntityManager em = getEntityManager();
        List lst = em.createQuery(
                "SELECT m FROM Mangas m WHERE UPPER(m.mName) LIKE UPPER(:mName)").
                setParameter("mName", name).
                getResultList();

        if (lst != null && !lst.isEmpty()) {
            return lst;
        } else {
            return lst;
        }
    }

    public static void createManga(Mangas m) throws PreexistingEntityException, Exception {
        MangasJpaController mC = new MangasJpaController(getEntityManagerFactory());
        mC.create(m);
    }

    public static void createLinkMangaServer(LinkMangaServer link) throws PreexistingEntityException, Exception {
        LinkMangaServerJpaController lC = new LinkMangaServerJpaController(getEntityManagerFactory());
        lC.create(link);
    }

    public static LinkMangaServer getLinkMangaServerByMangaServer(Mangas m, Servers s) {
        EntityManager em = getEntityManager();
        List lst = em.createQuery(
                "SELECT l FROM LinkMangaServer l WHERE l.lMsServer.sId = :sId AND l.lMsManga.mId = :mId").
                setParameter("sId", s.getSId()).
                setParameter("mId", m.getMId()).
                getResultList();

        if (lst != null && !lst.isEmpty()) {
            return (LinkMangaServer) lst.get(0);
        } else {
            return null;
        }
    }

    public static void updateLinkMangaServer(LinkMangaServer link) throws NonexistentEntityException, Exception {
        LinkMangaServerJpaController lC = new LinkMangaServerJpaController(getEntityManagerFactory());
        lC.edit(link);
    }

    public static List<Mangas> getAllManga() {
        MangasJpaController mC = new MangasJpaController(getEntityManagerFactory());
        return mC.findMangasEntities();
    }

    public static List<LinkMangaServer> getAllLinkMangaServer() {
        LinkMangaServerJpaController lC = new LinkMangaServerJpaController(getEntityManagerFactory());
        return lC.findLinkMangaServerEntities();
    }

    public static List<Watchers> getAllWatchers() {
        WatchersJpaController wC = new WatchersJpaController(getEntityManagerFactory());
        return wC.findWatchersEntities();
    }

    public static int createWatcher(Watchers w) {
        WatchersJpaController wC = new WatchersJpaController(getEntityManagerFactory());
        wC.create(w);
        return w.getWId();
    }

    public static int createLinkWatcherLinkMs(LinkMangaServer l, int id) {
        LinkWatcherLinkmsJpaController lC = new LinkWatcherLinkmsJpaController(getEntityManagerFactory());
        LinkWatcherLinkms link = new LinkWatcherLinkms();
        link.setLWatcher(new Watchers(id));
        link.setLWlLinms(l);
        lC.create(link);
        return link.getLWlId();
    }

    public static void editWatcher(Watchers wE) throws NonexistentEntityException, Exception {
        WatchersJpaController wC = new WatchersJpaController(getEntityManagerFactory());
        wC.edit(wE);
    }
}
