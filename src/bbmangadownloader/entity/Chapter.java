/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.Heuristic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bach
 */
public class Chapter extends HtmlDocument implements Comparable<Chapter>, Serializable {

    private Manga manga;
    private float chapterNumber;
    private String displayName;
    private MangaDateTime uploadDate;
    private List<Image> listImage;
    private List<Page> listPage;
    private String translator;

    @Deprecated
    public Chapter(float chapterNumber, String displayName, String url, Manga manga) {
        this.chapterNumber = chapterNumber;
        this.displayName = displayName.trim();
        this.url = url;
        this.manga = manga;
        if (chapterNumber < 0) {
            this.chapterNumber = Heuristic.tryGetChapterNumber(this);
        }
    }

    /**
     *
     * @param chapterNumber Chapter Number, input < 0 value for autro detect.
     * @param displayName
     * @param url
     * @param manga
     * @param translator
     * @param uploadDate
     */
    public Chapter(float chapterNumber, String displayName, String url, Manga manga, String translator, MangaDateTime uploadDate) {
        this(chapterNumber, displayName, url, manga);
        this.translator = translator;
        this.uploadDate = uploadDate;
        if (chapterNumber < 0) {
            this.chapterNumber = Heuristic.tryGetChapterNumber(this);
        }
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public float getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public MangaDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(MangaDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return this.getChapterNumber() + "\t" + this.displayName;
    }

    @Override
    public int compareTo(Chapter o) {
        if (o.getChapterNumber() > this.getChapterNumber()) {
            return 1;
        } else {
            return -1;
        }
    }

    public synchronized List<Image> getListImage() {
        if (this.listImage == null) {
            synchronized (this) {
                if (this.listImage == null) {
                    listImage = new ArrayList<Image>();
                }
            }
        }
        return listImage;
    }

    public void addImage(Image img) {
        if (this.listImage == null) {
            this.listImage = new ArrayList<Image>();
        }
        listImage.add(img);
    }

    public int getImagesCount() {
        return this.listImage == null ? -1 : this.listImage.size();
    }

    public synchronized List<Page> getListPage() {
        if (this.listPage == null) {
            this.listPage = new ArrayList<Page>();
        }
        return listPage;
    }

    public void addPage(Page img) {
        if (this.listPage == null) {
            this.listPage = new ArrayList<Page>();
        }
        listPage.add(img);
    }

    public int getPagesCount() {
        return this.listPage == null ? -1 : this.listPage.size();
    }

    public void addImages(List<Image> lstImage) {
        List<Image> temp = getListImage();
        temp.addAll(lstImage);
    }

    public void addPages(List<Page> lstPage) {
        List<Page> temp = getListPage();
        temp.addAll(lstPage);
    }

    public void clearImages() {
        getListImage().clear();
    }

    public void clearPages() {
        getListPage().clear();
    }
}
