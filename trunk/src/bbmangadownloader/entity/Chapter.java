/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.Heuristic;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bach
 */
public class Chapter extends HtmlDocument implements Comparable<Chapter>, Serializable {

    private Manga manga;
    private float chapterNumber;
    private String displayName;
    private MangaDateTime uploadDate;
    private Set<Image> setImage;
    private Set<Page> setPage;
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

    public synchronized Set<Image> getSetImage() {
        if (this.setImage == null) {
            synchronized (this) {
                if (this.setImage == null) {
                    setImage = new HashSet<Image>();
                }
            }
        }
        return setImage;
    }

    public void addImage(Image img) {
        getSetImage().add(img);
    }

    public int getImagesCount() {
        return this.setImage == null ? -1 : this.setImage.size();
    }

    public synchronized Set<Page> getSetPage() {
        if (this.setPage == null) {
            this.setPage = new HashSet<Page>();
        }
        return setPage;
    }

    public void addPage(Page page) {
        getSetPage().add(page);
    }

    public int getPagesCount() {
        return this.setPage == null ? -1 : this.setPage.size();
    }

    /**
     *
     * Add images to chapter. All duplicate image (define by hashset) will ve
     * remove.
     *
     * @param images
     */
    public void addImages(Collection<Image> images) {
        getSetImage().addAll(images);
    }

    public void addPages(Collection<Page> pages) {
        getSetPage().addAll(pages);
    }

    public void clearImages() {
        getSetImage().clear();
    }

    public void clearPages() {
        getSetPage().clear();
    }
}
