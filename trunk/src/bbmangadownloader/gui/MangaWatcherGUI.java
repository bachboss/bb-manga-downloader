/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.database.Database;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.gui.listener.TableMouseListener;
import bbmangadownloader.gui.listener.TableMouseMenuHandler;
import bbmangadownloader.gui.model.Watcher;
import bbmangadownloader.gui.model.WatcherMangaTreeTableModel;
import bbmangadownloader.gui.model.WatcherTableModel;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.MemoryUtilities;
import bbmangadownloader.ult.MultitaskJob;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 *
 * @author Bach
 */
public class MangaWatcherGUI extends javax.swing.JFrame {

    private WatcherTableModel modelWatcher;
    private WatcherMangaTreeTableModel modelManga;
    private Watcher currentWatcher;
    private Manga selectingManga;
    private Chapter[] selectingChapers;
    private SearchMangaDialog dlgSearchManga;
    private AddMangaDialog dlgAddManga;

    public MangaWatcherGUI() {
        initIconAndTitle();
        initComponents();
        initWatcherTable();
        initPopUpMenuForItem();
        initColumnModel();
//        initTreeTable();
//        initColumnForTreeTable();
    }

    private void initIconAndTitle() {
        this.setTitle(bbmangadownloader.BBMangaDownloader.APPLICATION_NAME);
        this.setIconImage(bbmangadownloader.BBMangaDownloader.getApplicationIcon());
    }

    private void initColumnModel() {
        {
            TableColumnModel columnModel = tblWatcher.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(150);
            columnModel.getColumn(1).setPreferredWidth(50);
        }
    }

    private void initColumnModelForTreeTable() {
        {
            TableColumnModel columnModel = ttblList.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(300);
            columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(150);
            ttblList.setAutoCreateColumnsFromModel(false);
        }

    }

    private void initWatcherTable() {
        modelWatcher = new WatcherTableModel();
        tblWatcher.setModel(modelWatcher);
        loadAllWatchers();
    }

    private void initPopUpMenuForItem() {
//        ttblList.setComponentPopupMenu(popListManga);
//        ttblList.setInheritsPopupMenu(true);

//        tblWatcher.setComponentPopupMenu(popWatcher);
//        tblWatcher.setInheritsPopupMenu(true);

        tblWatcher.addMouseListener(new TableMouseListener(tblWatcher, new TableMouseMenuHandler() {
            private void setEnableOnMulti(boolean enable) {
                mnWatcherAddHost.setEnabled(enable);
                mnWatcherRename.setEnabled(enable);
            }

            @Override
            public void onShowMenu(MouseEvent e) {
                boolean isPopup = GUIUtilities.isPopup(e);
                if (isPopup && e.getComponent() instanceof JTable) {
                    boolean isDisable = (tblWatcher.getSelectedRowCount() > 1);
                    System.out.println("Show Menu, disable = " + isDisable);
                    if (isDisable) {
                        setEnableOnMulti(false);
                    }
//                    popWatcher.revalidate();
                    popWatcher.show(e.getComponent(), e.getX(), e.getY());
                    popWatcher.repaint();
                    if (isDisable) {
                        setEnableOnMulti(true);
                    }
                }
            }
        }));

        ttblList.addMouseListener(new TableMouseListener(ttblList, new TableMouseMenuHandler() {
            @Override
            public void onShowMenu(MouseEvent e) {
                boolean isPopup = GUIUtilities.isPopup(e);
                if (isPopup && e.getComponent() instanceof JTable) {
                    mnChapterViewOnBrowser.setEnabled(true);
                    Object o = ttblList.getValueAt(ttblList.getSelectedRow(), -1);
                    if (o instanceof Manga) {
                        selectingManga = (Manga) o;
                        mnChapterViewOnBrowser.setEnabled(false);
                        popManga.show(e.getComponent(), e.getX(), e.getY());
                        mnChapterViewOnBrowser.setEnabled(true);
                    } else if (o instanceof Chapter) {
                        int[] arrInt = ttblList.getSelectedRows();
                        if (arrInt.length > 1) {
                            mnChapterViewOnBrowser.setEnabled(false);
                        }
                        ArrayList<Chapter> arrTemp = new ArrayList<Chapter>();
                        for (int i : arrInt) {
                            Object o2 = ttblList.getValueAt(i, -1);
                            if (o2 instanceof Chapter) {
                                arrTemp.add((Chapter) o2);
                            }
                        }
                        selectingChapers = (Chapter[]) arrTemp.toArray(new Chapter[0]);
                        if (selectingChapers.length != 0) {
                            popChapter.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
            }
        }));


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
        mnWatcherForceCheck = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnWatcherRename = new javax.swing.JMenuItem();
        mnWatcherRemove = new javax.swing.JMenuItem();
        mnWatcherAddHost = new javax.swing.JMenuItem();
        mnWatcherAddHostManually = new javax.swing.JMenuItem();
        popManga = new javax.swing.JPopupMenu();
        mnListAdd = new javax.swing.JMenuItem();
        mnMangaRemove = new javax.swing.JMenuItem();
        mnMangaViewOnBrowser = new javax.swing.JMenuItem();
        popChapter = new javax.swing.JPopupMenu();
        mnChapterAdd = new javax.swing.JMenuItem();
        mnChapterViewOnBrowser = new javax.swing.JMenuItem();
        dlgRenameWatcher = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        txtWatcherRename = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlLeft = new javax.swing.JPanel();
        txtWatcherName = new javax.swing.JTextField();
        btnAddWatcher = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblWatcher = new javax.swing.JTable();
        pnlRight = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnlRightTop = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ttblList = new org.jdesktop.swingx.JXTreeTable();
        pnlFilter = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        spnFrom = new javax.swing.JSpinner();
        spnTo = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pnlRightLeft = new javax.swing.JPanel();
        panelDownload = new bbmangadownloader.gui.control.PanelDownload();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
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

        mnWatcherForceCheck.setText("Force check");
        mnWatcherForceCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherForceCheckActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherForceCheck);
        popWatcher.add(jSeparator1);

        mnWatcherRename.setText("Rename");
        mnWatcherRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherRenameActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherRename);

        mnWatcherRemove.setText("Remove");
        mnWatcherRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherRemoveActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherRemove);

        mnWatcherAddHost.setText("Add host(s) (Search)");
        mnWatcherAddHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherAddHostActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherAddHost);

        mnWatcherAddHostManually.setText("Add host(s) (Manually)");
        mnWatcherAddHostManually.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherAddHostManuallyActionPerformed(evt);
            }
        });
        popWatcher.add(mnWatcherAddHostManually);

        mnListAdd.setText("Add");
        mnListAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListAddActionPerformed(evt);
            }
        });
        popManga.add(mnListAdd);

        mnMangaRemove.setText("Remove");
        mnMangaRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMangaRemoveActionPerformed(evt);
            }
        });
        popManga.add(mnMangaRemove);

        mnMangaViewOnBrowser.setText("View in browser");
        mnMangaViewOnBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMangaViewOnBrowserActionPerformed(evt);
            }
        });
        popManga.add(mnMangaViewOnBrowser);

        mnChapterAdd.setLabel("Add to download");
        mnChapterAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChapterAddActionPerformed(evt);
            }
        });
        popChapter.add(mnChapterAdd);

        mnChapterViewOnBrowser.setText("View in browser");
        mnChapterViewOnBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChapterViewOnBrowserActionPerformed(evt);
            }
        });
        popChapter.add(mnChapterViewOnBrowser);

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

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 0));
        jSplitPane1.setName(""); // NOI18N
        jSplitPane1.setOneTouchExpandable(true);

        btnAddWatcher.setText("+");
        btnAddWatcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddWatcherActionPerformed(evt);
            }
        });

        tblWatcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblWatcherMouseClicked(evt);
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addComponent(txtWatcherName, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddWatcher, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWatcherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddWatcher))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(pnlLeft);

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(350);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setOneTouchExpandable(true);

        jScrollPane4.setViewportView(ttblList);

        pnlFilter.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));

        jLabel3.setText("Chapter from");

        spnFrom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnFromStateChanged(evt);
            }
        });

        spnTo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnToStateChanged(evt);
            }
        });

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
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnTo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        pnlFilterLayout.setVerticalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(spnFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel5)
                .addComponent(spnTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1))
        );

        jButton3.setText("Expand All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRightTopLayout = new javax.swing.GroupLayout(pnlRightTop);
        pnlRightTop.setLayout(pnlRightTopLayout);
        pnlRightTopLayout.setHorizontalGroup(
            pnlRightTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightTopLayout.createSequentialGroup()
                .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(51, Short.MAX_VALUE))
            .addComponent(jScrollPane4)
        );
        pnlRightTopLayout.setVerticalGroup(
            pnlRightTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRightTopLayout.createSequentialGroup()
                .addGroup(pnlRightTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlRightTopLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(pnlRightTop);

        javax.swing.GroupLayout pnlRightLeftLayout = new javax.swing.GroupLayout(pnlRightLeft);
        pnlRightLeft.setLayout(pnlRightLeftLayout);
        pnlRightLeftLayout.setHorizontalGroup(
            pnlRightLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );
        pnlRightLeftLayout.setVerticalGroup(
            pnlRightLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(pnlRightLeft);

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(pnlRight);

        jMenu5.setText("File");

        jMenuItem5.setText("Clear Memory");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem5);
        jMenu5.add(jSeparator2);

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
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblWatcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblWatcherMouseClicked
        if (evt.getClickCount() >= 1) {
            // Double Click !
            int row = tblWatcher.getSelectedRow();
            if (row != -1) {
                currentWatcher = modelWatcher.getWatcherAt(row);
                loadWatcher(currentWatcher);
//                loadAllChapterInWatcher(currentWatcher);
            }
        }
    }//GEN-LAST:event_tblWatcherMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        applyFilter();
        ttblList.setTreeTableModel(WatcherMangaTreeTableModel.empty());
        ttblList.setTreeTableModel(modelManga);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddWatcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddWatcherActionPerformed
        String name = txtWatcherName.getText().trim();
        if (!name.isEmpty()) {
            Watcher w = new Watcher(name);
            Database.createWatcher(w);
            modelWatcher.addWatcher(w);
        }
    }//GEN-LAST:event_btnAddWatcherActionPerformed

    private void mnWatcherCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherCheckActionPerformed
        int[] arrTemp = tblWatcher.getSelectedRows();
        Watcher[] arrWatcher = new Watcher[arrTemp.length];
        for (int i = 0; i < arrTemp.length; i++) {
            arrWatcher[i] = modelWatcher.getWatcherAt(arrTemp[i]);
        }
        loadAllChapterInWatcher(arrWatcher);
    }//GEN-LAST:event_mnWatcherCheckActionPerformed

    private void mnListAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListAddActionPerformed
        addMangasToWatcher(currentWatcher);
    }//GEN-LAST:event_mnListAddActionPerformed

    private void mnWatcherRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherRenameActionPerformed
        actionClickOnRenameWatcher();
    }//GEN-LAST:event_mnWatcherRenameActionPerformed

    private void mnChapterAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChapterAddActionPerformed
        addToDownloadList();
    }//GEN-LAST:event_mnChapterAddActionPerformed

    private void mnWatcherAddHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherAddHostActionPerformed
        int selectedRow = tblWatcher.getSelectedRow();
        addMangasToWatcher(modelWatcher.getWatcherAt(selectedRow));
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
        GenerateHtmlDialog dialog = new GenerateHtmlDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ConfigurationDialog form = new ConfigurationDialog(this, true);
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AboutUsDiaglog(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnChapterViewOnBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChapterViewOnBrowserActionPerformed
        String url = selectingChapers[0].getUrl();
        viewOnBrowser(url);
    }//GEN-LAST:event_mnChapterViewOnBrowserActionPerformed

    private void mnMangaRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMangaRemoveActionPerformed
        Database.removeMangaFromWacher(selectingManga, currentWatcher);
        loadWatcher(currentWatcher);
    }//GEN-LAST:event_mnMangaRemoveActionPerformed

    private void mnMangaViewOnBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMangaViewOnBrowserActionPerformed
        String url = selectingManga.getUrl();
        viewOnBrowser(url);
    }//GEN-LAST:event_mnMangaViewOnBrowserActionPerformed

    private void spnFromStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnFromStateChanged
        int valueFrom = (Integer) spnFrom.getValue();
        int valueTo = (Integer) spnTo.getValue();
        if (valueTo < valueFrom) {
            spnTo.setValue(valueFrom);
        }
    }//GEN-LAST:event_spnFromStateChanged

    private void spnToStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnToStateChanged
        int valueFrom = (Integer) spnFrom.getValue();
        int valueTo = (Integer) spnTo.getValue();
        if (valueTo < valueFrom) {
            spnFrom.setValue(valueTo);
        }
    }//GEN-LAST:event_spnToStateChanged

    private void mnWatcherRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherRemoveActionPerformed
        int[] arrTemp = tblWatcher.getSelectedRows();
        Watcher[] arrWatcher = new Watcher[arrTemp.length];
        for (int i = arrTemp.length - 1; i >= 0; i--) {
            arrWatcher[i] = modelWatcher.getWatcherAt(arrTemp[i]);
        }

        deleteWatchers(arrWatcher);
    }//GEN-LAST:event_mnWatcherRemoveActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ttblList.expandAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mnWatcherForceCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherForceCheckActionPerformed
        int[] arrTemp = tblWatcher.getSelectedRows();
        Watcher[] arrWatcher = new Watcher[arrTemp.length];
        for (int i = 0; i < arrTemp.length; i++) {
            arrWatcher[i] = modelWatcher.getWatcherAt(arrTemp[i]);
        }
        forceLoadAllChapterInWatcher(arrWatcher);
    }//GEN-LAST:event_mnWatcherForceCheckActionPerformed

    private void mnWatcherAddHostManuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherAddHostManuallyActionPerformed
        int selectedRow = tblWatcher.getSelectedRow();
        addMangasManuallyToWatcher(modelWatcher.getWatcherAt(selectedRow));
    }//GEN-LAST:event_mnWatcherAddHostManuallyActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        System.gc();
        System.out.println("Free Memory: " + MemoryUtilities.getFreeMemoryString());
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddWatcher;
    private javax.swing.JDialog dlgRenameWatcher;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuItem mnChapterAdd;
    private javax.swing.JMenuItem mnChapterViewOnBrowser;
    private javax.swing.JMenuItem mnListAdd;
    private javax.swing.JMenuItem mnMangaRemove;
    private javax.swing.JMenuItem mnMangaViewOnBrowser;
    private javax.swing.JMenuItem mnWatcherAddHost;
    private javax.swing.JMenuItem mnWatcherAddHostManually;
    private javax.swing.JMenuItem mnWatcherCheck;
    private javax.swing.JMenuItem mnWatcherForceCheck;
    private javax.swing.JMenuItem mnWatcherRemove;
    private javax.swing.JMenuItem mnWatcherRename;
    private bbmangadownloader.gui.control.PanelDownload panelDownload;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPanel pnlRightLeft;
    private javax.swing.JPanel pnlRightTop;
    private javax.swing.JPopupMenu popChapter;
    private javax.swing.JPopupMenu popManga;
    private javax.swing.JPopupMenu popWatcher;
    private javax.swing.JSpinner spnFrom;
    private javax.swing.JSpinner spnTo;
    private javax.swing.JTable tblWatcher;
    private org.jdesktop.swingx.JXTreeTable ttblList;
    private javax.swing.JTextField txtWatcherName;
    private javax.swing.JTextField txtWatcherRename;
    // End of variables declaration//GEN-END:variables

    private void loadWatcher(Watcher w) {
        if (modelManga == null) {
            modelManga = new WatcherMangaTreeTableModel(ttblList);
            modelManga.initFromWatcher(w);
            ttblList.setTreeTableModel(modelManga);
            initColumnModelForTreeTable();
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
            panelDownload.addChapter(c);
        }
    }

    private void addMangasToWatcher(Watcher watcher) {
        if (watcher != null) {
            if (dlgSearchManga == null) {
                dlgSearchManga = new SearchMangaDialog(this, true);
            }
            dlgSearchManga.clearSelection();
            dlgSearchManga.setWatcher(watcher);
            dlgSearchManga.setVisible(true);
            System.out.println("Number of Mangas: " + watcher.getMangaCount());
            if (watcher == currentWatcher) {
                loadWatcher(currentWatcher);
            }
        }
    }

    private void loadAllChapterInWatcher(final Watcher w) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                w.loadChapers();
            }
        }).start();
    }

    private void forceLoadAllChapterInWatcher(final Watcher w) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                w.forceLoadChapters();
            }
        }).start();
    }

    private void forceLoadAllChapterInWatcher(final Watcher[] arrWatchers) {
        if (arrWatchers != null && arrWatchers.length != 0) {
            MultitaskJob.doTask(new ArrayList<Callable<Boolean>>() {
                {
                    for (final Watcher w : arrWatchers) {
                        add(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                forceLoadAllChapterInWatcher(w);
                                return true;
                            }
                        });
                    }
                }
            });
        }
    }

    private void loadAllChapterInWatcher(final Watcher[] arrWatchers) {
        if (arrWatchers != null && arrWatchers.length != 0) {
            MultitaskJob.doTask(MultitaskJob.PRIOVITY_NORMAL, new ArrayList<Callable<Object>>() {
                {
                    for (final Watcher w : arrWatchers) {
                        add(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                System.out.println("Loading watcher: " + w.getName());
                                loadAllChapterInWatcher(w);
                                return null;
                            }
                        });
                    }
                }
            });
        }
    }

    private void loadAllWatchers() {
        List<Watcher> lstTemp = Database.getAllWatcher();
        modelWatcher.addWatchers(lstTemp);
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
        w.setName(newName);
        Database.editWatcher(w);
        modelWatcher.fireTableDataChanged();
        System.out.println("Renamed to " + w.getName());
    }

    private void viewOnBrowser(String url) {
        if (!GUIUtilities.openLink(url)) {
            GUIUtilities.showError(this, "Your OS not support browse action!");
        }
    }

    private void deleteWatchers(Watcher[] arrWatcher) {
        for (int i = 0; i < arrWatcher.length; i++) {
            Database.removeWatcher(arrWatcher[i].getId());
            modelWatcher.removeWatcher(arrWatcher[i]);
        }
        modelWatcher.fireTableDataChanged();
    }

    private void addMangasManuallyToWatcher(Watcher watcher) {
        if (watcher != null) {
            if (dlgAddManga == null) {
                dlgAddManga = new AddMangaDialog(this, true);
            }
            dlgAddManga.clearSelection();
            dlgAddManga.setWatcher(watcher);
            dlgAddManga.setVisible(true);
            System.out.println("Number of Mangas: " + watcher.getMangaCount());
            if (watcher == currentWatcher) {
                loadWatcher(currentWatcher);
            }
        }
    }
}