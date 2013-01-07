/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.ult.Heuristic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public final class Manga extends HtmlDocument implements Serializable {

    private int id = -1;
    private String mangaName;
    private Server server;
    //
    private List<Chapter> lstChapter;
    public boolean isLoaded = false;

//    public Manga(Mangas eManga) {
//        this.id = eManga.getMId();
//        this.url = eManga.getMUrl();
//        this.server = ServerManager.getServerByName(eManga.getMServer().getSName());
//        setMangaName(eManga.getMName());
//    }
//
    public Manga(Server server, String mangaName, String url) {
        this.url = url;
        this.server = server;
        setMangaName(mangaName);
    }

    public Manga(int id, Server server, String mangaName, String url) {
        this(server, mangaName, url);
        this.id = id;
    }

    public void forceLoadChapter() throws Exception {
        IFacadeMangaServer facade = server.getMangaServer();
        lstChapter = facade.getAllChapters(this);
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void loadChapter() {
        if (!isLoaded) {
            synchronized (this) {
                if (!isLoaded) {
                    try {
                        forceLoadChapter();
                        isLoaded = true;
                    } catch (Exception ex) {
                        Logger.getLogger(Manga.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public int getChapterCount() {
        loadChapter();
        return lstChapter.size();
    }

    public Chapter getChapterAt(int index) {
        return lstChapter.get(index);
    }

    public int indexOfChapter(Chapter c) {
        return lstChapter.indexOf(c);
    }

    public List<Chapter> getListChapter() {
        if (lstChapter == null) {
            lstChapter = new ArrayList<Chapter>();
        }
        return lstChapter;
    }

    public void addChapter(Chapter c) {
        if (c != null) {
            getListChapter().add(c);
        }
    }

    public void addChapters(List<Chapter> c) {
        getListChapter().addAll(c);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName.trim();
        this.nameSplited = Heuristic.doSplitHeuristic(mangaName);
//        this.lowerCaseName = mangaName.toLowerCase();
    }

    public int getNumberOfDigitInMangaName() {
        if (d == -1) {
            d = Heuristic.getNumberOfDigitInString(mangaName, null);
        }
        return d;
    }
    /*
     * Return grade in range of 0 to 100 asking text is a long,, long text =)
     */

    public int isWordsAccecptableByGrade(String askingText) {
        int g = 0;
        boolean isContinue = true;
        if (Heuristic.isContainChapter(askingText)) {
            g = 100;
            isContinue = false;
        }
        if (isContinue) {
            List<Integer> lstInt = new ArrayList<Integer>();
            int n = Heuristic.getNumberOfDigitInString(askingText, lstInt) - getNumberOfDigitInMangaName();
            // Maxium is 5 more than digit in link's name
            if (n < 0 && n > 5) {
                isContinue = false;
            }
        }

        if (isContinue) {
            String[] textSplited = Heuristic.doSplitHeuristic(askingText);
            // Maxium is 10 words
            if (textSplited.length - nameSplited.length > 10) {
                isContinue = false;
            }
            if (isContinue) {
                int c = 0;
                int j = 0;

                for (int i = 0; i < nameSplited.length; i++) {
                    while (!(Heuristic.isTwoWordAccecptable(nameSplited[i], textSplited[j]) >= Heuristic.DEFAULT_MIN_ACCECPT)) {
                        j++;
                        if (j >= textSplited.length) {
                            break;
                        }
                    }
                    if (j >= textSplited.length) {
                        break;
                    } else {
                        c++;
                    }


                }
                g = Heuristic.getRatio(c, nameSplited.length, 100);
            }
        }
//        System.out.println("\t" + g + "\tCompare : " + askingText + "\t" + lowerCaseName + "\t");
        return g;
    }

    public boolean isWordsAccecptable(String parsedString) {
        int grade = isWordsAccecptableByGrade(parsedString.toLowerCase());
        return (grade >= Heuristic.DEFAULT_MIN_ACCECPT);
    }
    private int d = -1;
    private String[] nameSplited;
//    private String lowerCaseName;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.getUrl() != null ? this.getUrl().hashCode() : 0);
        hash = 53 * hash + (this.mangaName != null ? this.mangaName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Manga)) {
            return false;
        }
        final Manga other = (Manga) obj;
        return other.hashCode() == this.hashCode();
    }

    public void doSortChapters(boolean isAsc) {
        try {
            Collections.sort(lstChapter, new ComparatorImpl(isAsc));
        } catch (java.lang.IllegalArgumentException ex) {
            System.err.println("Caught Exception: " + ex.getMessage());
        }
    }

    private static class ComparatorImpl implements Comparator<Chapter> {

        private boolean isAsc;

        public ComparatorImpl(boolean isAsc) {
            this.isAsc = isAsc;
        }

        @Override
        public int compare(Chapter o1, Chapter o2) {
            int b = (o1.compareTo(o2));
            return isAsc ? b : (-b);
        }
    }

    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<id>");
        sb.append(getHashId());
        sb.append("</id>");
        sb.append("<name>");
        sb.append(mangaName);
        sb.append("</name>");
        sb.append("<server>");
        sb.append(server.getServerName());
        sb.append("</server>");
        sb.append("<url>");
        sb.append(url);
        sb.append("</url>");
        return sb.toString();
    }

    public String getObjectFromXml(String text) {
        return null;
    }

    public int getHashId() {
        if (id == -1) {
            synchronized (this) {
                if (id == -1) {
                    id = this.mangaName.hashCode() + this.server.getIdHashCode();
                }
            }
        }
//        System.out.println("Get Hash ID: " + mangaName + "\t" + id);
        return id;
    }

    @Override
    public String toString() {
//        return "Manga [" + hashCode() + "]";
        return mangaName;
    }
}