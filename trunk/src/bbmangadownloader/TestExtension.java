/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader;

import java.io.IOException;
import nanohttpd.MyHttpdServer;

/**
 *
 * @author Bach
 */
public class TestExtension {

    public static void main(String[] args) throws IOException {
        MyHttpdServer customServer = new MyHttpdServer();
        System.out.println("Created server on port 9090.");
        System.out.println("Press <Enter> to exit");
        System.in.read();
    }
}
