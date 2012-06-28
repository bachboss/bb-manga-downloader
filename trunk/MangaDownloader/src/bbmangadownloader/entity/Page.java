/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.entity;

import java.io.Serializable;

/**
 *
 * @author Bach
 */
public class Page extends HtmlDocument implements Serializable {

    private Chapter chapter;

    public Page(String url, Chapter chapter) {
        this.url = url;
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
