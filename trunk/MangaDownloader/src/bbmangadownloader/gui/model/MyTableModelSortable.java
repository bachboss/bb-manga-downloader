/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import java.util.List;

/**
 *
 * @author Bach
 */
public interface MyTableModelSortable<T> {

    public List getData();

    public boolean isSortable(int column);

    public Object getDisplayDataAtColumn(int column, T data);

    public Object getRealDataAtColumn(int column, T data);

    public boolean getIsAsc();

    public boolean swithSortOrder();
}
