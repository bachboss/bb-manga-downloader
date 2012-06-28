/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.ult;

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

    public static <V> List<Future<V>> doTask(int poolSize, List<? extends Callable<V>> listTask) {
        ExecutorService execSvc = Executors.newFixedThreadPool(poolSize);
        try {
            List<Future<V>> lstReturn = execSvc.invokeAll(listTask);
            execSvc.shutdown();
            return lstReturn;
        } catch (InterruptedException ex) {
            Logger.getLogger(MultitaskJob.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}