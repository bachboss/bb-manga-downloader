/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import comichtmlgender.HTMLGenerator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import bbmangadownloader.cache.CacheLoader;
import bbmangadownloader.config.ConfigManager;
import bbmangadownloader.database.Database;
import bbmangadownloader.database.entity.LinkWatcherLinkms;
import bbmangadownloader.database.entity.Watchers;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.gui.bus.ListTaskDownloader;
import bbmangadownloader.gui.model.ChapterDownloadModel;
import bbmangadownloader.gui.model.Watcher;
import bbmangadownloader.gui.model.WatcherMangaTreeTableModel;
import bbmangadownloader.gui.model.WatcherTableModel;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 *
 * @author Bach
 */
public class MangaWatcherGUI extends javax.swing.JFrame {

    private WatcherTableModel modelWatcher;
    private WatcherMangaTreeTableModel modelManga;
    private ChapterDownloadModel modelDownload;
    private Watcher currentWatcher;
    private Manga selectingManga;
    private Chapter[] selectingChapers;
    private SearchMangaDialog searchManga;

    public MangaWatcherGUI() {
        initComponents();
        initWatcherTable();
        initPopUpMenuForItem();
        initDownloadTable();
//        initTreeTable();
//        initColumnForTreeTable();

    }

    private void initColumnForTreeTable() {
        TableColumnModel columnModel = ttblList.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(300);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(150);
    }

    private void initDownloadTable() {
        this.modelDownload = new ChapterDownloadModel();
        tblDownload.setModel(modelDownload);
    }

    private void initWatcherTable() {
        modelWatcher = new WatcherTableModel();
        tblWatcher.setModel(modelWatcher);
        loadAllWatchers();
    }

    private void initPopUpMenuForItem() {
        tblWatcher.setComponentPopupMenu(popWatcher);
        tblWatcher.setInheritsPopupMenu(true);

        tblDownload.setComponentPopupMenu(popDownload);
        tblDownload.setInheritsPopupMenu(true);

//        ttblList.setComponentPopupMenu(popListManga);
//        ttblList.setInheritsPopupMenu(true);

        ttblList.addMouseListener(new MouseAdapter() {

            int lastRelease = -1;

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int r = ttblList.rowAtPoint(e.getPoint());
//                    if (lastRelease == -1) {
//                        lastRelease = r;
//                    }
                    if (r >= 0 && r < ttblList.getRowCount()) {
                        ttblList.setRowSelectionInterval(r, r);
                        lastRelease = r;
                    } else {
                        ttblList.clearSelection();
                    }

                    int rowindex = ttblList.getSelectedRow();
                    if (rowindex < 0) {
                        return;
                    }

                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                        Object o = ttblList.getValueAt(ttblList.getSelectedRow(), -1);
                        if (o instanceof Manga) {
                            selectingManga = (Manga) o;
                            popListManga.show(e.getComponent(), e.getX(), e.getY());
                            System.out.println();
                        } else if (o instanceof Chapter) {
                            int[] arrInt = ttblList.getSelectedRows();
                            ArrayList<Chapter> arrTemp = new ArrayList<>();
                            for (int i : arrInt) {
                                Object o2 = ttblList.getValueAt(i, -1);
                                if (o2 instanceof Chapter) {
                                    arrTemp.add((Chapter) o2);
                                }
                            }
                            selectingChapers = (Chapter[]) arrTemp.toArray(new Chapter[0]);
                            if (selectingChapers.length != 0) {
                                popManga.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                    }
                }
            }
        });

        //        ttblList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        popWatcher = new javax.swing.JPopupMenu();
        mnWatcherCheck = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnWatcherRename = new javax.swing.JMenuItem();
        mnWatcherRemove = new javax.swing.JMenuItem();
        mnWatcherAddHost = new javax.swing.JMenuItem();
        popListManga = new javax.swing.JPopupMenu();
        mnListAdd = new javax.swing.JMenuItem();
        mnListEdit = new javax.swing.JMenuItem();
        mnListRemove = new javax.swing.JMenuItem();
        popManga = new javax.swing.JPopupMenu();
        mnMangaAdd = new javax.swing.JMenuItem();
        popDownload = new javax.swing.JPopupMenu();
        mnDownloadStart = new javax.swing.JMenuItem();
        mnDownloadStop = new javax.swing.JMenuItem();
        dlgRenameWatcher = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        txtWatcherRename = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        pnlLeft = new javax.swing.JPanel();
        txtWatcherName = new javax.swing.JTextField();
        btnAddWatcher = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblWatcher = new javax.swing.JTable();
        pnlRightTop = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ttblList = new org.jdesktop.swingx.JXTreeTable();
        pnlFilter = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cldFrom = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnFrom = new javax.swing.JSpinner();
        spnTo = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        cldTo = new org.jdesktop.swingx.JXDatePicker();
        jButton1 = new javax.swing.JButton();
        pnlRightLeft = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDownload = new javax.swing.JTable();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        mnWatcherCheck.setText("Check");
        mnWatcherCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherCheckActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherCheck);
        popWatcher.add(jSeparator1);

        mnWatcherRename.setText("Rename");
        mnWatcherRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherRenameActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherRename);

        mnWatcherRemove.setText("Remove");
        popWatcher.add(mnWatcherRemove);

        mnWatcherAddHost.setText("Add host(s)");
        mnWatcherAddHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherAddHostActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherAddHost);

        mnListAdd.setText("Add");
        mnListAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListAddActionPerformed(evt);
            }
        });
        popListManga.add(mnListAdd);

        mnListEdit.setText("Edit");
        mnListEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListEditActionPerformed(evt);
            }
        });
        popListManga.add(mnListEdit);

        mnListRemove.setText("Remove");
        popListManga.add(mnListRemove);

        mnMangaAdd.setLabel("Add to download");
        mnMangaAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMangaAddActionPerformed(evt);
            }
        });
        popManga.add(mnMangaAdd);

        mnDownloadStart.setText("Start");
        mnDownloadStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadStartActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadStart);

        mnDownloadStop.setText("Stop");
        popDownload.add(mnDownloadStop);

        jLabel1.setText("New Name");

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        javax.swing.GroupLayout dlgRenameWatcherLayout = new javax.swing.GroupLayout(dlgRenameWatcher.getContentPane());
        dlgRenameWatcher.getContentPane().setLayout(dlgRenameWatcherLayout);
        dlgRenameWatcherLayout.setHorizontalGroup(
            dlgRenameWatcherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgRenameWatcherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWatcherRename, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgRenameWatcherLayout.setVerticalGroup(
            dlgRenameWatcherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgRenameWatcherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgRenameWatcherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtWatcherRename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAddWatcher.setText("+");
        btnAddWatcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddWatcherActionPerformed(evt);
            }
        });

        tblWatcher.setModel(new javax.swing.table.DefaultTableModel(
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
        tblWatcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblWatcherMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblWatcherMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblWatcher);

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addComponent(txtWatcherName, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddWatcher, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWatcherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddWatcher))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jScrollPane4.setViewportView(ttblList);

        pnlFilter.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));

        jLabel2.setText("Upload Date from");

        jLabel3.setText("Chapter from");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("to");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("to");

        jButton1.setText("Apply Filter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFilterLayout = new javax.swing.GroupLayout(pnlFilter);
        pnlFilter.setLayout(pnlFilterLayout);
        pnlFilterLayout.setHorizontalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cldFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(spnFrom)))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(cldTo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(spnTo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))))
        );
        pnlFilterLayout.setVerticalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(cldFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cldTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spnFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(spnTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );

        javax.swing.GroupLayout pnlRightTopLayout = new javax.swing.GroupLayout(pnlRightTop);
        pnlRightTop.setLayout(pnlRightTopLayout);
        pnlRightTopLayout.setHorizontalGroup(
            pnlRightTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightTopLayout.createSequentialGroup()
                .addGroup(pnlRightTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                    .addGroup(pnlRightTopLayout.createSequentialGroup()
                        .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlRightTopLayout.setVerticalGroup(
            pnlRightTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRightTopLayout.createSequentialGroup()
                .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
        );

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
        jScrollPane3.setViewportView(tblDownload);

        javax.swing.GroupLayout pnlRightLeftLayout = new javax.swing.GroupLayout(pnlRightLeft);
        pnlRightLeft.setLayout(pnlRightLeftLayout);
        pnlRightLeftLayout.setHorizontalGroup(
            pnlRightLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLeftLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlRightLeftLayout.setVerticalGroup(
            pnlRightLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLeftLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu5.setText("File");

        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        jMenuBar3.add(jMenu5);

        jMenu7.setText("Edit");

        jMenuItem1.setText("HTML Generator");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem1);

        jMenuItem4.setText("Configuration");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem4);

        jMenuBar3.add(jMenu7);

        jMenu6.setText("Help");

        jMenuItem2.setText("About us");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem2);

        jMenuBar3.add(jMenu6);

        setJMenuBar(jMenuBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRightTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRightLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRightTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRightLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblWatcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblWatcherMouseClicked
        if (evt.getClickCount() >= 2) {
            // Double Click !
            int row = tblWatcher.getSelectedRow();
            if (row != -1) {
                currentWatcher = modelWatcher.getWatcherAt(row);
                loadWatcher(currentWatcher);
            }
        }

    }//GEN-LAST:event_tblWatcherMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        applyFilter();
        ttblList.setTreeTableModel(new WatcherMangaTreeTableModel());
        ttblList.setTreeTableModel(modelManga);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddWatcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddWatcherActionPerformed
        String name = txtWatcherName.getText().trim();
        if (!name.isEmpty()) {
            Watchers wE = new Watchers();
            wE.setWName(name);
            Database.createWatcher(wE);
            modelWatcher.addWatcher(new Watcher(wE));
        }
    }//GEN-LAST:event_btnAddWatcherActionPerformed

    private void tblWatcherMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblWatcherMouseReleased
        // TODO add your handling code here:
        // on Right click
    }//GEN-LAST:event_tblWatcherMouseReleased

    private void mnWatcherCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherCheckActionPerformed
        // Check all manga in watcher        

        loadAllChapterInWatcher();

    }//GEN-LAST:event_mnWatcherCheckActionPerformed

    private void mnListAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListAddActionPerformed
        addMangasToWatcher();
    }//GEN-LAST:event_mnListAddActionPerformed

    private void mnWatcherRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherRenameActionPerformed
        actionClickOnRenameWatcher();
    }//GEN-LAST:event_mnWatcherRenameActionPerformed

    private void mnListEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListEditActionPerformed
        int s = ttblList.getSelectedRow();
        System.out.println("Selected: " + s);
//        
//        modelWatcher.get(ttblList.getSelectedRow())
//        if ()

    }//GEN-LAST:event_mnListEditActionPerformed

    private void mnMangaAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMangaAddActionPerformed
        addToDownloadList();
    }//GEN-LAST:event_mnMangaAddActionPerformed

    private void mnDownloadStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadStartActionPerformed

        new Thread(new ListTaskDownloader(modelDownload.getListDownload(), modelDownload)).start();

    }//GEN-LAST:event_mnDownloadStartActionPerformed

    private void mnWatcherAddHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherAddHostActionPerformed
        addMangasToWatcher();
    }//GEN-LAST:event_mnWatcherAddHostActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String name = txtWatcherRename.getText();
        if (!(name = name.trim()).isEmpty()) {
            renameWatcher(currentWatcher, name);
            dlgRenameWatcher.setVisible(false);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        comichtmlgender.HTMLGenerator dialog = new HTMLGenerator(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ConfigurationDialog form = new ConfigurationDialog(this, true);
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AboutUsDiaglog(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddWatcher;
    private org.jdesktop.swingx.JXDatePicker cldFrom;
    private org.jdesktop.swingx.JXDatePicker cldTo;
    private javax.swing.JDialog dlgRenameWatcher;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem mnDownloadStart;
    private javax.swing.JMenuItem mnDownloadStop;
    private javax.swing.JMenuItem mnListAdd;
    private javax.swing.JMenuItem mnListEdit;
    private javax.swing.JMenuItem mnListRemove;
    private javax.swing.JMenuItem mnMangaAdd;
    private javax.swing.JMenuItem mnWatcherAddHost;
    private javax.swing.JMenuItem mnWatcherCheck;
    private javax.swing.JMenuItem mnWatcherRemove;
    private javax.swing.JMenuItem mnWatcherRename;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRightLeft;
    private javax.swing.JPanel pnlRightTop;
    private javax.swing.JPopupMenu popDownload;
    private javax.swing.JPopupMenu popListManga;
    private javax.swing.JPopupMenu popManga;
    private javax.swing.JPopupMenu popWatcher;
    private javax.swing.JSpinner spnFrom;
    private javax.swing.JSpinner spnTo;
    private javax.swing.JTable tblDownload;
    private javax.swing.JTable tblWatcher;
    private org.jdesktop.swingx.JXTreeTable ttblList;
    private javax.swing.JTextField txtWatcherName;
    private javax.swing.JTextField txtWatcherRename;
    // End of variables declaration//GEN-END:variables

    private void loadWatcher(Watcher w) {
        if (modelManga == null) {
            modelManga = new WatcherMangaTreeTableModel();
            modelManga.initFromWatcher(w);
            ttblList.setTreeTableModel(modelManga);
            initColumnForTreeTable();
        } else {
            ttblList.setTreeTableModel(new DefaultTreeTableModel());
            modelManga.initFromWatcher(w);
            ttblList.setTreeTableModel(modelManga);
        }
    }

    private void applyFilter() {
        int fromChapter = (Integer) spnFrom.getValue();
        int toChapter = (Integer) spnTo.getValue();
        modelManga.applyFilter(fromChapter, toChapter);
    }

    private void addToDownloadList() {
        if (selectingChapers == null) {
            return;
        }
        if (selectingChapers.length == 0) {
            return;
        }
        for (Chapter c : selectingChapers) {
            modelDownload.addChapter(c);
        }
    }

    private void addMangasToWatcher() {
        if (currentWatcher != null) {
            if (searchManga == null) {
                searchManga = new SearchMangaDialog(this, true);
            }
            searchManga.setWatcher(currentWatcher);
            searchManga.setVisible(true);
            System.out.println("Number of Mangas: " + currentWatcher.getMangaCount());

            for (Manga m : currentWatcher.getLstManga()) {
                System.out.println(m.getServer().hashCode() + "\t" + m.getServer().getServerName() + "\t");
            }
        }
    }

    private void loadAllChapterInWatcher() {
        if (currentWatcher != null) {
            List<Manga> lstManga = currentWatcher.getLstManga();
            for (final Manga m : lstManga) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        m.loadChapter();
                    }
                }).start();
            }
        }
    }

    private void loadAllWatchers() {
        List<Watchers> lstTemp = Database.getAllWatchers();
        for (Watchers wE : lstTemp) {
            Watcher w = new Watcher(wE);
            modelWatcher.addWatcher(w);
        }
        tblWatcher.validate();
    }

    private void actionClickOnRenameWatcher() {
        if (currentWatcher != null) {
            txtWatcherRename.setText(currentWatcher.getName());
            dlgRenameWatcher.pack();
            dlgRenameWatcher.setVisible(true);
        }
    }

    private void renameWatcher(Watcher w, String newName) {
        Watchers wE = new Watchers(w.getId());
        wE.setWName(newName);
        List<LinkWatcherLinkms> l = Collections.emptyList();
        wE.setLinkWatcherLinkmsList(l);
        try {
            Database.editWatcher(wE);
        } catch (Exception ex) {
            Logger.getLogger(MangaWatcherGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        w.setName(wE.getWName());
        modelWatcher.fireTableDataChanged();
        System.out.println("Renamed to " + w.getName());
    }
}
