/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import bbmangadownloader.database.entity.LinkMangaServer;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.ult.Heuristic;

/**
 *
 * @author Bach
 */
public class Manga extends HtmlDocument implements Serializable {

    private int id;
    private String mangaName;
    private Server server;
    private List<Chapter> lstChapter;
    public boolean isLoaded = false;

    public Manga(LinkMangaServer m) {
        this.id = m.getLMsId();
        this.url = m.getLMsUrl();
        this.server = ServerManager.getServerByName(m.getLMsServer().getSName());
        setMangaName(m.getLMsManga().getMName());
    }

    public Manga(Server server, String mangaName, String url) {
        this.url = url;
        this.server = server;
        setMangaName(mangaName);
    }

    private void lazyLoadChapter() throws Exception {
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
                        lazyLoadChapter();
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
            lstChapter = new ArrayList<>();
        }
        return lstChapter;
    }

    public void addChapter(Chapter c) {
        getListChapter().add(c);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName.trim();
        this.nameSplited = Heuristic.doSplitHeuristic(mangaName);
        this.lowerCaseName = mangaName.toLowerCase();
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
            List<Integer> lstInt = new ArrayList<>();
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
    private String lowerCaseName;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.getUrl());
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
}
