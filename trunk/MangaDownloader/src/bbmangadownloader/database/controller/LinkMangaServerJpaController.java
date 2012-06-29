/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database.controller;

import bbmangadownloader.database.controller.exceptions.NonexistentEntityException;
import bbmangadownloader.database.entity.LinkMangaServer;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bbmangadownloader.database.entity.Servers;
import bbmangadownloader.database.entity.Mangas;
import bbmangadownloader.database.entity.LinkWatcherLinkms;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Bach
 */
public class LinkMangaServerJpaController implements Serializable {

    public LinkMangaServerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LinkMangaServer linkMangaServer) {
        if (linkMangaServer.getLinkWatcherLinkmsList() == null) {
            linkMangaServer.setLinkWatcherLinkmsList(new ArrayList<LinkWatcherLinkms>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servers LMsServer = linkMangaServer.getLMsServer();
            if (LMsServer != null) {
                LMsServer = em.getReference(LMsServer.getClass(), LMsServer.getSId());
                linkMangaServer.setLMsServer(LMsServer);
            }
            Mangas LMsManga = linkMangaServer.getLMsManga();
            if (LMsManga != null) {
                LMsManga = em.getReference(LMsManga.getClass(), LMsManga.getMId());
                linkMangaServer.setLMsManga(LMsManga);
            }
            List<LinkWatcherLinkms> attachedLinkWatcherLinkmsList = new ArrayList<LinkWatcherLinkms>();
            for (LinkWatcherLinkms linkWatcherLinkmsListLinkWatcherLinkmsToAttach : linkMangaServer.getLinkWatcherLinkmsList()) {
                linkWatcherLinkmsListLinkWatcherLinkmsToAttach = em.getReference(linkWatcherLinkmsListLinkWatcherLinkmsToAttach.getClass(), linkWatcherLinkmsListLinkWatcherLinkmsToAttach.getLWlId());
                attachedLinkWatcherLinkmsList.add(linkWatcherLinkmsListLinkWatcherLinkmsToAttach);
            }
            linkMangaServer.setLinkWatcherLinkmsList(attachedLinkWatcherLinkmsList);
            em.persist(linkMangaServer);
            if (LMsServer != null) {
                LMsServer.getLinkMangaServerList().add(linkMangaServer);
                LMsServer = em.merge(LMsServer);
            }
            if (LMsManga != null) {
                LMsManga.getLinkMangaServerList().add(linkMangaServer);
                LMsManga = em.merge(LMsManga);
            }
            for (LinkWatcherLinkms linkWatcherLinkmsListLinkWatcherLinkms : linkMangaServer.getLinkWatcherLinkmsList()) {
                LinkMangaServer oldLWlLinmsOfLinkWatcherLinkmsListLinkWatcherLinkms = linkWatcherLinkmsListLinkWatcherLinkms.getLWlLinms();
                linkWatcherLinkmsListLinkWatcherLinkms.setLWlLinms(linkMangaServer);
                linkWatcherLinkmsListLinkWatcherLinkms = em.merge(linkWatcherLinkmsListLinkWatcherLinkms);
                if (oldLWlLinmsOfLinkWatcherLinkmsListLinkWatcherLinkms != null) {
                    oldLWlLinmsOfLinkWatcherLinkmsListLinkWatcherLinkms.getLinkWatcherLinkmsList().remove(linkWatcherLinkmsListLinkWatcherLinkms);
                    oldLWlLinmsOfLinkWatcherLinkmsListLinkWatcherLinkms = em.merge(oldLWlLinmsOfLinkWatcherLinkmsListLinkWatcherLinkms);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LinkMangaServer linkMangaServer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinkMangaServer persistentLinkMangaServer = em.find(LinkMangaServer.class, linkMangaServer.getLMsId());
            Servers LMsServerOld = persistentLinkMangaServer.getLMsServer();
            Servers LMsServerNew = linkMangaServer.getLMsServer();
            Mangas LMsMangaOld = persistentLinkMangaServer.getLMsManga();
            Mangas LMsMangaNew = linkMangaServer.getLMsManga();
            List<LinkWatcherLinkms> linkWatcherLinkmsListOld = persistentLinkMangaServer.getLinkWatcherLinkmsList();
            List<LinkWatcherLinkms> linkWatcherLinkmsListNew = linkMangaServer.getLinkWatcherLinkmsList();
            if (LMsServerNew != null) {
                LMsServerNew = em.getReference(LMsServerNew.getClass(), LMsServerNew.getSId());
                linkMangaServer.setLMsServer(LMsServerNew);
            }
            if (LMsMangaNew != null) {
                LMsMangaNew = em.getReference(LMsMangaNew.getClass(), LMsMangaNew.getMId());
                linkMangaServer.setLMsManga(LMsMangaNew);
            }
            List<LinkWatcherLinkms> attachedLinkWatcherLinkmsListNew = new ArrayList<LinkWatcherLinkms>();
            for (LinkWatcherLinkms linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach : linkWatcherLinkmsListNew) {
                linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach = em.getReference(linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach.getClass(), linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach.getLWlId());
                attachedLinkWatcherLinkmsListNew.add(linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach);
            }
            linkWatcherLinkmsListNew = attachedLinkWatcherLinkmsListNew;
            linkMangaServer.setLinkWatcherLinkmsList(linkWatcherLinkmsListNew);
            linkMangaServer = em.merge(linkMangaServer);
            if (LMsServerOld != null && !LMsServerOld.equals(LMsServerNew)) {
                LMsServerOld.getLinkMangaServerList().remove(linkMangaServer);
                LMsServerOld = em.merge(LMsServerOld);
            }
            if (LMsServerNew != null && !LMsServerNew.equals(LMsServerOld)) {
                LMsServerNew.getLinkMangaServerList().add(linkMangaServer);
                LMsServerNew = em.merge(LMsServerNew);
            }
            if (LMsMangaOld != null && !LMsMangaOld.equals(LMsMangaNew)) {
                LMsMangaOld.getLinkMangaServerList().remove(linkMangaServer);
                LMsMangaOld = em.merge(LMsMangaOld);
            }
            if (LMsMangaNew != null && !LMsMangaNew.equals(LMsMangaOld)) {
                LMsMangaNew.getLinkMangaServerList().add(linkMangaServer);
                LMsMangaNew = em.merge(LMsMangaNew);
            }
            for (LinkWatcherLinkms linkWatcherLinkmsListOldLinkWatcherLinkms : linkWatcherLinkmsListOld) {
                if (!linkWatcherLinkmsListNew.contains(linkWatcherLinkmsListOldLinkWatcherLinkms)) {
                    linkWatcherLinkmsListOldLinkWatcherLinkms.setLWlLinms(null);
                    linkWatcherLinkmsListOldLinkWatcherLinkms = em.merge(linkWatcherLinkmsListOldLinkWatcherLinkms);
                }
            }
            for (LinkWatcherLinkms linkWatcherLinkmsListNewLinkWatcherLinkms : linkWatcherLinkmsListNew) {
                if (!linkWatcherLinkmsListOld.contains(linkWatcherLinkmsListNewLinkWatcherLinkms)) {
                    LinkMangaServer oldLWlLinmsOfLinkWatcherLinkmsListNewLinkWatcherLinkms = linkWatcherLinkmsListNewLinkWatcherLinkms.getLWlLinms();
                    linkWatcherLinkmsListNewLinkWatcherLinkms.setLWlLinms(linkMangaServer);
                    linkWatcherLinkmsListNewLinkWatcherLinkms = em.merge(linkWatcherLinkmsListNewLinkWatcherLinkms);
                    if (oldLWlLinmsOfLinkWatcherLinkmsListNewLinkWatcherLinkms != null && !oldLWlLinmsOfLinkWatcherLinkmsListNewLinkWatcherLinkms.equals(linkMangaServer)) {
                        oldLWlLinmsOfLinkWatcherLinkmsListNewLinkWatcherLinkms.getLinkWatcherLinkmsList().remove(linkWatcherLinkmsListNewLinkWatcherLinkms);
                        oldLWlLinmsOfLinkWatcherLinkmsListNewLinkWatcherLinkms = em.merge(oldLWlLinmsOfLinkWatcherLinkmsListNewLinkWatcherLinkms);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linkMangaServer.getLMsId();
                if (findLinkMangaServer(id) == null) {
                    throw new NonexistentEntityException("The linkMangaServer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinkMangaServer linkMangaServer;
            try {
                linkMangaServer = em.getReference(LinkMangaServer.class, id);
                linkMangaServer.getLMsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linkMangaServer with id " + id + " no longer exists.", enfe);
            }
            Servers LMsServer = linkMangaServer.getLMsServer();
            if (LMsServer != null) {
                LMsServer.getLinkMangaServerList().remove(linkMangaServer);
                LMsServer = em.merge(LMsServer);
            }
            Mangas LMsManga = linkMangaServer.getLMsManga();
            if (LMsManga != null) {
                LMsManga.getLinkMangaServerList().remove(linkMangaServer);
                LMsManga = em.merge(LMsManga);
            }
            List<LinkWatcherLinkms> linkWatcherLinkmsList = linkMangaServer.getLinkWatcherLinkmsList();
            for (LinkWatcherLinkms linkWatcherLinkmsListLinkWatcherLinkms : linkWatcherLinkmsList) {
                linkWatcherLinkmsListLinkWatcherLinkms.setLWlLinms(null);
                linkWatcherLinkmsListLinkWatcherLinkms = em.merge(linkWatcherLinkmsListLinkWatcherLinkms);
            }
            em.remove(linkMangaServer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LinkMangaServer> findLinkMangaServerEntities() {
        return findLinkMangaServerEntities(true, -1, -1);
    }

    public List<LinkMangaServer> findLinkMangaServerEntities(int maxResults, int firstResult) {
        return findLinkMangaServerEntities(false, maxResults, firstResult);
    }

    private List<LinkMangaServer> findLinkMangaServerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LinkMangaServer.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public LinkMangaServer findLinkMangaServer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LinkMangaServer.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinkMangaServerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LinkMangaServer> rt = cq.from(LinkMangaServer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
