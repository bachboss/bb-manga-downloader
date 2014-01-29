/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.ult.GUIUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author Bach
 */
public class WatcherMangaTreeTableModel extends AbstractTreeTableModel {

    private static final String LOADING = "Loading";
    // 
    private static final WatcherMangaTreeTableModel EMPTY = new WatcherMangaTreeTableModel();

    private WatcherMangaTreeTableModel() {
    }

    public static TreeTableModel empty() {
        return EMPTY;
    }
//    private MyFilter lastFilter;
    private JXTreeTable treeTable;
    //
    private List<MangaWrapper> listMangaWrapper;
    private static String[] COLUMNS = {"Display Name", "Chapter", "Upload Date", "Uploader", "URL"};
//    private boolean isAsc = true;

    public WatcherMangaTreeTableModel(JXTreeTable t) {
        TreeNode r = new TreeNode();
        super.root = r;
        listMangaWrapper = new ArrayList<MangaWrapper>();
        this.treeTable = t;
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
                    return m.manga.getServer().getServerName() + " - " + m.manga.getMangaName();
                case 4:
                    return m.manga.getURL();
                default:
                    return "--";
            }
        } else if (o instanceof Chapter) {
            Chapter c = (Chapter) o;
            switch (i) {
                case -1:
                    return c;
                case 0:
                    return c.getDisplayName();
                case 1:
                    return GUIUtilities.getStringFromFloat(c.getChapterNumber());
                case 2:
                    return c.getUploadDate();
                case 3:
                    return c.getTranslator();
                case 4:
                    return c.getUrl();
            }
        } else if (o instanceof String) {
            return LOADING;
        }
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof TreeNode) {
            return listMangaWrapper.get(index);
        } else if (parent instanceof MangaWrapper) {
            MangaWrapper m = (MangaWrapper) parent;
            if (m.isLoaded()) {
                return m.getChapterAt(index);
            } else {
                return LOADING;
            }
        } else {
            return null;
        }
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof TreeNode) {
            return listMangaWrapper.size();
        } else if (parent instanceof MangaWrapper) {
            final MangaWrapper mW = (MangaWrapper) parent;
            if (mW.isLoaded()) {
                return mW.getChapterCount();
            } else {
                // lazy load here !
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        mW.manga.loadChapter();
                        mW.reload();
//                        treeTable.setTreeTableModel(new DefaultTreeTableModel());
                        TreeTableModel t = WatcherMangaTreeTableModel.this;
                        treeTable.setTreeTableModel(t);
                    }
                });
                return 1;
            }
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
        return (node instanceof Chapter || node instanceof String);
    }

    public void initFromWatcher(Watcher w) {
        listMangaWrapper.clear();
        List<Manga> tempLst = w.getLstManga();
        for (Manga m : tempLst) {
            listMangaWrapper.add(new MangaWrapper(m));
        }
//        valueForPathChanged(new TreePath(root), new TreeNode());
    }

    public void applyFilter(int fromChapter, int toChapter) {
        MyFilter f = new MyFilter(fromChapter, toChapter);
        for (MangaWrapper mW : listMangaWrapper) {
            mW.applyFilter(f);
        }
    }

    private static class MangaWrapper {

        private Manga manga;
        private List<Chapter> lstChapter;

        public MangaWrapper(Manga manga) {
            this.manga = manga;
            lstChapter = new ArrayList<Chapter>(manga.getListChapter());
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
            lstChapter = new ArrayList<Chapter>(manga.getListChapter());
        }

        public boolean isLoaded() {
            return manga.isLoaded;
        }
    }

    private static class MyFilter {

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

    private static class TreeNode {
    }
//    /**
//     * Notifies all listeners that have registered interest for notification on
//     * this event type. The event instance is lazily created using the
//     * parameters passed into the fire method.
//     *
//     * @param source the node being changed
//     * @param path the path to the root node
//     * @param childIndices the indices of the changed elements
//     * @param children the changed elements
//     * @see EventListenerList
//     */
//    protected void fireTreeNodesChanged(Object source, Object[] path,
//            int[] childIndices,
//            Object[] children) {
//        // Guaranteed to return a non-null array
//        TreeModelListener[] listeners = getTreeModelListeners();
//        TreeModelEvent e = null;
//        // Process the listeners last to first, notifying
//        // those that are interested in this event
//        for (int i = listeners.length - 2; i >= 0; i -= 2) {
//            // Lazily create the event:
//            if (e == null) {
//                e = new TreeModelEvent(source, path, childIndices, children);
//            }
//            ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
//        }
//    }
}
