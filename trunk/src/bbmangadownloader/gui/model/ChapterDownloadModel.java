/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.ult.GUIUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class ChapterDownloadModel extends AbstractTableModel implements MyTableModelSortable<DownloadTask> {

    private static final String[] COLUMNS = {"Chapter", "Display Name", "Status", "URL"};
    private List<DownloadTask> listDownload;
    private boolean isAsc = true;

    public ChapterDownloadModel() {
        // remove the creat-code later
        this.listDownload = new ArrayList<DownloadTask>();
    }

    public List<DownloadTask> getListDownload() {
        return listDownload;
    }

    public DownloadTask getTaskAt(int row) {
        return listDownload.get(row);
    }

    @Override
    public int getRowCount() {
        return listDownload.size();
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
    public Object getDisplayDataAtColumn(int column, DownloadTask task) {
        switch (column) {
            case (0):
                return GUIUtilities.getStringFromFloat(task.getChapter().getChapterNumber());
            case (1):
                return task.getChapter().getDisplayName();
            case (2):
                return task.getStatus();
            case (3):
                return task.getChapter().getUrl();
        }
        return null;
    }

    @Override
    public Object getRealDataAtColumn(int column, DownloadTask task) {
        switch (column) {
            case (0):
                return task.getChapter().getChapterNumber();
            case (1):
                return task.getChapter().getDisplayName();
            case (2):
                return task.getStatus();
            case (3):
                return task.getChapter().getUrl();
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DownloadTask task = getTaskAt(rowIndex);
        return this.getDisplayDataAtColumn(columnIndex, task);
    }

    @Override
    public boolean isSortable(int column) {
        return true;
    }

    public DownloadTask addChapter(Chapter c) {
        for (DownloadTask t : listDownload) {
            if (t.getChapter() == c) {
                return t;
            }
        }
        DownloadTask t = new DownloadTask(c);
        this.addTask(t);
        return t;
    }

    public void addTask(DownloadTask t) {
        this.listDownload.add(t);
        this.fireTableRowsInserted(listDownload.size(), listDownload.size());
//        this.fireTableDataChanged();
    }

    public void removeTask(DownloadTask t) {
        int index = this.listDownload.indexOf(t);
        removeTaskAt(index);
    }

    public void removeTaskAt(int index) {
        this.listDownload.remove(index);
        this.fireTableRowsDeleted(index, index);
//        this.fireTableDataChanged();
    }

    public void clear() {
        this.listDownload.clear();
        this.fireTableDataChanged();
    }

    public void fireDownloadTaskStatusUpdated(DownloadTask task) {
        fireTableCellUpdated(listDownload.indexOf(task), 2);
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

    @Override
    public List getData() {
        return listDownload;
    }
}
