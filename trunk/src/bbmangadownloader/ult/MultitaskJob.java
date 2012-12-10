/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class MultitaskJob {

    public static final int PRIOVITY_HIGH = 4;
    public static final int PRIOVITY_NORMAL = 3;
    public static final int PRIOVITY_LOW = 2;
    private static final int DEFAULT_POOL_SIZE = 3;

    public static <V> List<Future<V>> doTask(int poolSize, List<? extends Callable<V>> listTask) {
        if (poolSize != 0) {
            ExecutorService execSvc = Executors.newFixedThreadPool(poolSize);
            try {
                List<Future<V>> lstReturn = execSvc.invokeAll(listTask);
                return lstReturn;
            } catch (InterruptedException ex) {
                Logger.getLogger(MultitaskJob.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } finally {
                execSvc.shutdown();
            }
        }
        return null;
    }

    public static <V> List<Future<V>> doTask(List<? extends Callable<V>> listTask) {
        return doTask(DEFAULT_POOL_SIZE, listTask);
    }
}