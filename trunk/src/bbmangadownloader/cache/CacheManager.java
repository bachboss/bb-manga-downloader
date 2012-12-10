/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.cache;

/**
 *
 * @author Bach
 */
public class CacheManager {
//    private static final Map<String, File> MAP_FILE = new HashMap<>();
//    private static final File folderCache = new File("D:\\Manga\\Cache\\Temp\\");
//
//    static {
//        folderCache.mkdirs();
//    }
//
//    public static boolean writeObjectToCache(String name, Object data) {
//        if (!MAP_FILE.containsKey(name)) {
//            ObjectOutputStream oos = null;
//            try {
//                File f = File.createTempFile(name, ".tmp", folderCache);
//                f.deleteOnExit();
//                oos = new ObjectOutputStream(new FileOutputStream(f));
//                oos.writeObject(data);
//                MAP_FILE.put(name, f);
//            } catch (IOException ex) {
//                System.out.println("Can not write to cache: ");
//                Logger.getLogger(CacheManager.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            } finally {
//                try {
//                    oos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(CacheManager.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return false;
//    }
//
//    public static Object getObjectFromCache(String name) throws IOException {
//        File f = MAP_FILE.get(name);
//        if (f != null) {
//            ObjectInputStream ois = null;
//            try {
//                ois = new ObjectInputStream(new FileInputStream(f));
//                try {
//                    Object o = ois.readObject();
//                    return o;
//                } catch (ClassNotFoundException ex) {
//                    return null;
//                }
//            } finally {
//                try {
//                    ois.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(CacheManager.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return null;
//    }
}