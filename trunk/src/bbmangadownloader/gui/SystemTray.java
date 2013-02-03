/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui;

import bbmangadownloader.BBMangaDownloader;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author bach
 */
public class SystemTray {

    public static void loadSystemTray() {
        final TrayIcon trayIcon;

        if (java.awt.SystemTray.isSupported()) {
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            Image image = BBMangaDownloader.getApplicationIcon();
            //<editor-fold defaultstate="collapsed" desc="Mouse Listener">
            MouseListener mouseListener = new MyMouseListener();//</editor-fold>
            PopupMenu popup = new PopupMenu();
            //
            MenuItem menuExit = new MenuItem("Exit");
            menuExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            });
            popup.add(menuExit);
            // 
            MenuItem menuOpen = new MenuItem("Open");
            menuOpen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BBMangaDownloader.setVisibleMainWindows(true);
                }
            });
            popup.add(menuOpen);

            trayIcon = new TrayIcon(image, BBMangaDownloader.APPLICATION_NAME, popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Action Event",
                            "An Action Event Has Been Performed!",
                            TrayIcon.MessageType.INFO);
                }
            });
            trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        } else {
            System.out.println("System  not support tray icon...");
        }
    }

    private static class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Tray Icon - Mouse clicked!");
            if (e.getClickCount() >= 2 && SwingUtilities.isLeftMouseButton(e)) {
                BBMangaDownloader.setVisibleMainWindows(true);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("Tray Icon - Mouse entered!");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("Tray Icon - Mouse exited!");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("Tray Icon - Mouse pressed!");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("Tray Icon - Mouse released!");
        }
    };
}
