/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database.entity;

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
@Table(name = "MANGAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mangas.findAll", query = "SELECT m FROM Mangas m"),
    @NamedQuery(name = "Mangas.findByMId", query = "SELECT m FROM Mangas m WHERE m.mId = :mId"),
    @NamedQuery(name = "Mangas.findByMName", query = "SELECT m FROM Mangas m WHERE m.mName = :mName")})
public class Mangas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "M_ID")
    private Integer mId;
    @Column(name = "M_NAME")
    private String mName;
    @OneToMany(mappedBy = "lMsManga")
    private List<LinkMangaServer> linkMangaServerList;

    public Mangas() {
    }

    public Mangas(Integer mId) {
        this.mId = mId;
    }

    public Integer getMId() {
        return mId;
    }

    public void setMId(Integer mId) {
        this.mId = mId;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    @XmlTransient
    public List<LinkMangaServer> getLinkMangaServerList() {
        return linkMangaServerList;
    }

    public void setLinkMangaServerList(List<LinkMangaServer> linkMangaServerList) {
        this.linkMangaServerList = linkMangaServerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mId != null ? mId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mangas)) {
            return false;
        }
        Mangas other = (Mangas) object;
        if ((this.mId == null && other.mId != null) || (this.mId != null && !this.mId.equals(other.mId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bbmangadownloader.database.entity.Mangas[ mId=" + mId + " ]";
    }
    
}
