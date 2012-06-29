/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bach
 */
@Entity
@Table(name = "LINK_WATCHER_LINKMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinkWatcherLinkms.findAll", query = "SELECT l FROM LinkWatcherLinkms l"),
    @NamedQuery(name = "LinkWatcherLinkms.findByLWlId", query = "SELECT l FROM LinkWatcherLinkms l WHERE l.lWlId = :lWlId")})
public class LinkWatcherLinkms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "L_WL_ID")
    private Integer lWlId;
    @JoinColumn(name = "L_WATCHER", referencedColumnName = "W_ID")
    @ManyToOne
    private Watchers lWatcher;
    @JoinColumn(name = "L_WL_LINMS", referencedColumnName = "L_MS_ID")
    @ManyToOne
    private LinkMangaServer lWlLinms;

    public LinkWatcherLinkms() {
    }

    public LinkWatcherLinkms(Integer lWlId) {
        this.lWlId = lWlId;
    }

    public Integer getLWlId() {
        return lWlId;
    }

    public void setLWlId(Integer lWlId) {
        this.lWlId = lWlId;
    }

    public Watchers getLWatcher() {
        return lWatcher;
    }

    public void setLWatcher(Watchers lWatcher) {
        this.lWatcher = lWatcher;
    }

    public LinkMangaServer getLWlLinms() {
        return lWlLinms;
    }

    public void setLWlLinms(LinkMangaServer lWlLinms) {
        this.lWlLinms = lWlLinms;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lWlId != null ? lWlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkWatcherLinkms)) {
            return false;
        }
        LinkWatcherLinkms other = (LinkWatcherLinkms) object;
        if ((this.lWlId == null && other.lWlId != null) || (this.lWlId != null && !this.lWlId.equals(other.lWlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bbmangadownloader.database.entity.LinkWatcherLinkms[ lWlId=" + lWlId + " ]";
    }
    
}
