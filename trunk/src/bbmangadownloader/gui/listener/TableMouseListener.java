/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Bach
 */
public class TableMouseListener extends MouseAdapter {

    private JTable table;
    private TableMouseMenuHandler handler;

    public TableMouseListener(JTable table, TableMouseMenuHandler handler) {
        this.table = table;
        this.handler = handler;
    }

    private boolean isRowInSelecting(int value, int[] arrValue) {
        for (int i : arrValue) {
            if (value == i) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int mouseAtRow = table.rowAtPoint(e.getPoint());
            int[] rowSelecting = table.getSelectedRows();
//                    int rowindex = ttblList.getSelectedRow();
                    /*
             * If mouseAtRow in selecingRow then show menu else select one
             * record !
             */

            if (isRowInSelecting(mouseAtRow, rowSelecting)) {
                showMenu(e);
            } else {
                table.setRowSelectionInterval(mouseAtRow, mouseAtRow);
                showMenu(e);
            }
//                    } else {
//                        ttblList.clearSelection();
//                    }
            //                    if (mouseAtRow >= 0 && mouseAtRow < ttblList.getRowCount()) {
            //                    } else {
            //                        ttblList.clearSelection();
            //                    }
            //<editor-fold>
            //                    if (r >= 0 && r < ttblList.getRowCount()) {
            //                        ttblList.setRowSelectionInterval(r, r);
            //                        lastRelease = r;
            //                    } else {
            //                        ttblList.clearSelection();
            //                    }
            //</editor-fold>
            //                    if (rowindex < 0) {
            //                        return;
            //                    }
            {
            }
        }
    }

    private void showMenu(MouseEvent e) {
        if (handler != null) {
            handler.onShowMenu(e);
        }
    }
}
