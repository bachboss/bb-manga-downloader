/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Bach
 */
public class HelpDialog extends javax.swing.JDialog {

    private DefaultComboBoxModel<ServerUrl> modelServer;

    public HelpDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Help");
        initComponents();
        initHosts();
    }
    private IHelpListener listener;

    public void setListener(IHelpListener listener) {
        this.listener = listener;
    }

    private void initHosts() {
        //<editor-fold defaultstate="collapsed" desc="Links">
        String[] arrLink = new String[]{
            "http://eatmanga.com/Manga-Scan/Toriko",
            "http://mangafox.me/manga/fairy_tail/",
            "http://www.mangareader.net/120/the-world-god-only-knows.html",
            "http://truyentranhtuan.com/cage-of-eden/",
//            "http://vechai.info/Naruto/",
            "http://www.batoto.net/comic/_/comics/beelzebub-r4",
            "http://truyen.vietboom.com/truyen/Death-Note",
            "http://truyen.comicvn.net/2011/03/bloody-monday-ngay-thu-hai-dam-mau.html",
            "http://truyen.vnsharing.net/Truyen/K-On-2011?id=4930",
            "http://kissmanga.com/Manga/Uzumaki",
            "http://www.mangainn.com/manga/431_hunter-x-hunter",
            "http://www.mangahere.com/manga/fairy_tail/",
            "http://cococomic.com/comic/5533/",
            "http://mh.99770.cc/comic/168/",
            "http://manga24h.com/944/Toriko.html",
            "http://blogtruyen.com/Truyen/rosariovampire-season-2",
            "http://www.tenmanga.com/book/Naruto.html",
            "http://mngcow.net/Crepuscule/"
//            "http://99mh.com/comic/7350/"
        };
//</editor-fold>

        ArrayList<ServerUrl> lstServerUrl = new ArrayList<ServerUrl>();

        for (String link : arrLink) {
            ServerUrl sU = new ServerUrl();
            sU.url = link;
            sU.server = ServerManager.getServerByUrl(link);
            lstServerUrl.add(sU);
        }
        Collections.sort(lstServerUrl, new Comparator<ServerUrl>() {
            @Override
            public int compare(ServerUrl o1, ServerUrl o2) {
                String s1 = o1.server == null ? null : o1.server.getServerName();
                String s2 = o2.server == null ? null : o2.server.getServerName();
                if (s1 == null) {
                    return -1;
                }
                if (s2 == null) {
                    return 1;
                }
                return s1.compareTo(s2);
            }
        });

        modelServer = new DefaultComboBoxModel<ServerUrl>();
        for (ServerUrl s : lstServerUrl) {
            modelServer.addElement(s);
        }

        cbxSupportedHost.setModel(modelServer);
        cbxSupportedHost.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbxSupportedHost = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        lblLink = new org.jdesktop.swingx.JXHyperlink();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(2147483647, 100));
        setMinimumSize(new java.awt.Dimension(600, 90));

        jLabel1.setText("Supported host:");

        cbxSupportedHost.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxSupportedHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSupportedHostActionPerformed(evt);
            }
        });

        jLabel2.setText("Sample manga url:");

        lblLink.setText("lblLink");

        jPanel1.setMaximumSize(new java.awt.Dimension(125, 33));

        jButton1.setText("Add to manga url");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxSupportedHost, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLink, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxSupportedHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxSupportedHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSupportedHostActionPerformed
        try {
            String url = ((ServerUrl) modelServer.getSelectedItem()).url;
            lblLink.setText(url);
            lblLink.setURI(new URI(url));
        } catch (URISyntaxException ex) {
            Logger.getLogger(HelpDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbxSupportedHostActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listener.doAddSampleUrl(lblLink.getText());
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxSupportedHost;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXHyperlink lblLink;
    // End of variables declaration//GEN-END:variables

    private static class ServerUrl {

        private Server server;
        private String url;

        @Override
        public String toString() {
            return server == null ? url : server.getServerName();
        }
    }

    public static interface IHelpListener {

        void doAddSampleUrl(String url);
    }
}
