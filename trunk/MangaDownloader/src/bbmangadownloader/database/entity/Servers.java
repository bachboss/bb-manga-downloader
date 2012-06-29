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
@Table(name = "SERVERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servers.findAll", query = "SELECT s FROM Servers s"),
    @NamedQuery(name = "Servers.findBySId", query = "SELECT s FROM Servers s WHERE s.sId = :sId"),
    @NamedQuery(name = "Servers.findBySName", query = "SELECT s FROM Servers s WHERE s.sName = :sName"),
    @NamedQuery(name = "Servers.findBySUrl", query = "SELECT s FROM Servers s WHERE s.sUrl = :sUrl")})
public class Servers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "S_ID")
    private Integer sId;
    @Column(name = "S_NAME")
    private String sName;
    @Column(name = "S_URL")
    private String sUrl;
    @OneToMany(mappedBy = "lMsServer")
    private List<LinkMangaServer> linkMangaServerList;

    public Servers() {
    }

    public Servers(Integer sId) {
        this.sId = sId;
    }

    public Integer getSId() {
        return sId;
    }

    public void setSId(Integer sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getSUrl() {
        return sUrl;
    }

    public void setSUrl(String sUrl) {
        this.sUrl = sUrl;
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
        hash += (sId != null ? sId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servers)) {
            return false;
        }
        Servers other = (Servers) object;
        if ((this.sId == null && other.sId != null) || (this.sId != null && !this.sId.equals(other.sId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bbmangadownloader.database.entity.Servers[ sId=" + sId + " ]";
    }
    
}
