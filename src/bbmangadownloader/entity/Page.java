/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import java.io.Serializable;

/**
 *
 * @author Bach
 */
public class Page extends HtmlDocument implements Serializable {

    private Chapter chapter;
    private boolean isChapterUrl = false;
    private int pageOrder;

    public Page(String url, Chapter chapter) {
        this.url = url;
        this.chapter = chapter;
    }

    public Page(String url, Chapter chapter, int order) {
        this.url = url;
        this.chapter = chapter;
        this.pageOrder = order;
    }

    public Page(String url, Chapter chapter, int order, boolean isChapterUrl) {
        this(url, chapter, order);
        this.isChapterUrl = isChapterUrl;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public boolean isIsChapterUrl() {
        return isChapterUrl;
    }

    public void setIsChapterUrl(boolean isChapterUrl) {
        this.isChapterUrl = isChapterUrl;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }
}
