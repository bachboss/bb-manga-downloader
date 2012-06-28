/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.database.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import mangadownloader.database.controller.exceptions.NonexistentEntityException;
import mangadownloader.database.entity.LinkMangaServer;
import mangadownloader.database.entity.LinkWatcherLinkms;
import mangadownloader.database.entity.Watchers;

/**
 *
 * @author Bach
 */
public class LinkWatcherLinkmsJpaController implements Serializable {

    public LinkWatcherLinkmsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LinkWatcherLinkms linkWatcherLinkms) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Watchers LWatcher = linkWatcherLinkms.getLWatcher();
            if (LWatcher != null) {
                LWatcher = em.getReference(LWatcher.getClass(), LWatcher.getWId());
                linkWatcherLinkms.setLWatcher(LWatcher);
            }
            LinkMangaServer LWlLinms = linkWatcherLinkms.getLWlLinms();
            if (LWlLinms != null) {
                LWlLinms = em.getReference(LWlLinms.getClass(), LWlLinms.getLMsId());
                linkWatcherLinkms.setLWlLinms(LWlLinms);
            }
            em.persist(linkWatcherLinkms);
            if (LWatcher != null) {
                LWatcher.getLinkWatcherLinkmsList().add(linkWatcherLinkms);
                LWatcher = em.merge(LWatcher);
            }
            if (LWlLinms != null) {
                LWlLinms.getLinkWatcherLinkmsList().add(linkWatcherLinkms);
                LWlLinms = em.merge(LWlLinms);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LinkWatcherLinkms linkWatcherLinkms) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinkWatcherLinkms persistentLinkWatcherLinkms = em.find(LinkWatcherLinkms.class, linkWatcherLinkms.getLWlId());
            Watchers LWatcherOld = persistentLinkWatcherLinkms.getLWatcher();
            Watchers LWatcherNew = linkWatcherLinkms.getLWatcher();
            LinkMangaServer LWlLinmsOld = persistentLinkWatcherLinkms.getLWlLinms();
            LinkMangaServer LWlLinmsNew = linkWatcherLinkms.getLWlLinms();
            if (LWatcherNew != null) {
                LWatcherNew = em.getReference(LWatcherNew.getClass(), LWatcherNew.getWId());
                linkWatcherLinkms.setLWatcher(LWatcherNew);
            }
            if (LWlLinmsNew != null) {
                LWlLinmsNew = em.getReference(LWlLinmsNew.getClass(), LWlLinmsNew.getLMsId());
                linkWatcherLinkms.setLWlLinms(LWlLinmsNew);
            }
            linkWatcherLinkms = em.merge(linkWatcherLinkms);
            if (LWatcherOld != null && !LWatcherOld.equals(LWatcherNew)) {
                LWatcherOld.getLinkWatcherLinkmsList().remove(linkWatcherLinkms);
                LWatcherOld = em.merge(LWatcherOld);
            }
            if (LWatcherNew != null && !LWatcherNew.equals(LWatcherOld)) {
                LWatcherNew.getLinkWatcherLinkmsList().add(linkWatcherLinkms);
                LWatcherNew = em.merge(LWatcherNew);
            }
            if (LWlLinmsOld != null && !LWlLinmsOld.equals(LWlLinmsNew)) {
                LWlLinmsOld.getLinkWatcherLinkmsList().remove(linkWatcherLinkms);
                LWlLinmsOld = em.merge(LWlLinmsOld);
            }
            if (LWlLinmsNew != null && !LWlLinmsNew.equals(LWlLinmsOld)) {
                LWlLinmsNew.getLinkWatcherLinkmsList().add(linkWatcherLinkms);
                LWlLinmsNew = em.merge(LWlLinmsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linkWatcherLinkms.getLWlId();
                if (findLinkWatcherLinkms(id) == null) {
                    throw new NonexistentEntityException("The linkWatcherLinkms with id " + id + " no longer exists.");
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
            LinkWatcherLinkms linkWatcherLinkms;
            try {
                linkWatcherLinkms = em.getReference(LinkWatcherLinkms.class, id);
                linkWatcherLinkms.getLWlId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linkWatcherLinkms with id " + id + " no longer exists.", enfe);
            }
            Watchers LWatcher = linkWatcherLinkms.getLWatcher();
            if (LWatcher != null) {
                LWatcher.getLinkWatcherLinkmsList().remove(linkWatcherLinkms);
                LWatcher = em.merge(LWatcher);
            }
            LinkMangaServer LWlLinms = linkWatcherLinkms.getLWlLinms();
            if (LWlLinms != null) {
                LWlLinms.getLinkWatcherLinkmsList().remove(linkWatcherLinkms);
                LWlLinms = em.merge(LWlLinms);
            }
            em.remove(linkWatcherLinkms);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LinkWatcherLinkms> findLinkWatcherLinkmsEntities() {
        return findLinkWatcherLinkmsEntities(true, -1, -1);
    }

    public List<LinkWatcherLinkms> findLinkWatcherLinkmsEntities(int maxResults, int firstResult) {
        return findLinkWatcherLinkmsEntities(false, maxResults, firstResult);
    }

    private List<LinkWatcherLinkms> findLinkWatcherLinkmsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LinkWatcherLinkms.class));
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

    public LinkWatcherLinkms findLinkWatcherLinkms(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LinkWatcherLinkms.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinkWatcherLinkmsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LinkWatcherLinkms> rt = cq.from(LinkWatcherLinkms.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
