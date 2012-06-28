/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.entity;

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
}
