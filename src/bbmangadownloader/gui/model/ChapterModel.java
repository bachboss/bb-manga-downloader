/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.data.MangaDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class ChapterModel extends AbstractTableModel {

    // remove the creat-code later
    private List<Chapter> listChapter;
    private static String[] COLUMNS = {"Chapter", "Display Name", "Upload Date", "Uploader", "URL"};
    private static Class[] CLASSES = {Float.class, String.class, MangaDateTime.class, String.class, String.class};

    public ChapterModel() {
        this.listChapter = new ArrayList<Chapter>();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CLASSES[columnIndex];
    }

    public List<Chapter> getListChapter() {
        return listChapter;
    }

    private Chapter getChapterAt(int row) {
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Chapter chapter = getChapterAt(rowIndex);
        switch (columnIndex) {
            case -1:
                return chapter;
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
}
