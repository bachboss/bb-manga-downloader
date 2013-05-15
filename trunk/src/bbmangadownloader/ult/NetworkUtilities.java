/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Bach
 */
public class NetworkUtilities {

    public static List<InetAddress> getLocalInetAddress() throws SocketException {
        return getLocalInetAddress(false, false);
    }

    public static List<InetAddress> getLocalInetAddress(boolean isSelfLoop, boolean isIpV6) throws SocketException {
        List<InetAddress> addrList = new ArrayList<InetAddress>();
        ArrayList<NetworkInterface> e1 = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface ifc : e1) {
            if (ifc.isUp()) {
                ArrayList<InetAddress> e2 = Collections.list(ifc.getInetAddresses());
                for (InetAddress addr : e2) {
                    if (!isIpV6 && addr instanceof Inet6Address) {
                        continue;
                    }
                    byte[] arrByte = addr.getAddress();
                    if (!isSelfLoop && (arrByte[0] == 0
                            || (arrByte[0] == 127 && arrByte[1] == 0
                            && arrByte[2] == 0 && arrByte[3] == 1))) {
                        continue;
                    }
                    addrList.add(addr);
                }
            }
        }

        if (addrList.size() > 1) {
            Collections.sort(addrList, new Comparator<InetAddress>() {
                @Override
                public int compare(InetAddress o1, InetAddress o2) {
                    boolean b1 = (o1 instanceof Inet6Address);
                    boolean b2 = (o2 instanceof Inet6Address);
                    if (b1 && b2) {
                        return -o1.getHostAddress().compareTo(o2.getHostAddress());
                    }
                    if (b1) {
                        return 1;
                    }
                    if (b2) {
                        return -1;
                    }
                    return -o1.getHostAddress().compareTo(o2.getHostAddress());
                }
            });
        }

        return addrList;
    }
}
