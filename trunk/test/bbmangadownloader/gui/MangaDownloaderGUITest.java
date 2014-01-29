/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.BBMangaDownloader;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;
import bbmangadownloader.manager.ConfigManager;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author bach
 */
public class MangaDownloaderGUITest extends javax.swing.JFrame {

    /**
     * Creates new form MangaDownloaderGUITest
     */
    public MangaDownloaderGUITest() {
        initComponents();
        initSampleData();
    }

    private void initSampleData() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Server s = ServerManager.getServerByName("test");
                Manga m = new Manga(s, "Test Mana", "http://google.com.vn");
                Random r = new Random();
                for (int i = 0; i < 20; i++) {
                    pnlTaskDownload.addChapter(
                            new Chapter(i, "Chapter " + r.nextInt(1000), "http://google.com.vn",
                                    m));
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mangaDownloadGUI1 = new bbmangadownloader.gui.MangaDownloadGUI();
        pnlTaskDownload = new bbmangadownloader.gui.control.PanelDownload();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlTaskDownload, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlTaskDownload, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc="Set Look&Feel">
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(BBMangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConfigManager.loadOnStartUp();
                ServerManager.loadServer();
                new MangaDownloaderGUITest().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private bbmangadownloader.gui.MangaDownloadGUI mangaDownloadGUI1;
    private bbmangadownloader.gui.control.PanelDownload pnlTaskDownload;
    // End of variables declaration//GEN-END:variables
}
