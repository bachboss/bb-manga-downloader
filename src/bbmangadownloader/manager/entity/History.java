/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bach
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class History {

    private String mangaName;
    private String mangaUrl;
    private String chapterName;
    private String chapterUrl;
    private Date finishTime;

    public History() {
    }

    public History(String mangaName, String mangaUrl, String chapterName, String chapterUrl) {
        this.mangaName = mangaName;
        this.mangaUrl = mangaUrl;
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
        this.finishTime = new Date();
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public String getMangaUrl() {
        return mangaUrl;
    }

    public void setMangaUrl(String mangaUrl) {
        this.mangaUrl = mangaUrl;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

}
