/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.database.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bach
 */
@Entity
@Table(name = "LINK_MANGA_SERVER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinkMangaServer.findAll", query = "SELECT l FROM LinkMangaServer l"),
    @NamedQuery(name = "LinkMangaServer.findByLMsId", query = "SELECT l FROM LinkMangaServer l WHERE l.lMsId = :lMsId"),
    @NamedQuery(name = "LinkMangaServer.findByLMsUrl", query = "SELECT l FROM LinkMangaServer l WHERE l.lMsUrl = :lMsUrl"),
    @NamedQuery(name = "LinkMangaServer.findByLMsLastupdate", query = "SELECT l FROM LinkMangaServer l WHERE l.lMsLastupdate = :lMsLastupdate")})
public class LinkMangaServer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "L_MS_ID")
    private Integer lMsId;
    @Column(name = "L_MS_URL")
    private String lMsUrl;
    @Column(name = "L_MS_LASTUPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lMsLastupdate;
    @JoinColumn(name = "L_MS_SERVER", referencedColumnName = "S_ID")
    @ManyToOne
    private Servers lMsServer;
    @JoinColumn(name = "L_MS_MANGA", referencedColumnName = "M_ID")
    @ManyToOne
    private Mangas lMsManga;
    @OneToMany(mappedBy = "lWlLinms")
    private List<LinkWatcherLinkms> linkWatcherLinkmsList;

    public LinkMangaServer() {
    }

    public LinkMangaServer(Integer lMsId) {
        this.lMsId = lMsId;
    }

    public Integer getLMsId() {
        return lMsId;
    }

    public void setLMsId(Integer lMsId) {
        this.lMsId = lMsId;
    }

    public String getLMsUrl() {
        return lMsUrl;
    }

    public void setLMsUrl(String lMsUrl) {
        this.lMsUrl = lMsUrl;
    }

    public Date getLMsLastupdate() {
        return lMsLastupdate;
    }

    public void setLMsLastupdate(Date lMsLastupdate) {
        this.lMsLastupdate = lMsLastupdate;
    }

    public Servers getLMsServer() {
        return lMsServer;
    }

    public void setLMsServer(Servers lMsServer) {
        this.lMsServer = lMsServer;
    }

    public Mangas getLMsManga() {
        return lMsManga;
    }

    public void setLMsManga(Mangas lMsManga) {
        this.lMsManga = lMsManga;
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
        hash += (lMsId != null ? lMsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkMangaServer)) {
            return false;
        }
        LinkMangaServer other = (LinkMangaServer) object;
        if ((this.lMsId == null && other.lMsId != null) || (this.lMsId != null && !this.lMsId.equals(other.lMsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bbmangadownloader.database.entity.LinkMangaServer[ lMsId=" + lMsId + " ]";
    }
    
}
