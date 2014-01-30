/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.manager.HistoryManager;
import bbmangadownloader.manager.entity.History;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Bach
 */
public class HistoryTableModel extends AbstractTableModel {

    private final List<History> listHistory;

    public HistoryTableModel() {
        listHistory = HistoryManager.getHistories();
    }

    public boolean addHistory(History m) {
        return (HistoryManager.addHistory(m));
    }

    public void removeHistory(History m) {
        HistoryManager.removeHistory(m);
    }

    private static final String[] COLUMNS = new String[]{
        "Chapter Name", "Chapter Url", "Manga Name", "Manga Url"
    };

    private static final Class[] CLASSES = new Class[]{
        String.class, String.class, String.class, String.class
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
        return listHistory.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    private History getHistoryAt(int index) {
        return listHistory.get(index);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        History m = getHistoryAt(rowIndex);
        switch (columnIndex) {
            case -1:
                return m;
            case 0:
                return m.getChapterName();
            case 1:
                return m.getChapterUrl();
            case 2:
                return m.getMangaName();
            case 3:
                return m.getMangaUrl();
        }
        return "not support";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        History m = getHistoryAt(rowIndex);
        switch (columnIndex) {
            case 0: {
                String newValue = (String) aValue;
                String oldValue = m.getChapterName();
                if (!newValue.equals(oldValue)) {
                    m.setChapterName(newValue);
                    HistoryManager.markUpdate();
                }
                break;
            }
            case 1: {
                String newValue = (String) aValue;
                String oldValue = m.getChapterUrl();
                if (!newValue.equals(oldValue)) {
                    m.setChapterUrl(newValue);
                    HistoryManager.markUpdate();
                }
                break;
            }
            case 2: {
                String newValue = (String) aValue;
                String oldValue = m.getMangaName();
                if (!newValue.equals(oldValue)) {
                    m.setMangaName(newValue);
                    HistoryManager.markUpdate();
                }
                break;
            }
            case 3: {
                String newValue = (String) aValue;
                String oldValue = m.getMangaUrl();
                if (!newValue.equals(oldValue)) {
                    m.setMangaUrl(newValue);
                    HistoryManager.markUpdate();
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
