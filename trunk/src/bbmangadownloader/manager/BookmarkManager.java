/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager;

import bbmangadownloader.manager.entity.Bookmark;
import bbmangadownloader.manager.entity.ListBookmark;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Bach
 */
public class BookmarkManager {

    private static ListBookmark listBookmark;
    private static final LazyUpdateTask updateTask = new LazyUpdateTask();

    private static boolean IS_LOADED = false;

    public static synchronized void lazyLoad() {
        if (!IS_LOADED) {
            System.out.println("Load Bookmark...");
            IS_LOADED = true;
            try {
                File fileXml = new File(ConfigManager.getCurrentInstance().getBookmarkFile());
                if (!fileXml.exists() || fileXml.length() == 0) {
                    listBookmark = new ListBookmark();
                    saveToFile();
                } else {
                    // Load from file
                    JAXBContext jc = JAXBContext.newInstance(ListBookmark.class);
                    Unmarshaller reader = jc.createUnmarshaller();
                    listBookmark = (ListBookmark) reader.unmarshal(fileXml);
                }
                //
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(updateTask, 1500, 1500);
            } catch (JAXBException ex) {
                Logger.getLogger(BookmarkManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BookmarkManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void saveToFile() throws JAXBException, IOException {
        File fileXml = new File(ConfigManager.getCurrentInstance().getBookmarkFile());
        JAXBContext jc = JAXBContext.newInstance(ListBookmark.class);
        Marshaller writer = jc.createMarshaller();

        writer.marshal(listBookmark, new FileWriter(fileXml));
        System.out.println("Save Bookmark to file !");
    }

    public static List<Bookmark> getBookMarks() {
        lazyLoad();
        return listBookmark;
    }

    public static boolean addBookMark(Bookmark bookmark) {
        lazyLoad();
        for (Bookmark b : listBookmark) {
            if (b.getUrl().equals(bookmark.getUrl())) {
                return false;
            }
        }
        listBookmark.add(bookmark);
        markUpdate();
        return true;
    }

    public static void removeBookmark(Bookmark bookmark) {
        lazyLoad();
        for (Bookmark b : listBookmark) {
            if (b.getUrl().equals(bookmark.getUrl())) {
                listBookmark.remove(b);
                markUpdate();
                break;
            }
        }
    }

    public static void markUpdate() {
        lazyLoad();
        updateTask.isUpdate = true;
    }

    private static class LazyUpdateTask extends TimerTask {

        private boolean isUpdate = false;

        @Override
        public void run() {
            synchronized (this) {
                if (isUpdate) {
                    try {
                        saveToFile();
                    } catch (JAXBException ex) {
                        Logger.getLogger(BookmarkManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(BookmarkManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    isUpdate = false;
                }
            }
        }
    }
}
