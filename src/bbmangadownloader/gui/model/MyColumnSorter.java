/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import java.util.Comparator;

/**
 *
 * @author Bach
 */
public class MyColumnSorter<T> implements Comparator<T> {

    private int colIndex;
    private MyTableModelSortable<T> tableModel;

    public MyColumnSorter(int colIndex, MyTableModelSortable<T> tableModel) {
        this.colIndex = colIndex;
        this.tableModel = tableModel;
    }

    @Override
    public int compare(T t1, T t2) {
        Object o1 = tableModel.getRealDataAtColumn(colIndex, t1);
        Object o2 = tableModel.getRealDataAtColumn(colIndex, t2);
        if (o1 == null) {
            return (o2 == null ? 0 : -1);
        }
        // if (o1!= null) && (o2==null)
        if (o2 == null) {
            return +1;
        }
        int value;
        if (o1 instanceof Comparable) {
            value = ((Comparable) o1).compareTo(o2);
        } else {
//            System.out.println("Hash Code of " + o1.getClass());
            value = (o1.toString()).compareTo(o2.toString());
        }
        return (tableModel.getIsAsc() ? value : (-value));
    }
}
