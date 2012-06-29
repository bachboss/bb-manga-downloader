/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class WatcherTableModel extends AbstractTableModel implements MyTableModelSortable<Watcher> {

    private List<Watcher> listWatcher;
    private static String[] COLUMNS = {"Watcher", "New"};
    private boolean isAsc = true;

    public WatcherTableModel() {
        this.listWatcher = new ArrayList<>();
    }

    public List<Watcher> getListWatcher() {
        return listWatcher;
    }

    public Watcher getWatcherAt(int row) {
        return listWatcher.get(row);
    }

    @Override
    public int getRowCount() {
        return listWatcher.size();
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
    public Object getDisplayDataAtColumn(int column, Watcher w) {
        switch (column) {
            case (0):
                return w.getName() + " (" + w.getMangaCount() + ")";
            case (1):
                WatcherStatus s = w.getWatcherStatus();
                return (s == WatcherStatus.Loaded) ? w.getNewestChapter() : s.toString();
        }
        return null;
    }

    @Override
    public Object getRealDataAtColumn(int column, Watcher w) {
        switch (column) {
            case (0):
                return w.getName();
            case (2):
                return w.getNewestChapter();
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Watcher w = getWatcherAt(rowIndex);
        return getDisplayDataAtColumn(columnIndex, w);
    }

    public void addWatcher(Watcher w) {
        this.listWatcher.add(w);
        this.fireTableDataChanged();
    }

    public void addWatchers(List<Watcher> watchers) {
        this.listWatcher.addAll(watchers);
    }

    public void removeWatcher(Watcher w) {
        this.listWatcher.remove(w);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.listWatcher.clear();
        this.fireTableDataChanged();
    }

    @Override
    public List<Watcher> getData() {
        return listWatcher;
    }

    @Override
    public boolean isSortable(int column) {
        return true;
    }

    @Override
    public boolean getIsAsc() {
        return isAsc;
    }

    @Override
    public boolean swithSortOrder() {
        isAsc = !isAsc;
        return isAsc;
    }
}
