/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.manager.BookmarkManager;
import bbmangadownloader.manager.entity.Bookmark;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class BookmarkTableModel extends AbstractTableModel {

    private final List<Bookmark> listBookmark;

    public BookmarkTableModel() {
        listBookmark = BookmarkManager.getBookMarks();
    }

    public boolean addBookmark(Bookmark m) {
        return (BookmarkManager.addBookMark(m));
    }

    public void removeBookmark(Bookmark m) {
        BookmarkManager.removeBookmark(m);
    }

    private static final String[] COLUMNS = new String[]{
        "Name", "Manga", "URLs"
    };

    private static final Class[] CLASSES = new Class[]{
        String.class, String.class, String.class
    };

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CLASSES[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public int getRowCount() {
        return listBookmark.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    private Bookmark getBookMarkAt(int index) {
        return listBookmark.get(index);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bookmark m = getBookMarkAt(rowIndex);
        switch (columnIndex) {
            case -1:
                return m;
            case 0:
                return m.getBookmarkName();
            case 1:
                return m.getMangaName();
            case 2:
                return m.getUrl();
        }
        return "not support";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Bookmark m = getBookMarkAt(rowIndex);
        switch (columnIndex) {
            case 0: {
                String newValue = (String) aValue;
                String oldValue = m.getBookmarkName();
                if (!newValue.equals(oldValue)) {
                    m.setBookmarkName(newValue);
                    BookmarkManager.markUpdate();
                }
                break;
            }
            case 1: {
                String newValue = (String) aValue;
                String oldValue = m.getMangaName();
                if (!newValue.equals(oldValue)) {
                    m.setMangaName(newValue);
                    BookmarkManager.markUpdate();
                }
                break;
            }
            case 2: {
                String newValue = (String) aValue;
                String oldValue = m.getUrl();
                if (!newValue.equals(oldValue)) {
                    m.setUrl(newValue);
                    BookmarkManager.markUpdate();
                }
                break;
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable;
    }

    private boolean editable;

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
