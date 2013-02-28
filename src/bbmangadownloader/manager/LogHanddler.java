/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author bach
 */
public class LogHanddler extends Handler {

//    private static final SimpleDateFormat FORMATER = new SimpleDateFormat("MM:dd hh:mm:ss");
//
//    private static String getCurrentTime() {
//        return FORMATER.format(new Date());
//    }
//
    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record) || record == null) {
            return;
        }

        String msg;
        try {
            msg = getFormatter().format(record);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.FORMAT_FAILURE);
            return;
        }
        try {
            String header = getFormatter().getHead(this);
            System.out.println("Log:\t" + header + "\t" + msg);
        } catch (Exception ex) {
            reportError(null, ex, ErrorManager.WRITE_FAILURE);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
