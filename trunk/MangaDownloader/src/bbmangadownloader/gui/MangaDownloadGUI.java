/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.bus.exception.HtmlParsingException;
import comichtmlgender.HTMLGenerator;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.faces.FacadeManager;
import bbmangadownloader.gui.bus.ListTaskDownloader;
import bbmangadownloader.gui.model.ChapterDownloadModel;
import bbmangadownloader.gui.model.ChapterModel;
import bbmangadownloader.gui.model.MyColumnSorter;
import bbmangadownloader.gui.model.MyTableModelSortable;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.HttpDownloadManager;
import java.io.IOException;

/**
 *
 * @author Bach
 */
public class MangaDownloadGUI extends javax.swing.JFrame {

    /**
     * Creates new form MangaDownloader
     */
    private ChapterModel modelChapter;
    private ChapterDownloadModel modelDownload;
    private IFacadeMangaServer mangaServer;
    private ListTaskDownloader downloadThread;

    public MangaDownloadGUI() {
        initComponents();
        init();

        setTitle("BB Managa Downloader");
    }

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
            ((AbstractTableModel) model).fireTableStructureChanged();
        }
    }

    private void init() {
        HttpDownloadManager.init();

        this.modelChapter = new ChapterModel();
        tblChapters.setModel(modelChapter);

        this.modelDownload = new ChapterDownloadModel();
        tblDownload.setModel(modelDownload);

        //<editor-fold>
        tblChapters.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblChapters.getColumnModel().getColumn(0).setMaxWidth(100);
        tblChapters.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblChapters.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblChapters.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblChapters.getColumnModel().getColumn(4).setPreferredWidth(350);
        tblChapters.setAutoCreateColumnsFromModel(false);
        //</editor-fold>

        //<editor-fold>
        tblDownload.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblDownload.getColumnModel().getColumn(0).setMaxWidth(100);
        tblDownload.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblDownload.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDownload.getColumnModel().getColumn(2).setMaxWidth(100);
        tblDownload.getColumnModel().getColumn(3).setPreferredWidth(350);
        tblDownload.setAutoCreateColumnsFromModel(false);
        //</editor-fold>

        addHeaderListener(tblChapters);
        addHeaderListener(tblDownload);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        pnlTop = new javax.swing.JPanel();
        btnFletch = new javax.swing.JButton();
        btnDownload = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        sclChapter = new javax.swing.JScrollPane();
        tblChapters = new javax.swing.JTable();
        sclDownload = new javax.swing.JScrollPane();
        tblDownload = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lblSupport = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMangaUrl = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMangaName = new javax.swing.JTextField();
        btnCheckSupport = new javax.swing.JButton();
        pnlBot = new javax.swing.JPanel();
        lblOutput = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnFletch.setText("Fletch");
        btnFletch.setEnabled(false);
        btnFletch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFletchActionPerformed(evt);
            }
        });

        btnDownload.setText("Download");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });

        jLabel3.setText("Download");

        tblChapters.setModel(new javax.swing.table.DefaultTableModel(
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
        tblChapters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblChaptersMouseReleased(evt);
            }
        });
        sclChapter.setViewportView(tblChapters);

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
        sclDownload.setViewportView(tblDownload);

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Remove");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblSupport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSupport.setForeground(new java.awt.Color(255, 0, 0));
        lblSupport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupport.setText("Support");
        lblSupport.setToolTipText("");

        jLabel1.setText("Manga URL");

        jLabel2.setText("Chapters");

        txtMangaUrl.setText("http://truyentranhtuan.com/naruto/");
        txtMangaUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMangaUrlActionPerformed(evt);
            }
        });

        jLabel5.setText("Manga");

        txtMangaName.setText("Naruto");

        btnCheckSupport.setText("Check");
        btnCheckSupport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSupportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sclChapter)
                            .addComponent(sclDownload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnDownload, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFletch, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addComponent(txtMangaName)
                        .addGap(128, 128, 128))
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addComponent(txtMangaUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckSupport, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSupport, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMangaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMangaUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckSupport)
                    .addComponent(jLabel1)
                    .addComponent(lblSupport))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(btnFletch))
                        .addGap(318, 318, 318)
                        .addComponent(jButton2))
                    .addComponent(sclChapter, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDownload))
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(sclDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)))
        );

        lblOutput.setText(".");
        lblOutput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlBotLayout = new javax.swing.GroupLayout(pnlBot);
        pnlBot.setLayout(pnlBotLayout);
        pnlBotLayout.setHorizontalGroup(
            pnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(pnlBotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBotLayout.setVerticalGroup(
            pnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

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

        jMenuBar1.add(jMenu7);

        jMenu2.setText("Help");

        jMenuItem2.setText("About us");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFletchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFletchActionPerformed

        new Thread(new Runnable() {

            @Override
            public void run() {
                modelChapter.clear();
                Server s = new Server(mangaServer.clone());
                Manga manga = new Manga(s, txtMangaName.getText(), txtMangaUrl.getText());
                List<Chapter> lstChapter;
                try {
                    lstChapter = mangaServer.getAllChapters(manga);

                    modelChapter.addChapters(lstChapter);
                    modelChapter.fireTableDataChanged();

                    System.out.println("Loaded: " + lstChapter.size());

                    tblChapters.setEnabled(true);
                    tblDownload.setEnabled(true);
                } catch (Exception ex) {
                    Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
                    GUIUtilities.showError(null, ex.getMessage());
                }
            }
        }).start();

    }//GEN-LAST:event_btnFletchActionPerformed

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed

        if (downloadThread == null) {
            downloadThread = new ListTaskDownloader(modelDownload.getListDownload(), modelDownload);
        }
        if (!downloadThread.isRunning()) {
            new Thread(downloadThread).start();
        } else {
            GUIUtilities.showDialog(this, "Already downloading!");
        }

//        GUIUtil.showDialog(this, "Running in the background");

    }//GEN-LAST:event_btnDownloadActionPerformed

    private void tblChaptersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChaptersMouseReleased

        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() >= 2) {
            int[] selectedRows = tblChapters.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                Chapter c = modelChapter.getChapterAt(selectedRows[i]);
                modelDownload.addChapter(c);
            }
        }
    }//GEN-LAST:event_tblChaptersMouseReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int[] selectedRows = tblDownload.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            DownloadTask t = modelDownload.getTaskAt(selectedRows[i]);
            modelDownload.removeTask(t);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int[] selectedRows = tblChapters.getSelectedRows();



        for (int i = 0; i < selectedRows.length; i++) {
            Chapter c = modelChapter.getChapterAt(selectedRows[i]);
            modelChapter.fireTableDataChanged();
            modelDownload.addChapter(c);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtMangaUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMangaUrlActionPerformed
        doCheckSupport();
    }//GEN-LAST:event_txtMangaUrlActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        comichtmlgender.HTMLGenerator dialog = new HTMLGenerator(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnCheckSupportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSupportActionPerformed
        doCheckSupport();
    }//GEN-LAST:event_btnCheckSupportActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AboutUsDiaglog(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ConfigurationDialog form = new ConfigurationDialog(this, true);
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckSupport;
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnFletch;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lblOutput;
    private javax.swing.JLabel lblSupport;
    private javax.swing.JPanel pnlBot;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JScrollPane sclChapter;
    private javax.swing.JScrollPane sclDownload;
    private javax.swing.JTable tblChapters;
    private javax.swing.JTable tblDownload;
    private javax.swing.JTextField txtMangaName;
    private javax.swing.JTextField txtMangaUrl;
    // End of variables declaration//GEN-END:variables

    private void doCheckSupport() {
        String url = txtMangaUrl.getText();

        mangaServer = FacadeManager.getServerFacadeByUrl(url);

        modelChapter.clear();

        if (mangaServer != null) {
            btnFletch.setEnabled(true);
            lblSupport.setText(mangaServer.getSupportType().toString());
            btnFletchActionPerformed(null);
        } else {
            btnFletch.setEnabled(false);
            lblSupport.setText("Not Support");
            GUIUtilities.showDialog(this, "Host not supported !");
        }
    }
}
