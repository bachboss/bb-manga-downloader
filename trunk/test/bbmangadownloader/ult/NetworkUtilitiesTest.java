/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

/**
 *
 * @author Bach
 */
public class NetworkUtilitiesTest {

    public static void main(String[] args) throws SocketException {
        List<InetAddress> lst = NetworkUtilities.getLocalInetAddress(false, false);
        for (InetAddress address : lst) {
            System.out.println(address.getHostAddress());
        }
    }
}