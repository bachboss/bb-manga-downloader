/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.FacadeManager;
import bbmangadownloader.faces.IFacadeMangaServer;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.gui.HelpDialog.IHelpListener;
import bbmangadownloader.gui.model.*;
import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.ult.GUIUtilities;
import bbmangadownloader.ult.MySwingUtilities;
import bbmangadownloader.ult.OSSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

/**
 *
 * @author Bach
 */
public class MangaDownloadGUI extends javax.swing.JFrame implements IHelpListener, IMangaInterface {

    private ChapterModel modelChapter;
    private IFacadeMangaServer mangaServer;
    private ListComboBoxModel<Manga> modelManga;
    private DefaultComboBoxModel<Server> modelScanner;
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

        ConfigManager config = ConfigManager.getCurrentInstance();
        String mName = config.getLastDownloadMangaName();
        String mUrl = config.getLastDownloadMangaUrl();
        txtMangaName.setText(mName);
        txtMangaUrl.setText(mUrl);
        fixMacOSLayout();
    }

    private void fixMacOSLayout() {
        if (OSSupport.getOS() == OSSupport.OS.MAC_OS) {
            GroupLayout pnlDownloadInformationLayout = (GroupLayout) pnlDownloadInformation.getLayout();
            pnlDownloadInformationLayout.setVerticalGroup(
                    pnlDownloadInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tpnlController, javax.swing.GroupLayout.PREFERRED_SIZE, 120, Short.MAX_VALUE));
        }
    }

//     <editor-fold defaultstate="collapsed" desc="Initation">                          
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
        modelScanner = new DefaultComboBoxModel<Server>(
                ServerManager.getMapScannerClone().values().toArray(new Server[0]));

        modelManga = new ListComboBoxModel<Manga>(Collections.<Manga>emptyList());
    }

    private void initPopup() {
        tblChapters.setComponentPopupMenu(popChapters);
        tblChapters.setInheritsPopupMenu(true);
    }

    private void init() {
        this.modelChapter = new ChapterModel();
        tblChapters.setModel(modelChapter);

        //<editor-fold>
        tblChapters.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblChapters.getColumnModel().getColumn(0).setMaxWidth(100);
        tblChapters.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblChapters.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblChapters.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblChapters.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblChapters.setAutoCreateColumnsFromModel(false);
        //</editor-fold>

    }
    //</editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        popChapters = new javax.swing.JPopupMenu();
        mnChaptersAddToDownload = new javax.swing.JMenuItem();
        mnChapterViewInBroser = new javax.swing.JMenuItem();
        pnlTop = new javax.swing.JPanel();
        pnlDownloadInformation = new javax.swing.JPanel();
        tpnlController = new javax.swing.JTabbedPane();
        pnlServer = new javax.swing.JPanel();
        txtMangaName = new javax.swing.JTextField();
        btnCheckSupport = new javax.swing.JButton();
        lblSupport = new javax.swing.JLabel();
        txtMangaUrl = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblLoading = new org.jdesktop.swingx.JXBusyLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlScaner = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbxScanner = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        btnScanerFletch = new javax.swing.JButton();
        cbxManga = new javax.swing.JComboBox();
        lblScannerLoading = new org.jdesktop.swingx.JXBusyLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnlChapters = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChapters = new org.jdesktop.swingx.JXTable();
        pnlDownload = new javax.swing.JPanel();
        pnlTaskDownload = new bbmangadownloader.gui.control.PanelDownload();
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
        jMenuItem5 = new javax.swing.JMenuItem();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlTop.setMinimumSize(new java.awt.Dimension(0, 0));

        pnlServer.setPreferredSize(new java.awt.Dimension(32307, 100));
        pnlServer.setRequestFocusEnabled(false);

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

        jLabel1.setForeground(new java.awt.Color(11, 22, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html><u>Help?</u></html>");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlServerLayout = new javax.swing.GroupLayout(pnlServer);
        pnlServer.setLayout(pnlServerLayout);
        pnlServerLayout.setHorizontalGroup(
            pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMangaUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(txtMangaName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCheckSupport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
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
                    .addComponent(lblSupport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMangaUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(pnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMangaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckSupport)
                    .addComponent(jLabel5)
                    .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tpnlController.addTab("Directory Sites", pnlServer);

        pnlScaner.setPreferredSize(new java.awt.Dimension(32307, 100));
        pnlScaner.setRequestFocusEnabled(false);

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
        cbxScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxScannerActionPerformed(evt);
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
                    .addComponent(cbxManga, 0, 520, Short.MAX_VALUE)
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

        tpnlController.addTab("Others", pnlScaner);

        javax.swing.GroupLayout pnlDownloadInformationLayout = new javax.swing.GroupLayout(pnlDownloadInformation);
        pnlDownloadInformation.setLayout(pnlDownloadInformationLayout);
        pnlDownloadInformationLayout.setHorizontalGroup(
            pnlDownloadInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnlController, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlDownloadInformationLayout.setVerticalGroup(
            pnlDownloadInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnlController, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
        );

        tpnlController.getAccessibleContext().setAccessibleName("Scanner");

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
        tblChapters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblChaptersMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblChapters);

        javax.swing.GroupLayout pnlChaptersLayout = new javax.swing.GroupLayout(pnlChapters);
        pnlChapters.setLayout(pnlChaptersLayout);
        pnlChaptersLayout.setHorizontalGroup(
            pnlChaptersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        pnlChaptersLayout.setVerticalGroup(
            pnlChaptersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );

        jSplitPane2.setTopComponent(pnlChapters);

        pnlDownload.setBorder(javax.swing.BorderFactory.createTitledBorder("Download"));
        pnlDownload.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlDownload.setPreferredSize(new java.awt.Dimension(700, 150));

        javax.swing.GroupLayout pnlDownloadLayout = new javax.swing.GroupLayout(pnlDownload);
        pnlDownload.setLayout(pnlDownloadLayout);
        pnlDownloadLayout.setHorizontalGroup(
            pnlDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTaskDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        pnlDownloadLayout.setVerticalGroup(
            pnlDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTaskDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
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
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
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

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.CTRL_MASK));
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

        jMenuItem5.setText("Check for updates");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

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

    private void txtMangaUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMangaUrlActionPerformed
        doCheckSupport();
    }//GEN-LAST:event_txtMangaUrlActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GenerateHtmlDialog dialog = new GenerateHtmlDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnCheckSupportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSupportActionPerformed
        doNormalizeUrl();
        doCheckSupport();
        saveLastDownload();
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
        Chapter c = MySwingUtilities.<Chapter>getSelectedObject(tblChapters);
        if (c != null) {
            GUIUtilities.openLink(c.getUrl());
        }
    }//GEN-LAST:event_mnChapterViewInBroserActionPerformed

    private void mnChaptersAddToDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChaptersAddToDownloadActionPerformed
        doAddToDownload();
    }//GEN-LAST:event_mnChaptersAddToDownloadActionPerformed

    private void btnScanerFletchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanerFletchActionPerformed
        doLoadScanner();
    }//GEN-LAST:event_btnScanerFletchActionPerformed

    private void cbxMangaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMangaActionPerformed
        doLoadManga();
    }//GEN-LAST:event_cbxMangaActionPerformed

    private void cbxScannerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxScannerItemStateChanged
//        Object selectedItem = (cbxScanner.getSelectedItem());
//        System.out.println("Selected Item: " + selectedItem);
//        if (selectedItem != lastScannerServer) {
//            modelManga = new ListComboBoxModel<Manga>(Collections.<Manga>emptyList());
//            cbxManga.setModel(modelManga);
//            cbxManga.setSelectedItem(selectedItem);
//        }
    }//GEN-LAST:event_cbxScannerItemStateChanged

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        doShowHelp();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void cbxScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxScannerActionPerformed
        Object selectedItem = (cbxScanner.getSelectedItem());
//        System.out.println("Selected Item: " + selectedItem);
        if (selectedItem != lastScannerServer) {
            modelManga = new ListComboBoxModel<Manga>(Collections.<Manga>emptyList());
            cbxManga.setModel(modelManga);
//            cbxManga.setSelectedItem(selectedItem);
        }
    }//GEN-LAST:event_cbxScannerActionPerformed
    private UpdateDialog updateDialog;
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (updateDialog == null) {
            updateDialog = new UpdateDialog(this, false);
        }
        updateDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void tblChaptersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChaptersMouseReleased
        if (MySwingUtilities.isDoubleLeftClick(evt)) {
            doAddToDownload();
        }
    }//GEN-LAST:event_tblChaptersMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckSupport;
    private javax.swing.JButton btnScanerFletch;
    private javax.swing.JComboBox cbxManga;
    private javax.swing.JComboBox cbxScanner;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane2;
    private org.jdesktop.swingx.JXBusyLabel lblLoading;
    public static javax.swing.JLabel lblOutput;
    private org.jdesktop.swingx.JXBusyLabel lblScannerLoading;
    private javax.swing.JLabel lblSupport;
    private javax.swing.JMenuItem mnChapterViewInBroser;
    private javax.swing.JMenuItem mnChaptersAddToDownload;
    private javax.swing.JPanel pnlBot;
    private javax.swing.JPanel pnlChapters;
    private javax.swing.JPanel pnlDownload;
    private javax.swing.JPanel pnlDownloadInformation;
    private javax.swing.JPanel pnlScaner;
    private javax.swing.JPanel pnlServer;
    private bbmangadownloader.gui.control.PanelDownload pnlTaskDownload;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPopupMenu popChapters;
    private org.jdesktop.swingx.JXTable tblChapters;
    private javax.swing.JTabbedPane tpnlController;
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

                    if (lstChapter != null && !lstChapter.isEmpty()) {
                        System.out.println("Loaded: " + lstChapter.size());
                        tblChapters.setEnabled(true);
                        modelChapter.addChapters(lstChapter);
                        modelChapter.fireTableDataChanged();
//                    tblDownload.setEnabled(true);
                    } else {
                        GUIUtilities.showDialog(null, "No record found !");
                    }

                } catch (Throwable ex) {
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
            Chapter c = (Chapter) tblChapters.getValueAt(selectedRows[i], -1);
            modelChapter.fireTableDataChanged();
            addDownloadChapter(c);
        }
    }

    private void addDownloadChapter(Chapter c) {
        pnlTaskDownload.addChapter(c);
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
    private HelpDialog helpDialog;

    private void doShowHelp() {
        if (helpDialog == null) {
            helpDialog = new HelpDialog(this, false);
            helpDialog.setListener(this);
        }
        helpDialog.setVisible(true);
    }

    @Override
    public void doAddSampleUrl(String url) {
        txtMangaUrl.setText(url);
        helpDialog.setVisible(false);
    }

    private void doNormalizeUrl() {
        String url = txtMangaUrl.getText();
        if (url.indexOf(':') == -1) {
            url = "http://" + url;
        }
        txtMangaUrl.setText(url);
    }

    @Override
    public void addUrl(String url) {
        Server s = ServerManager.getServerByUrl(url);
        IFacadeMangaServer iMangaServer = s.getMangaServer();
        switch (iMangaServer.getUrlType(url)) {
            case Chapter: {
                Chapter c = iMangaServer.getChapter(url, true);
                addDownloadChapter(c);
                break;
            }
            case Manga: {
                System.out.println("Not Supported !");
                break;
            }
            default: {
                System.out.println("Unknow !");
                break;
            }
        }
    }

    private void saveLastDownload() {
        try {
            ConfigManager config = ConfigManager.getCurrentInstance();
            config.setLastDownloadMangaName(txtMangaName.getText());
            config.setLastDownloadMangaUrl(txtMangaUrl.getText());
            config.save();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}