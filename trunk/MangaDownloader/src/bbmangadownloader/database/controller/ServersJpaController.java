/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database.controller;

import bbmangadownloader.database.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bbmangadownloader.database.entity.LinkMangaServer;
import bbmangadownloader.database.entity.Servers;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Bach
 */
public class ServersJpaController implements Serializable {

    public ServersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servers servers) {
        if (servers.getLinkMangaServerList() == null) {
            servers.setLinkMangaServerList(new ArrayList<LinkMangaServer>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<LinkMangaServer> attachedLinkMangaServerList = new ArrayList<LinkMangaServer>();
            for (LinkMangaServer linkMangaServerListLinkMangaServerToAttach : servers.getLinkMangaServerList()) {
                linkMangaServerListLinkMangaServerToAttach = em.getReference(linkMangaServerListLinkMangaServerToAttach.getClass(), linkMangaServerListLinkMangaServerToAttach.getLMsId());
                attachedLinkMangaServerList.add(linkMangaServerListLinkMangaServerToAttach);
            }
            servers.setLinkMangaServerList(attachedLinkMangaServerList);
            em.persist(servers);
            for (LinkMangaServer linkMangaServerListLinkMangaServer : servers.getLinkMangaServerList()) {
                Servers oldLMsServerOfLinkMangaServerListLinkMangaServer = linkMangaServerListLinkMangaServer.getLMsServer();
                linkMangaServerListLinkMangaServer.setLMsServer(servers);
                linkMangaServerListLinkMangaServer = em.merge(linkMangaServerListLinkMangaServer);
                if (oldLMsServerOfLinkMangaServerListLinkMangaServer != null) {
                    oldLMsServerOfLinkMangaServerListLinkMangaServer.getLinkMangaServerList().remove(linkMangaServerListLinkMangaServer);
                    oldLMsServerOfLinkMangaServerListLinkMangaServer = em.merge(oldLMsServerOfLinkMangaServerListLinkMangaServer);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servers servers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servers persistentServers = em.find(Servers.class, servers.getSId());
            List<LinkMangaServer> linkMangaServerListOld = persistentServers.getLinkMangaServerList();
            List<LinkMangaServer> linkMangaServerListNew = servers.getLinkMangaServerList();
            List<LinkMangaServer> attachedLinkMangaServerListNew = new ArrayList<LinkMangaServer>();
            for (LinkMangaServer linkMangaServerListNewLinkMangaServerToAttach : linkMangaServerListNew) {
                linkMangaServerListNewLinkMangaServerToAttach = em.getReference(linkMangaServerListNewLinkMangaServerToAttach.getClass(), linkMangaServerListNewLinkMangaServerToAttach.getLMsId());
                attachedLinkMangaServerListNew.add(linkMangaServerListNewLinkMangaServerToAttach);
            }
            linkMangaServerListNew = attachedLinkMangaServerListNew;
            servers.setLinkMangaServerList(linkMangaServerListNew);
            servers = em.merge(servers);
            for (LinkMangaServer linkMangaServerListOldLinkMangaServer : linkMangaServerListOld) {
                if (!linkMangaServerListNew.contains(linkMangaServerListOldLinkMangaServer)) {
                    linkMangaServerListOldLinkMangaServer.setLMsServer(null);
                    linkMangaServerListOldLinkMangaServer = em.merge(linkMangaServerListOldLinkMangaServer);
                }
            }
            for (LinkMangaServer linkMangaServerListNewLinkMangaServer : linkMangaServerListNew) {
                if (!linkMangaServerListOld.contains(linkMangaServerListNewLinkMangaServer)) {
                    Servers oldLMsServerOfLinkMangaServerListNewLinkMangaServer = linkMangaServerListNewLinkMangaServer.getLMsServer();
                    linkMangaServerListNewLinkMangaServer.setLMsServer(servers);
                    linkMangaServerListNewLinkMangaServer = em.merge(linkMangaServerListNewLinkMangaServer);
                    if (oldLMsServerOfLinkMangaServerListNewLinkMangaServer != null && !oldLMsServerOfLinkMangaServerListNewLinkMangaServer.equals(servers)) {
                        oldLMsServerOfLinkMangaServerListNewLinkMangaServer.getLinkMangaServerList().remove(linkMangaServerListNewLinkMangaServer);
                        oldLMsServerOfLinkMangaServerListNewLinkMangaServer = em.merge(oldLMsServerOfLinkMangaServerListNewLinkMangaServer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servers.getSId();
                if (findServers(id) == null) {
                    throw new NonexistentEntityException("The servers with id " + id + " no longer exists.");
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
            Servers servers;
            try {
                servers = em.getReference(Servers.class, id);
                servers.getSId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servers with id " + id + " no longer exists.", enfe);
            }
            List<LinkMangaServer> linkMangaServerList = servers.getLinkMangaServerList();
            for (LinkMangaServer linkMangaServerListLinkMangaServer : linkMangaServerList) {
                linkMangaServerListLinkMangaServer.setLMsServer(null);
                linkMangaServerListLinkMangaServer = em.merge(linkMangaServerListLinkMangaServer);
            }
            em.remove(servers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servers> findServersEntities() {
        return findServersEntities(true, -1, -1);
    }

    public List<Servers> findServersEntities(int maxResults, int firstResult) {
        return findServersEntities(false, maxResults, firstResult);
    }

    private List<Servers> findServersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servers.class));
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

    public Servers findServers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servers.class, id);
        } finally {
            em.close();
        }
    }

    public int getServersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servers> rt = cq.from(Servers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
