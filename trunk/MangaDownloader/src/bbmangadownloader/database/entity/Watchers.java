/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.database.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bach
 */
@Entity
@Table(name = "WATCHERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Watchers.findAll", query = "SELECT w FROM Watchers w"),
    @NamedQuery(name = "Watchers.findByWId", query = "SELECT w FROM Watchers w WHERE w.wId = :wId"),
    @NamedQuery(name = "Watchers.findByWName", query = "SELECT w FROM Watchers w WHERE w.wName = :wName")})
public class Watchers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "W_ID")
    private Integer wId;
    @Column(name = "W_NAME")
    private String wName;
    @OneToMany(mappedBy = "lWatcher")
    private List<LinkWatcherLinkms> linkWatcherLinkmsList;

    public Watchers() {
    }

    public Watchers(Integer wId) {
        this.wId = wId;
    }

    public Integer getWId() {
        return wId;
    }

    public void setWId(Integer wId) {
        this.wId = wId;
    }

    public String getWName() {
        return wName;
    }

    public void setWName(String wName) {
        this.wName = wName;
    }

    @XmlTransient
    public List<LinkWatcherLinkms> getLinkWatcherLinkmsList() {
        return linkWatcherLinkmsList;
    }

    public void setLinkWatcherLinkmsList(List<LinkWatcherLinkms> linkWatcherLinkmsList) {
        this.linkWatcherLinkmsList = linkWatcherLinkmsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wId != null ? wId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Watchers)) {
            return false;
        }
        Watchers other = (Watchers) object;
        if ((this.wId == null && other.wId != null) || (this.wId != null && !this.wId.equals(other.wId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mangadownloader.database.entity.Watchers[ wId=" + wId + " ]";
    }
    
}
