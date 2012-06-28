/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.database.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import mangadownloader.database.entity.LinkMangaServer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import mangadownloader.database.controller.exceptions.NonexistentEntityException;
import mangadownloader.database.entity.Mangas;

/**
 *
 * @author Bach
 */
public class MangasJpaController implements Serializable {

    public MangasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mangas mangas) {
        if (mangas.getLinkMangaServerList() == null) {
            mangas.setLinkMangaServerList(new ArrayList<LinkMangaServer>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<LinkMangaServer> attachedLinkMangaServerList = new ArrayList<>();
            for (LinkMangaServer linkMangaServerListLinkMangaServerToAttach : mangas.getLinkMangaServerList()) {
                linkMangaServerListLinkMangaServerToAttach = em.getReference(linkMangaServerListLinkMangaServerToAttach.getClass(), linkMangaServerListLinkMangaServerToAttach.getLMsId());
                attachedLinkMangaServerList.add(linkMangaServerListLinkMangaServerToAttach);
            }
            mangas.setLinkMangaServerList(attachedLinkMangaServerList);
            em.persist(mangas);
            for (LinkMangaServer linkMangaServerListLinkMangaServer : mangas.getLinkMangaServerList()) {
                Mangas oldLMsMangaOfLinkMangaServerListLinkMangaServer = linkMangaServerListLinkMangaServer.getLMsManga();
                linkMangaServerListLinkMangaServer.setLMsManga(mangas);
                linkMangaServerListLinkMangaServer = em.merge(linkMangaServerListLinkMangaServer);
                if (oldLMsMangaOfLinkMangaServerListLinkMangaServer != null) {
                    oldLMsMangaOfLinkMangaServerListLinkMangaServer.getLinkMangaServerList().remove(linkMangaServerListLinkMangaServer);
                    oldLMsMangaOfLinkMangaServerListLinkMangaServer = em.merge(oldLMsMangaOfLinkMangaServerListLinkMangaServer);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mangas mangas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mangas persistentMangas = em.find(Mangas.class, mangas.getMId());
            List<LinkMangaServer> linkMangaServerListOld = persistentMangas.getLinkMangaServerList();
            List<LinkMangaServer> linkMangaServerListNew = mangas.getLinkMangaServerList();
            List<LinkMangaServer> attachedLinkMangaServerListNew = new ArrayList<>();
            for (LinkMangaServer linkMangaServerListNewLinkMangaServerToAttach : linkMangaServerListNew) {
                linkMangaServerListNewLinkMangaServerToAttach = em.getReference(linkMangaServerListNewLinkMangaServerToAttach.getClass(), linkMangaServerListNewLinkMangaServerToAttach.getLMsId());
                attachedLinkMangaServerListNew.add(linkMangaServerListNewLinkMangaServerToAttach);
            }
            linkMangaServerListNew = attachedLinkMangaServerListNew;
            mangas.setLinkMangaServerList(linkMangaServerListNew);
            mangas = em.merge(mangas);
            for (LinkMangaServer linkMangaServerListOldLinkMangaServer : linkMangaServerListOld) {
                if (!linkMangaServerListNew.contains(linkMangaServerListOldLinkMangaServer)) {
                    linkMangaServerListOldLinkMangaServer.setLMsManga(null);
                    linkMangaServerListOldLinkMangaServer = em.merge(linkMangaServerListOldLinkMangaServer);
                }
            }
            for (LinkMangaServer linkMangaServerListNewLinkMangaServer : linkMangaServerListNew) {
                if (!linkMangaServerListOld.contains(linkMangaServerListNewLinkMangaServer)) {
                    Mangas oldLMsMangaOfLinkMangaServerListNewLinkMangaServer = linkMangaServerListNewLinkMangaServer.getLMsManga();
                    linkMangaServerListNewLinkMangaServer.setLMsManga(mangas);
                    linkMangaServerListNewLinkMangaServer = em.merge(linkMangaServerListNewLinkMangaServer);
                    if (oldLMsMangaOfLinkMangaServerListNewLinkMangaServer != null && !oldLMsMangaOfLinkMangaServerListNewLinkMangaServer.equals(mangas)) {
                        oldLMsMangaOfLinkMangaServerListNewLinkMangaServer.getLinkMangaServerList().remove(linkMangaServerListNewLinkMangaServer);
                        oldLMsMangaOfLinkMangaServerListNewLinkMangaServer = em.merge(oldLMsMangaOfLinkMangaServerListNewLinkMangaServer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mangas.getMId();
                if (findMangas(id) == null) {
                    throw new NonexistentEntityException("The mangas with id " + id + " no longer exists.");
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
            Mangas mangas;
            try {
                mangas = em.getReference(Mangas.class, id);
                mangas.getMId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mangas with id " + id + " no longer exists.", enfe);
            }
            List<LinkMangaServer> linkMangaServerList = mangas.getLinkMangaServerList();
            for (LinkMangaServer linkMangaServerListLinkMangaServer : linkMangaServerList) {
                linkMangaServerListLinkMangaServer.setLMsManga(null);
                linkMangaServerListLinkMangaServer = em.merge(linkMangaServerListLinkMangaServer);
            }
            em.remove(mangas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mangas> findMangasEntities() {
        return findMangasEntities(true, -1, -1);
    }

    public List<Mangas> findMangasEntities(int maxResults, int firstResult) {
        return findMangasEntities(false, maxResults, firstResult);
    }

    private List<Mangas> findMangasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mangas.class));
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

    public Mangas findMangas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mangas.class, id);
        } finally {
            em.close();
        }
    }

    public int getMangasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mangas> rt = cq.from(Mangas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
