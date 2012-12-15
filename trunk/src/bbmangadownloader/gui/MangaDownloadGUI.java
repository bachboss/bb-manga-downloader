/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.bus.model.data.DownloadTask;
import bbmangadownloader.bus.model.data.DownloadTask.DownloadTaskStatus;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.FacadeManager;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.gui.bus.ListTaskDownloader;
import bbmangadownloader.gui.model.*;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.manager.HttpDownloadManager;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

/**
 *
 * @author Bach
 */
public class MangaDownloadGUI extends javax.swing.JFrame {

    private ChapterModel modelChapter;
    private ChapterDownloadModel modelDownload;
    private IFacadeMangaServer mangaServer;
    private ListTaskDownloader downloadThread;
    private ListComboBoxModel<Manga> modelManga;
    private ScannerComboboxModel modelScanner;
    private Server lastScannerServer;
    private Manga lastScannerManga;

    public MangaDownloadGUI() {
        initScanerTab();
        initComponents();
        init();
        lblLoading.setVisible(false);
        lblScannerLoading.setVisible(false);
        setTitle("BB Managa Downloader");
        setIconImage(bbmangadownloader.BBMangaDownloader.getApplicationIcon());
        initPopup();
        initDecorator();
        tblDownload.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initDecorator() {
        AutoCompleteDecorator.decorate(cbxScanner, new ObjectToStringConverter() {

            @Override
            public String getPreferredStringForItem(Object item) {
                return item == null ? null : item.toString();
            }
        });

        AutoCompleteDecorator.decorate(cbxManga, new ObjectToStringConverter() {

            @Override
            public String getPreferredStringForItem(Object item) {
                return item == null ? null : item.toString();
            }
        });

//        AutoCompleteDecorator.decorate(false);
    }

    private void initScanerTab() {
        modelScanner = new ScannerComboboxModel();
        modelManga = new ListComboBoxModel<Manga>(Collections.<Manga>emptyList());
    }

    private void initPopup() {
        tblChapters.setComponentPopupMenu(popChapters);
        tblChapters.setInheritsPopupMenu(true);

        tblDownload.setComponentPopupMenu(popDownload);
        tblDownload.setInheritsPopupMenu(true);
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
        tblChapters.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblChapters.setAutoCreateColumnsFromModel(false);
        //</editor-fold>

        //<editor-fold>
        tblDownload.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblDownload.getColumnModel().getColumn(0).setMaxWidth(100);
        tblDownload.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblDownload.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDownload.getColumnModel().getColumn(2).setMaxWidth(100);
        tblDownload.getColumnModel().getColumn(3).setPreferredWidth(150);
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
        popChapters = new javax.swing.JPopupMenu();
        mnChaptersAddToDownload = new javax.swing.JMenuItem();
        mnChapterViewInBroser = new javax.swing.JMenuItem();
        popDownload = new javax.swing.JPopupMenu();
        mnDownloadStart = new javax.swing.JMenuItem();
        mnDownloadRemove = new javax.swing.JMenuItem();
        mnDownloadViewInBrowser = new javax.swing.JMenuItem();
        mnDownloadViewFolder = new javax.swing.JMenuItem();
        pnlTop = new javax.swing.JPanel();
        pnlDownloadInformation = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlServer = new javax.swing.JPanel();
        txtMangaName = new javax.swing.JTextField();
        btnCheckSupport = new javax.swing.JButton();
        lblSupport = new javax.swing.JLabel();
        txtMangaUrl = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblLoading = new org.jdesktop.swingx.JXBusyLabel();
        jLabel7 = new javax.swing.JLabel();
        pnlScaner = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbxScanner = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        btnScanerFletch = new javax.swing.JButton();
        cbxManga = new javax.swing.JComboBox();
        lblScannerLoading = new org.jdesktop.swingx.JXBusyLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnlChapters = new javax.swing.JPanel();
        sclChapter = new javax.swing.JScrollPane();
        tblChapters = new javax.swing.JTable();
        pnlDownload = new javax.swing.JPanel();
        sclDownload = new javax.swing.JScrollPane();
        tblDownload = new javax.swing.JTable();
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

        mnChaptersAddToDownload.setLabel("Add to Download");
        mnChaptersAddToDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChaptersAddToDownloadActionPerformed(evt);
            }
        });
        popChapters.add(mnChaptersAddToDownload);

        mnChapterViewInBroser.setText("View In Browser");
        mnChapterViewInBroser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChapterViewInBroserActionPerformed(evt);
            }
        });
        popChapters.add(mnChapterViewInBroser);

        mnDownloadStart.setLabel("Start All Download");
        mnDownloadStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadStartActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadStart);

        mnDownloadRemove.setLabel("Remove");
        mnDownloadRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadRemoveActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadRemove);

        mnDownloadViewInBrowser.setText("View In Browser");
        mnDownloadViewInBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadViewInBrowserActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadViewInBrowser);

        mnDownloadViewFolder.setText("View Directory");
        mnDownloadViewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDownloadViewFolderActionPerformed(evt);
            }
        });
        popDownload.add(mnDownloadViewFolder);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlTop.setMinimumSize(new java.awt.Dimension(0, 0));

        pnlServer.setPreferredSize(new java.awt.Dimension(32307, 98));

        txtMangaName.setText("Naruto");

        btnCheckSupport.setText("Fletch");
        btnCheckSupport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSupportActionPerformed(evt);
            }
        });

        lblSupport.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblSupport.setForeground(new java.awt.Color(255, 0, 0));
        lblSupport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupport.setText("Support");
        lblSupport.setToolTipText("");
        lblSupport.setMaximumSize(new java.awt.Dimension(125, 15));
        lblSupport.setMinimumSize(new java.awt.Dimension(125, 15));
        lblSupport.setPreferredSize(new java.awt.Dimension(125, 15));

        txtMangaUrl.setText("http://truyentranhtuan.com/naruto/");
        txtMangaUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMangaUrlActionPerformed(evt);
            }
        });

        jLabel5.setText("Manga");

        lblLoading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoading.setText("Loading");
        lblLoading.setBusy(true);
        lblLoading.setDirection(org.jdesktop.swingx.painter.BusyPainter.Direction.RIGHT);

        jLabel7.setText("Manga URL");

        javax.swing.GroupLayout pnlServerLayout = new javax.swing.GroupLayout(pnlServer);
        pnlServer.setLayout(pnlServerLayout);
        pnlServerLayout.setHorizontalGroup(
            pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlServerLayout.createSequentialGroup()
                        .addComponent(txtMangaName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckSupport))
                    .addComponent(txtMangaUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSupport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlServerLayout.setVerticalGroup(
            pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSupport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMangaUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(5, 5, 5)
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMangaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckSupport)
                    .addComponent(jLabel5)
                    .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Manga Sites", pnlServer);

        jLabel4.setText("Scanner");
        jLabel4.setMaximumSize(new java.awt.Dimension(60, 14));
        jLabel4.setMinimumSize(new java.awt.Dimension(60, 14));
        jLabel4.setPreferredSize(new java.awt.Dimension(60, 14));

        cbxScanner.setModel(modelScanner);
        cbxScanner.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxScannerItemStateChanged(evt);
            }
        });

        jLabel6.setText("Manga");
        jLabel6.setMaximumSize(new java.awt.Dimension(60, 14));
        jLabel6.setMinimumSize(new java.awt.Dimension(60, 14));
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 14));

        btnScanerFletch.setText("Fletch");
        btnScanerFletch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanerFletchActionPerformed(evt);
            }
        });

        cbxManga.setModel(modelManga);
        cbxManga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMangaActionPerformed(evt);
            }
        });

        lblScannerLoading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblScannerLoading.setText("Loading");
        lblScannerLoading.setBusy(true);
        lblScannerLoading.setDirection(org.jdesktop.swingx.painter.BusyPainter.Direction.RIGHT);
        lblScannerLoading.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout pnlScanerLayout = new javax.swing.GroupLayout(pnlScaner);
        pnlScaner.setLayout(pnlScanerLayout);
        pnlScanerLayout.setHorizontalGroup(
            pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScanerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxManga, 0, 508, Short.MAX_VALUE)
                    .addComponent(cbxScanner, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnScanerFletch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScannerLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlScanerLayout.setVerticalGroup(
            pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScanerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnScanerFletch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlScanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxManga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblScannerLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Scanner Sites", pnlScaner);

        javax.swing.GroupLayout pnlDownloadInformationLayout = new javax.swing.GroupLayout(pnlDownloadInformation);
        pnlDownloadInformation.setLayout(pnlDownloadInformationLayout);
        pnlDownloadInformationLayout.setHorizontalGroup(
            pnlDownloadInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDownloadInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDownloadInformationLayout.setVerticalGroup(
            pnlDownloadInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDownloadInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Scanner");

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(150);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setMinimumSize(new java.awt.Dimension(0, 0));
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setPreferredSize(new java.awt.Dimension(725, 305));

        pnlChapters.setBorder(javax.swing.BorderFactory.createTitledBorder("Chapters"));
        pnlChapters.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlChapters.setPreferredSize(new java.awt.Dimension(700, 150));

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
        tblChapters.setMinimumSize(new java.awt.Dimension(0, 0));
        tblChapters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblChaptersMouseReleased(evt);
            }
        });
        sclChapter.setViewportView(tblChapters);

        javax.swing.GroupLayout pnlChaptersLayout = new javax.swing.GroupLayout(pnlChapters);
        pnlChapters.setLayout(pnlChaptersLayout);
        pnlChaptersLayout.setHorizontalGroup(
            pnlChaptersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sclChapter, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        pnlChaptersLayout.setVerticalGroup(
            pnlChaptersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sclChapter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );

        jSplitPane2.setTopComponent(pnlChapters);

        pnlDownload.setBorder(javax.swing.BorderFactory.createTitledBorder("Download"));
        pnlDownload.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlDownload.setPreferredSize(new java.awt.Dimension(700, 150));

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

        javax.swing.GroupLayout pnlDownloadLayout = new javax.swing.GroupLayout(pnlDownload);
        pnlDownload.setLayout(pnlDownloadLayout);
        pnlDownloadLayout.setHorizontalGroup(
            pnlDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sclDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        pnlDownloadLayout.setVerticalGroup(
            pnlDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sclDownload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(pnlDownload);

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlDownloadInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addComponent(pnlDownloadInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
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
                .addComponent(lblOutput))
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
            .addComponent(pnlBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void tblChaptersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChaptersMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() >= 2) {
            int[] selectedRows = tblChapters.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                Chapter c = modelChapter.getChapterAt(selectedRows[i]);
                modelDownload.addChapter(c);
            }
        }
    }//GEN-LAST:event_tblChaptersMouseReleased

    private void txtMangaUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMangaUrlActionPerformed
        doCheckSupport();
    }//GEN-LAST:event_txtMangaUrlActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GenerateHtmlDialog dialog = new GenerateHtmlDialog(this, true);
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

    private void mnChapterViewInBroserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChapterViewInBroserActionPerformed
        int row = tblChapters.getSelectedRow();
        Chapter c = modelChapter.getChapterAt(row);
        if (c != null) {
            GUIUtilities.openLink(c.getUrl());
        }
    }//GEN-LAST:event_mnChapterViewInBroserActionPerformed

    private void mnChaptersAddToDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChaptersAddToDownloadActionPerformed
        doAddToDownload();
    }//GEN-LAST:event_mnChaptersAddToDownloadActionPerformed

    private void mnDownloadRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadRemoveActionPerformed
        doRemoveFromDownload();
    }//GEN-LAST:event_mnDownloadRemoveActionPerformed

    private void mnDownloadViewInBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadViewInBrowserActionPerformed
        int row = tblDownload.getSelectedRow();
        Chapter c = modelDownload.getTaskAt(row).getChapter();
        if (c != null) {
            GUIUtilities.openLink(c.getUrl());
        }
    }//GEN-LAST:event_mnDownloadViewInBrowserActionPerformed

    private void mnDownloadStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadStartActionPerformed
        doStartAllDownload();
    }//GEN-LAST:event_mnDownloadStartActionPerformed

    private void btnScanerFletchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanerFletchActionPerformed
        doLoadScanner();
    }//GEN-LAST:event_btnScanerFletchActionPerformed

    private void cbxMangaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMangaActionPerformed
        doLoadManga();
    }//GEN-LAST:event_cbxMangaActionPerformed

    private void cbxScannerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxScannerItemStateChanged
        if ((cbxScanner.getSelectedItem()) != lastScannerServer) {
            modelManga = new ListComboBoxModel<Manga>(Collections.<Manga>emptyList());
            cbxManga.setModel(modelManga);
        }
    }//GEN-LAST:event_cbxScannerItemStateChanged

    private void mnDownloadViewFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDownloadViewFolderActionPerformed
        doOpenDownloadFolder();
    }//GEN-LAST:event_mnDownloadViewFolderActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckSupport;
    private javax.swing.JButton btnScanerFletch;
    private javax.swing.JComboBox cbxManga;
    private javax.swing.JComboBox cbxScanner;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXBusyLabel lblLoading;
    public static javax.swing.JLabel lblOutput;
    private org.jdesktop.swingx.JXBusyLabel lblScannerLoading;
    private javax.swing.JLabel lblSupport;
    private javax.swing.JMenuItem mnChapterViewInBroser;
    private javax.swing.JMenuItem mnChaptersAddToDownload;
    private javax.swing.JMenuItem mnDownloadRemove;
    private javax.swing.JMenuItem mnDownloadStart;
    private javax.swing.JMenuItem mnDownloadViewFolder;
    private javax.swing.JMenuItem mnDownloadViewInBrowser;
    private javax.swing.JPanel pnlBot;
    private javax.swing.JPanel pnlChapters;
    private javax.swing.JPanel pnlDownload;
    private javax.swing.JPanel pnlDownloadInformation;
    private javax.swing.JPanel pnlScaner;
    private javax.swing.JPanel pnlServer;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPopupMenu popChapters;
    private javax.swing.JPopupMenu popDownload;
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
            lblSupport.setText(mangaServer.getSupportType().toString());
            doFletch();
        } else {
            lblSupport.setText("Not Support");
            GUIUtilities.showDialog(this, "Host not supported !");
        }
    }

    private void doFletch() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                lblLoading.setVisible(true);
                modelChapter.clear();
                try {

                    Server s = new Server(mangaServer.clone());
                    Manga manga = new Manga(s, txtMangaName.getText(), txtMangaUrl.getText());
                    List<Chapter> lstChapter;

                    lstChapter = mangaServer.getAllChapters(manga);

                    modelChapter.addChapters(lstChapter);
                    modelChapter.fireTableDataChanged();

                    System.out.println("Loaded: " + lstChapter.size());
                    if (lstChapter == null || lstChapter.isEmpty()) {
                        GUIUtilities.showLog("Loaded: 0 record(s) !");
                        GUIUtilities.showDialog(null, "No record found !");
                    }

                    tblChapters.setEnabled(true);
                    tblDownload.setEnabled(true);
                } catch (Exception ex) {
                    Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
                    GUIUtilities.showException(null, ex);
                }
                lblLoading.setVisible(false);
            }
        }).start();
    }

    private void doAddToDownload() {
        int[] selectedRows = tblChapters.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            Chapter c = modelChapter.getChapterAt(selectedRows[i]);
            modelChapter.fireTableDataChanged();
            modelDownload.addChapter(c);
        }
    }

    private void doStartAllDownload() {
        if (downloadThread == null) {
            downloadThread = new ListTaskDownloader(modelDownload.getListDownload(), modelDownload);
        }
        if (!downloadThread.isRunning()) {
            new Thread(downloadThread).start();
        } else {
            GUIUtilities.showDialog(this, "Already downloading!");
        }

//        GUIUtil.showDialog(this, "Downloading in the background");
    }

    private void doRemoveFromDownload() {
        int[] selectedRows = tblDownload.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            DownloadTask t = modelDownload.getTaskAt(selectedRows[i]);
            modelDownload.removeTask(t);
        }
    }

    private void doLoadScanner() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                lblScannerLoading.setVisible(true);
                try {
                    Server scanner = (Server) cbxScanner.getSelectedItem();
                    if (scanner != lastScannerServer) {
                        lastScannerServer = scanner;
                        List<Manga> lstManga = scanner.getMangaServer().getAllMangas(scanner);
                        modelManga = new ListComboBoxModel<Manga>(lstManga);
                        cbxManga.setModel(modelManga);
                    }
                } catch (Exception ex) {
                    GUIUtilities.showException(null, ex);
                    Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                lblScannerLoading.setVisible(false);
            }
        }).start();
    }

    private void doLoadManga() {
        Manga m = ((Manga) cbxManga.getSelectedItem());
        if (m != lastScannerManga) {
            lastScannerManga = m;
            if (m != null) {
                List<Chapter> lstChapter = m.getListChapter();
                modelChapter.clear();
                modelChapter.addChapters(lstChapter);
                modelChapter.fireTableDataChanged();
            }
        }
    }

    private void doOpenDownloadFolder() {
        DownloadTask task = modelDownload.getTaskAt(tblDownload.getSelectedRow());
        DownloadTaskStatus s = task.getStatusEnum();
        if (s == DownloadTaskStatus.Done || s == DownloadTask.DownloadTaskStatus.Downloading) {
            GUIUtilities.openFile(task.getDownloadTo());
        }
    }
}