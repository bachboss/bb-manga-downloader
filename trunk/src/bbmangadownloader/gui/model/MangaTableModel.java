/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import bbmangadownloader.entity.Manga;

/**
 *
 * @author Bach
 */
public class MangaTableModel extends AbstractTableModel {

    private static final String[] COLUMNS = new String[]{
        "Name", "Host", "Url", "ID"
    };
    //
    private List<Manga> listManga;
    //

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    public MangaTableModel(List<Manga> lst) {
        this.listManga = lst;
    }

    @Override
    public int getRowCount() {
        return listManga.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Manga m = getManga(rowIndex);
        switch (columnIndex) {
            case -1:
                return m;
            case 0:
                return m.getMangaName();
            case 1:
                return m.getServer().getServerName();
            case 2:
                return m.getUrl();
            case 3:
                return m.getHashId();
        }
        return null;
    }

    public Manga getManga(int rowIndex) {
        return listManga.get(rowIndex);
    }
}
