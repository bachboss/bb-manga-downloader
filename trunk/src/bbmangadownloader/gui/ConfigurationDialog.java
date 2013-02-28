/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.manager.ConfigManager;
import bbmangadownloader.manager.FileManager;
import bbmangadownloader.manager.HttpDownloadManager;
import bbmangadownloader.ult.GUIUtilities;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class ConfigurationDialog extends javax.swing.JDialog {

    /**
     * Creates new form ConfigurationDialog
     */
    public ConfigurationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Configuration");
        initComponents();
        initData();
    }

    private void initData() {
        ConfigManager config = ConfigManager.getCurrentInstance();
        synchronized (config) {
            // Maxium Number of Download
            {
                spnQueueMaxium.setValue(config.getMaxiumDownloadInQueue());
            }

            // Proxy
            boolean isUseProxy = config.getIsUsingProxy();
            cbxProxy.setSelected(isUseProxy);
            if (!isUseProxy) {
                txtProxyAddress.setEnabled(false);
                spnProxyPort.setEnabled(false);
            }
            {
                txtProxyAddress.setText(config.getProxyAddress());
                spnProxyPort.setValue(config.getProxyPort());
            }
            // Load Output
            {
                txtSaveTo.setSelectedFile(new File(config.getOutputFolder()));
                chbHtml.setSelected(config.isGenerateHtml());
                chbZip.setSelected(config.isZip());
                if (chbZip.isSelected()) {
                    chbDeleteZip.setEnabled(true);
                }
                chbDeleteZip.setSelected(config.isDeleteAfterZip());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        spnQueueMaxium = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtSaveTo = new bbmangadownloader.gui.control.JFolderChooser();
        chbZip = new javax.swing.JCheckBox();
        chbDeleteZip = new javax.swing.JCheckBox();
        chbHtml = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxProxy = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProxyAddress = new javax.swing.JTextField();
        spnProxyPort = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jLabel6.setText("Queue Size");

        spnQueueMaxium.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spnQueueMaxium, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(360, 360, 360))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnQueueMaxium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", jPanel4);

        jLabel5.setText("Save to");

        chbZip.setText("Zip File");
        chbZip.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chbZip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbZipActionPerformed(evt);
            }
        });

        chbDeleteZip.setText("Delete Image File After Zip");
        chbDeleteZip.setEnabled(false);
        chbDeleteZip.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        chbHtml.setText("Generate Html (Read in Browser)");
        chbHtml.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(chbZip, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(chbDeleteZip))
                    .addComponent(txtSaveTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbHtml))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSaveTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbHtml)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbZip)
                    .addComponent(chbDeleteZip))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Output", jPanel3);

        jLabel1.setText("Proxy");

        cbxProxy.setText("Use Proxy");
        cbxProxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProxyActionPerformed(evt);
            }
        });

        jLabel2.setText("HTTP Proxy");

        jLabel4.setText("Port");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbxProxy)
                        .addGap(0, 252, Short.MAX_VALUE))
                    .addComponent(txtProxyAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxProxy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtProxyAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(spnProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Connection", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxProxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProxyActionPerformed
        boolean isUseProxy = cbxProxy.isSelected();
        txtProxyAddress.setEnabled(isUseProxy);
        spnProxyPort.setEnabled(isUseProxy);
    }//GEN-LAST:event_cbxProxyActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ConfigManager config = ConfigManager.getCurrentInstance();
        synchronized (config) {
            // Maxium
            config.setMaxiumDownloadInQueue((Integer) spnQueueMaxium.getValue());

            // Proxy
            HttpDownloadManager downloadManager = HttpDownloadManager.getCurrentInstance();
            synchronized (downloadManager) {
                boolean isUseProxy = cbxProxy.isSelected();
                downloadManager.setIsUsingProxy(isUseProxy);
                if (isUseProxy) {
                    String address = txtProxyAddress.getText();
                    int port = (Integer) spnProxyPort.getValue();
                    downloadManager.setProxy(address, port);
                    config.setProxyAddress(address);
                    config.setProxyPort(port);
                }
                config.setIsUsingProxy(isUseProxy);
            }
            // Download                        

            File f = txtSaveTo.getSelectedFolder();
            try {
                f.mkdirs();
                if (f.isDirectory()) {
                    FileManager.setDownloadFolder(f);
                    config.setOutputFolder(f.getAbsolutePath());
                }
            } catch (Exception ex) {
            }

            // Zip, Html
            config.setZip(chbZip.isSelected());
            config.setDeleteAfterZip(chbDeleteZip.isSelected());
            config.setGenerateHtml(chbHtml.isSelected());
//            System.out.println("Save To: " + downloadFolder);



            try {
                // Save Config
                config.save();
                GUIUtilities.showDialog(this, "Saved configuration!");
                this.setVisible(false);
                this.dispose();
            } catch (Exception ex) {
                Logger.getLogger(ConfigurationDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void chbZipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbZipActionPerformed
        chbDeleteZip.setEnabled(chbZip.isSelected());
    }//GEN-LAST:event_chbZipActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbxProxy;
    private javax.swing.JCheckBox chbDeleteZip;
    private javax.swing.JCheckBox chbHtml;
    private javax.swing.JCheckBox chbZip;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spnProxyPort;
    private javax.swing.JSpinner spnQueueMaxium;
    private javax.swing.JTextField txtProxyAddress;
    private bbmangadownloader.gui.control.JFolderChooser txtSaveTo;
    // End of variables declaration//GEN-END:variables
}
