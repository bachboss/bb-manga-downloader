/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.ult.GUIUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class ChapterModel extends AbstractTableModel implements MyTableModelSortable<Chapter> {

    // remove the creat-code later
    private List<Chapter> listChapter;
    private static String[] COLUMNS = {"Chapter", "Display Name", "Upload Date", "Uploader", "URL"};
    private boolean isAsc = true;

    public ChapterModel() {
        this.listChapter = new ArrayList<Chapter>();
    }

    public List<Chapter> getListChapter() {
        return listChapter;
    }

    public Chapter getChapterAt(int row) {
        return listChapter.get(row);
    }

    @Override
    public int getRowCount() {
        return listChapter.size();
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
    public Object getDisplayDataAtColumn(int column, Chapter chapter) {
        switch (column) {
            case (0):
                return GUIUtilities.getStringFromFloat(chapter.getChapterNumber());
            case (1):
                return chapter.getDisplayName();
            case (2):
                return chapter.getUploadDate();
            case (3):
                return chapter.getTranslator();
            case (4):
                return chapter.getUrl();
        }
        return null;
    }

    @Override
    public Object getRealDataAtColumn(int column, Chapter chapter) {
        switch (column) {
            case (0):
                return chapter.getChapterNumber();
            case (1):
                return chapter.getDisplayName();
            case (2):
                return chapter.getUploadDate();
            case (3):
                return chapter.getTranslator();
            case (4):
                return chapter.getUrl();
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Chapter chapter = getChapterAt(rowIndex);
        return getDisplayDataAtColumn(columnIndex, chapter);

    }

    public void addChapter(Chapter c) {
        this.listChapter.add(c);
        this.fireTableDataChanged();
    }

    public void addChapters(List<Chapter> lstChapter) {
        this.listChapter.addAll(lstChapter);
    }

    public void removeChapter(Chapter c) {
        this.listChapter.remove(c);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.listChapter.clear();
        this.fireTableDataChanged();
    }

    @Override
    public List getData() {
        return listChapter;
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
