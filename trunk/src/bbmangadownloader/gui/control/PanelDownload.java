/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.control;

import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.bus.model.data.DownloadTask.DownloadTaskStatus;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.gui.bus.TaskDownloader;
import bbmangadownloader.gui.model.ChapterDownloadModel;
import bbmangadownloader.gui.model.MyColumnSorter;
import bbmangadownloader.gui.model.MyTableModelSortable;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.ult.GUIUtilities;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Bach
 */
public class PanelDownload extends javax.swing.JPanel implements
        TaskDownloader.ITaskDownloaderListener {

    // fixed;
//    private IPanelDownloadListener listener;
    private ChapterDownloadModel modelDownload;
    private final LinkedList<DownloadTask> queue;
    private int numberOfDownloading = 0;

    private static int getMaxiumNumberDownload() {
        return ConfigManager.getCurrentInstance().getMaxiumDownloadInQueue();
    }

    /**
     * Creates new form PanelDownload
     */
    public PanelDownload() {
//        if (listener == null) {
//            throw new  ("IPanelDownloadListener must not be null");
//        }
//        this.listener = listener;
        initComponents();
//        tblDownload.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initData();
        initHeader();
        queue = new LinkedList<DownloadTask>();

    }

    private void initData() {
        this.modelDownload = new ChapterDownloadModel();
        tblDownload.setModel(modelDownload);

        tblDownload.setComponentPopupMenu(popDownload);
        tblDownload.setInheritsPopupMenu(true);
    }

    private void initHeader() {
        //<editor-fold>
        tblDownload.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblDownload.getColumnModel().getColumn(0).setMaxWidth(100);
        tblDownload.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblDownload.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDownload.getColumnModel().getColumn(2).setMaxWidth(100);
        tblDownload.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblDownload.setAutoCreateColumnsFromModel(false);

        addHeaderListener(tblDownload);
        //</editor-fold>        
    }

    //<editor-fold>
    private void addHeaderListener(JTable table) {
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JTable table = ((JTableHeader) evt.getSource()).getTable();
                TableColumnModel colModel = table.getColumnModel();

                // The index of the column whose header was clicked
                int vColIndex = colModel.getColumnIndexAtX(evt.getX());
                int mColIndex = table.convertColumnIndexToModel(vColIndex);

                // Return if not clicked on any column header
                if (vColIndex == -1) {
                    return;
                }

                // Determine if mouse was clicked between column heads
                Rectangle headerRect = table.getTableHeader().getHeaderRect(vColIndex);
                if (vColIndex == 0) {
                    headerRect.width -= 3;    // Hard-coded constant
                } else {
                    headerRect.grow(-3, 0);   // Hard-coded constant
                }
                if (!headerRect.contains(evt.getX(), evt.getY())) {
                    // Mouse was clicked between column heads
                    // vColIndex is the column head closest to the click

                    // vLeftColIndex is the column head to the left of the click
                    int vLeftColIndex = vColIndex;
                    if (evt.getX() < headerRect.x) {
                        vLeftColIndex--;
                    }
                    //System.out.println("Click on the middle");
                } else {
                    sortAllRowsBy((MyTableModelSortable) table.getModel(), mColIndex);
                }
            }
        });
    }

    private void sortAllRowsBy(MyTableModelSortable model, int colIndex) {
        if (model.isSortable(colIndex)) {
            List data = model.getData();
            model.swithSortOrder();
            Collections.sort(data, new MyColumnSorter(colIndex, model));
            ((AbstractTableModel) model).fireTableDataChanged();
        }
    }

    //</editor-fold>
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popDownload = new javax.swing.JPopupMenu();
        mnDownloadStartImmediately = new javax.swing.JMenuItem();
        mnDownloadStartQueue = new javax.swing.JMenuItem();
        mnDownloadResume = new javax.swing.JMenuItem();
        mnDownloadStop = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnDownloadRemove = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnDownloadViewInBrowser = new javax.swing.JMenuItem();
        mnDownloadViewFolder = new javax.swing.JMenuItem();
        sclDownload = new javax.swing.JScrollPane();
        tblDownload = new javax.swing.JTable();

        popDownload.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                popDownloadPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        mnDownloadStartImmediately.setText("Start (Immediately)");
        mnDownloadStartImmediately.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadStartImmediatelyActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadStartImmediately);

        mnDownloadStartQueue.setText("Start (Queue)");
        mnDownloadStartQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadStartQueueActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadStartQueue);

        mnDownloadResume.setLabel("Resume");
        mnDownloadResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadResumeActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadResume);

        mnDownloadStop.setLabel("Stop");
        mnDownloadStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadStopActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadStop);
        popDownload.add(jSeparator1);

        mnDownloadRemove.setLabel("Remove");
        mnDownloadRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadRemoveActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadRemove);
        popDownload.add(jSeparator2);

        mnDownloadViewInBrowser.setText("View In Browser");
        mnDownloadViewInBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadViewInBrowserActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadViewInBrowser);

        mnDownloadViewFolder.setText("Open Folder");
        mnDownloadViewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadViewFolderActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadViewFolder);

        tblDownload.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDownload.setMinimumSize(new java.awt.Dimension(0, 0));
        sclDownload.setViewportView(tblDownload);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sclDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sclDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mnDownloadStartImmediatelyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadStartImmediatelyActionPerformed
        doDownloadStartDownload();
    }//GEN-LAST:event_mnDownloadStartImmediatelyActionPerformed

    private void mnDownloadRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadRemoveActionPerformed
        doDownloadRemoveFromDownload();
    }//GEN-LAST:event_mnDownloadRemoveActionPerformed

    private void mnDownloadViewInBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadViewInBrowserActionPerformed
        doDownloadViewInBrowser();
    }//GEN-LAST:event_mnDownloadViewInBrowserActionPerformed

    private void mnDownloadViewFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadViewFolderActionPerformed
        doDownloadOpenDownloadFolder();
    }//GEN-LAST:event_mnDownloadViewFolderActionPerformed

    private void mnDownloadResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadResumeActionPerformed
        doDownloadResume();
    }//GEN-LAST:event_mnDownloadResumeActionPerformed

    private void mnDownloadStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadStopActionPerformed
        doDownloadStop();
    }//GEN-LAST:event_mnDownloadStopActionPerformed

    private void popDownloadPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popDownloadPopupMenuWillBecomeVisible
        doEnableMenuItem();
    }//GEN-LAST:event_popDownloadPopupMenuWillBecomeVisible

    private void mnDownloadStartQueueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadStartQueueActionPerformed
        doStartQueue();
    }//GEN-LAST:event_mnDownloadStartQueueActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem mnDownloadRemove;
    private javax.swing.JMenuItem mnDownloadResume;
    private javax.swing.JMenuItem mnDownloadStartImmediately;
    private javax.swing.JMenuItem mnDownloadStartQueue;
    private javax.swing.JMenuItem mnDownloadStop;
    private javax.swing.JMenuItem mnDownloadViewFolder;
    private javax.swing.JMenuItem mnDownloadViewInBrowser;
    private javax.swing.JPopupMenu popDownload;
    private javax.swing.JScrollPane sclDownload;
    private javax.swing.JTable tblDownload;
    // End of variables declaration//GEN-END:variables

    private void doDownloadRemoveFromDownload() {
        int[] selectedRows = tblDownload.getSelectedRows();
        Arrays.sort(selectedRows);
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            DownloadTask task = modelDownload.removeTaskAt(selectedRows[i]);
            if (task.getStatusEnum() == DownloadTaskStatus.Queue) {
                queue.remove(task);
            }
        }
    }

    private void doDownloadOpenDownloadFolder() {
        DownloadTask task = modelDownload.getTaskAt(tblDownload.getSelectedRow());
        DownloadTaskStatus s = task.getStatusEnum();
        if (s == DownloadTaskStatus.Done || s == DownloadTask.DownloadTaskStatus.Downloading) {
            GUIUtilities.openFile(task.getDownloadTo());
        }
    }

    private DownloadTask getSelectedTask() {
        int row = tblDownload.getSelectedRow();
        return modelDownload.getTaskAt(row);
    }

    private List<DownloadTask> getSelectedTasks() {
        int[] rows = tblDownload.getSelectedRows();
        List<DownloadTask> returnValue = new ArrayList<DownloadTask>();
        for (int row : rows) {
            returnValue.add(modelDownload.getTaskAt(row));
        }
        return returnValue;
    }

    private void doDownloadViewInBrowser() {
        Chapter c = getSelectedTask().getChapter();
        if (c != null) {
            GUIUtilities.openLink(c.getUrl());
        }
    }

    private void doDownloadStop() {
        List<DownloadTask> lstTask = getSelectedTasks();
        for (DownloadTask task : lstTask) {
            task.getDownloader().stop();
            numberOfDownloading--;
        }
        if (bbmangadownloader.BBMangaDownloader.TEST) {
            System.out.println("Number of Downloading: " + numberOfDownloading);
        }
    }

    private void doDownloadResume() {
        List<DownloadTask> lstTask = getSelectedTasks();
        for (DownloadTask task : lstTask) {
            task.getDownloader().resume();
            numberOfDownloading++;
        }
        if (bbmangadownloader.BBMangaDownloader.TEST) {
            System.out.println("Number of Downloading: " + numberOfDownloading);
        }
    }

    private void doDownloadStartDownload() {
        List<DownloadTask> lstTask = getSelectedTasks();
        for (DownloadTask task : lstTask) {
            task.getDownloader().start();
            numberOfDownloading++;
        }
        if (bbmangadownloader.BBMangaDownloader.TEST) {
            System.out.println("Number of Downloading: " + numberOfDownloading);
        }
    }

    public void addChapter(Chapter c) {
        DownloadTask t = modelDownload.addChapter(c);
        if (t.getDownloader() == null) {
            t.setDownloader(new TaskDownloader(this, t));
        }
    }

    public void listAll() {
        for (DownloadTask t : modelDownload.getListDownload()) {
            System.out.println(t.getChapter().getDisplayName() + "\t"
                    + t.getStatus() + "\t" + t.getStatusEnum());
        }
    }

    @Override
    public void updateRecord(DownloadTask task) {
        modelDownload.fireDownloadTaskStatusUpdated(task);
    }

    private void doEnableMenuItem() {
        List<DownloadTask> lstSelectedTask = getSelectedTasks();
        boolean isEnableStartNow = false, isEnableStop = false, isEnableResume = false,
                isEnableStatQueue = false;
        for (Iterator<DownloadTask> it = lstSelectedTask.iterator(); it.hasNext();) {
            DownloadTask task = it.next();
            DownloadTaskStatus status = task.getStatusEnum();
            switch (status) {
                case No:
                case Error:
                    isEnableStartNow = true;
                    isEnableStatQueue = true;
                    break;
                case Checking:
                case Parsing:
                case Downloading:
                    isEnableStop = true;
                    break;
//                case Cleaning:                    
//                case Done:
            }
            if (!task.isIsRunning()) {
                isEnableResume = true;
            }
        }
        mnDownloadStartImmediately.setEnabled(isEnableStartNow);
        mnDownloadStop.setEnabled(isEnableStop);
        mnDownloadResume.setEnabled(isEnableResume);
        mnDownloadStartQueue.setEnabled(isEnableStatQueue);
    }

    private void doStartQueue() {
        boolean isAdded = false;
        List<DownloadTask> listTask = getSelectedTasks();
        for (DownloadTask task : listTask) {
            DownloadTaskStatus status = task.getStatusEnum();
            if (status == DownloadTaskStatus.No
                    || status == DownloadTaskStatus.Error) {
                task.setStatus(DownloadTaskStatus.Queue);
                modelDownload.fireDownloadTaskStatusUpdated(task);
                queue.addLast(task);
                isAdded = true;
            }
        }
        if (isAdded) {
            invokeQueue();
        }
    }

    private void invokeQueue() {
        if (!queue.isEmpty() && numberOfDownloading < getMaxiumNumberDownload()) {
            synchronized (queue) {
                while (numberOfDownloading < getMaxiumNumberDownload()
                        && !queue.isEmpty()) {
                    DownloadTask task = queue.removeFirst();
                    if (task.getStatusEnum() == DownloadTaskStatus.Queue) {
                        numberOfDownloading++;
                        task.getDownloader().start();
                    }
                }
            }
        }
    }

    @Override
    public void onTaskDownloadFinish(DownloadTask task) {
        numberOfDownloading--;
        invokeQueue();
    }

    public static interface IPanelDownloadListener {
    }
}
