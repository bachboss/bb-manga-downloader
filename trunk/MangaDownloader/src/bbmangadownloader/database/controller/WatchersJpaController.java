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
import mangadownloader.database.entity.LinkWatcherLinkms;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import mangadownloader.database.controller.exceptions.NonexistentEntityException;
import mangadownloader.database.entity.Watchers;

/**
 *
 * @author Bach
 */
public class WatchersJpaController implements Serializable {

    public WatchersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Watchers watchers) {
        if (watchers.getLinkWatcherLinkmsList() == null) {
            watchers.setLinkWatcherLinkmsList(new ArrayList<LinkWatcherLinkms>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<LinkWatcherLinkms> attachedLinkWatcherLinkmsList = new ArrayList<>();
            for (LinkWatcherLinkms linkWatcherLinkmsListLinkWatcherLinkmsToAttach : watchers.getLinkWatcherLinkmsList()) {
                linkWatcherLinkmsListLinkWatcherLinkmsToAttach = em.getReference(linkWatcherLinkmsListLinkWatcherLinkmsToAttach.getClass(), linkWatcherLinkmsListLinkWatcherLinkmsToAttach.getLWlId());
                attachedLinkWatcherLinkmsList.add(linkWatcherLinkmsListLinkWatcherLinkmsToAttach);
            }
            watchers.setLinkWatcherLinkmsList(attachedLinkWatcherLinkmsList);
            em.persist(watchers);
            for (LinkWatcherLinkms linkWatcherLinkmsListLinkWatcherLinkms : watchers.getLinkWatcherLinkmsList()) {
                Watchers oldLWatcherOfLinkWatcherLinkmsListLinkWatcherLinkms = linkWatcherLinkmsListLinkWatcherLinkms.getLWatcher();
                linkWatcherLinkmsListLinkWatcherLinkms.setLWatcher(watchers);
                linkWatcherLinkmsListLinkWatcherLinkms = em.merge(linkWatcherLinkmsListLinkWatcherLinkms);
                if (oldLWatcherOfLinkWatcherLinkmsListLinkWatcherLinkms != null) {
                    oldLWatcherOfLinkWatcherLinkmsListLinkWatcherLinkms.getLinkWatcherLinkmsList().remove(linkWatcherLinkmsListLinkWatcherLinkms);
                    oldLWatcherOfLinkWatcherLinkmsListLinkWatcherLinkms = em.merge(oldLWatcherOfLinkWatcherLinkmsListLinkWatcherLinkms);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Watchers watchers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Watchers persistentWatchers = em.find(Watchers.class, watchers.getWId());
            List<LinkWatcherLinkms> linkWatcherLinkmsListOld = persistentWatchers.getLinkWatcherLinkmsList();
            List<LinkWatcherLinkms> linkWatcherLinkmsListNew = watchers.getLinkWatcherLinkmsList();
            List<LinkWatcherLinkms> attachedLinkWatcherLinkmsListNew = new ArrayList<>();
            for (LinkWatcherLinkms linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach : linkWatcherLinkmsListNew) {
                linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach = em.getReference(linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach.getClass(), linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach.getLWlId());
                attachedLinkWatcherLinkmsListNew.add(linkWatcherLinkmsListNewLinkWatcherLinkmsToAttach);
            }
            linkWatcherLinkmsListNew = attachedLinkWatcherLinkmsListNew;
            watchers.setLinkWatcherLinkmsList(linkWatcherLinkmsListNew);
            watchers = em.merge(watchers);
            for (LinkWatcherLinkms linkWatcherLinkmsListOldLinkWatcherLinkms : linkWatcherLinkmsListOld) {
                if (!linkWatcherLinkmsListNew.contains(linkWatcherLinkmsListOldLinkWatcherLinkms)) {
                    linkWatcherLinkmsListOldLinkWatcherLinkms.setLWatcher(null);
                    linkWatcherLinkmsListOldLinkWatcherLinkms = em.merge(linkWatcherLinkmsListOldLinkWatcherLinkms);
                }
            }
            for (LinkWatcherLinkms linkWatcherLinkmsListNewLinkWatcherLinkms : linkWatcherLinkmsListNew) {
                if (!linkWatcherLinkmsListOld.contains(linkWatcherLinkmsListNewLinkWatcherLinkms)) {
                    Watchers oldLWatcherOfLinkWatcherLinkmsListNewLinkWatcherLinkms = linkWatcherLinkmsListNewLinkWatcherLinkms.getLWatcher();
                    linkWatcherLinkmsListNewLinkWatcherLinkms.setLWatcher(watchers);
                    linkWatcherLinkmsListNewLinkWatcherLinkms = em.merge(linkWatcherLinkmsListNewLinkWatcherLinkms);
                    if (oldLWatcherOfLinkWatcherLinkmsListNewLinkWatcherLinkms != null && !oldLWatcherOfLinkWatcherLinkmsListNewLinkWatcherLinkms.equals(watchers)) {
                        oldLWatcherOfLinkWatcherLinkmsListNewLinkWatcherLinkms.getLinkWatcherLinkmsList().remove(linkWatcherLinkmsListNewLinkWatcherLinkms);
                        oldLWatcherOfLinkWatcherLinkmsListNewLinkWatcherLinkms = em.merge(oldLWatcherOfLinkWatcherLinkmsListNewLinkWatcherLinkms);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = watchers.getWId();
                if (findWatchers(id) == null) {
                    throw new NonexistentEntityException("The watchers with id " + id + " no longer exists.");
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
            Watchers watchers;
            try {
                watchers = em.getReference(Watchers.class, id);
                watchers.getWId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The watchers with id " + id + " no longer exists.", enfe);
            }
            List<LinkWatcherLinkms> linkWatcherLinkmsList = watchers.getLinkWatcherLinkmsList();
            for (LinkWatcherLinkms linkWatcherLinkmsListLinkWatcherLinkms : linkWatcherLinkmsList) {
                linkWatcherLinkmsListLinkWatcherLinkms.setLWatcher(null);
                linkWatcherLinkmsListLinkWatcherLinkms = em.merge(linkWatcherLinkmsListLinkWatcherLinkms);
            }
            em.remove(watchers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Watchers> findWatchersEntities() {
        return findWatchersEntities(true, -1, -1);
    }

    public List<Watchers> findWatchersEntities(int maxResults, int firstResult) {
        return findWatchersEntities(false, maxResults, firstResult);
    }

    private List<Watchers> findWatchersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Watchers.class));
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

    public Watchers findWatchers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Watchers.class, id);
        } finally {
            em.close();
        }
    }

    public int getWatchersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Watchers> rt = cq.from(Watchers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
