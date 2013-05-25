package bbmangadownloader.ult;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author bach
 */
public class MySwingUtilities {

    public static boolean isDoubleLeftClick(MouseEvent evt) {
        if (evt.getClickCount() < 2) {
            return false;
        }
        if (SwingUtilities.isLeftMouseButton(evt)) {
            return true;
        }
        return false;
    }

    public static boolean isDoubleRightClick(MouseEvent evt) {
        if (evt.getClickCount() < 2) {
            return false;
        }
        if (SwingUtilities.isRightMouseButton(evt)) {
            return true;
        }
        return false;
    }

    public static boolean isMiddleClick(MouseEvent evt) {
        return (SwingUtilities.isMiddleMouseButton(evt));
    }

    public static <T> T getSelectedObject(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return (T) table.getValueAt(row, -1);
    }

    public static <T> List<T> getSelectedObjects(JTable table) {
        int[] row = table.getSelectedRows();
        ArrayList<T> listReturn = new ArrayList<T>();
        for (int i : row) {
            T data = (T) table.getValueAt(i, -1);
            listReturn.add(data);
        }
        return listReturn;
    }

    public static int getInt(Object o) throws NumberFormatException {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        } else {
            return Integer.parseInt(o.toString());
        }
    }

    public static long getLong(Object o) throws NumberFormatException {
        if (o instanceof Number) {
            return ((Number) o).longValue();
        } else {
            return Long.parseLong(o.toString());
        }
    }
}
