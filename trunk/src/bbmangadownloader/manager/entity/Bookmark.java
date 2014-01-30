/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bach
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Bookmark {

    private String bookmarkName;
    private String mangaName;
    private String url;

    public Bookmark() {
    }

    public Bookmark(String bookmarkName, String mangaName, String url) {
        this.bookmarkName = bookmarkName;
        this.mangaName = mangaName;
        this.url = url;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public String getMangaName() {
        return mangaName;
    }

    public String getUrl() {
        return url;
    }

}
