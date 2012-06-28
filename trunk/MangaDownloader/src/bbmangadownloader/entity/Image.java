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
public class Image extends HtmlDocument implements Serializable {

    private Chapter chapter;
    private int imgOrder;

    public Image(int imgOrder, String url, Chapter chapter) {
        this.imgOrder = imgOrder;
        this.chapter = chapter;
        this.url = url;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(int imgOrder) {
        this.imgOrder = imgOrder;
    }

    @Override
    public String toString() {
        return this.getUrl();
    }
}
