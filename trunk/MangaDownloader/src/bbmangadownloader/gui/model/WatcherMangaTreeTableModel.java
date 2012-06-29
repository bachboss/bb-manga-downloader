/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.tree.TreePath;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.ult.DateTimeUtilities;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

/**
 *
 * @author Bach
 */
public class WatcherMangaTreeTableModel extends AbstractTreeTableModel {

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd";
    //
    private List<MangaWrapper> listMangaWrapper;
    private static String[] COLUMNS = {"Display Name", "Chapter", "Upload Date", "Uploader", "URL"};
//    private boolean isAsc = true;

    public WatcherMangaTreeTableModel() {
        TreeNode r = new TreeNode();
        super.root = r;
        listMangaWrapper = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(Object o, int i) {
        if (o instanceof MangaWrapper) {
            MangaWrapper m = (MangaWrapper) o;
            switch (i) {
                case -1:
                    return m.manga;
                case 0:
                    return m.manga.getServer().getServerName();
                case 4:
                    return m.manga.getURL();
                default:
                    return "---";
            }
        } else if (o instanceof Chapter) {
            Chapter c = (Chapter) o;
            switch (i) {
                case -1:
                    return c;
                case 0:
                    return c.getDisplayName();
                case 1:
                    return c.getChapterNumber();
                case 2:
                    Date d = c.getUploadDate();
                    return (d == null ? null : DateTimeUtilities.getStringFromDate(d, DEFAULT_DATETIME_FORMAT));
                case 3:
                    return c.getTranslator();
                case 4:
                    return c.getUrl();
            }
        }
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof TreeNode) {
            return listMangaWrapper.get(index);
        } else if (parent instanceof MangaWrapper) {
            MangaWrapper m = (MangaWrapper) parent;
            return m.getChapterAt(index);
        } else {
            return null;
        }
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof TreeNode) {
            return listMangaWrapper.size();
        } else if (parent instanceof MangaWrapper) {
            MangaWrapper mW = (MangaWrapper) parent;
            return mW.getChapterCount();
        } else {
            return 0;
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof MangaWrapper) {
            return ((MangaWrapper) parent).indexOfChapter((Chapter) child);
        } else if (parent instanceof TreeNode) {
            return listMangaWrapper.indexOf((MangaWrapper) child);
        } else {
            return 0;
        }
    }

    @Override
    public boolean isLeaf(Object node) {
        return (node instanceof Chapter);
    }

    public void initFromWatcher(Watcher w) {
        listMangaWrapper.clear();
        List<Manga> tempLst = w.getLstManga();
        for (Manga m : tempLst) {
            listMangaWrapper.add(new MangaWrapper(m));
        }
        valueForPathChanged(new TreePath(root), new TreeNode());
    }

    public void applyFilter(int fromChapter, int toChapter) {
        MyFilter f = new MyFilter(fromChapter, toChapter);
        for (MangaWrapper mW : listMangaWrapper) {
            mW.applyFilter(f);
        }
    }

    private class MangaWrapper {

        private Manga manga;
        private List<Chapter> lstChapter;

        public MangaWrapper(Manga manga) {
            this.manga = manga;
            lstChapter = new ArrayList<>(manga.getListChapter());
        }

        public void applyFilter(MyFilter f) {
            lstChapter.clear();
            for (Chapter c : manga.getListChapter()) {
                if (f.isFit(c)) {
                    lstChapter.add(c);
//                    System.out.println("Fit: " + c.getChapterNumber());
                }
            }
        }

        private int getChapterCount() {
            if (!manga.isLoaded) {
                manga.loadChapter();
                reload();
            }
            return lstChapter.size();

        }

        private int indexOfChapter(Chapter chapter) {
            return lstChapter.indexOf(chapter);
        }

        private Object getChapterAt(int index) {
            return lstChapter.get(index);
        }

        private void reload() {
            lstChapter = new ArrayList<>(manga.getListChapter());
        }
    }

    private class MyFilter {

        private int fromChapter;
        private int toChapter;

        public MyFilter(int fromChapter, int toChapter) {
            this.fromChapter = fromChapter;
            this.toChapter = toChapter;
        }

        public boolean isFit(Chapter c) {
            float chapter = c.getChapterNumber();
            return ((chapter >= fromChapter) && (chapter <= toChapter));
        }
    }

    private class TreeNode {
    }
}
