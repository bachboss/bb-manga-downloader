/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import java.io.Serializable;
import java.net.URL;

/**
 *
 * @author Bach
 */
public abstract class HtmlDocument implements Serializable {

    protected String url;
    protected URL u;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public URL getURL() {
        try {
            this.u = new URL(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return u;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.url != null ? this.url.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HtmlDocument other = (HtmlDocument) obj;
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }
}
