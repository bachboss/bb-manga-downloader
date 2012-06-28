/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.ult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import bbmangadownloader.MangaDownloader;
import org.apache.commons.codec.EncoderException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public class HttpDownloadManager {

    private static int DEFAULT_READ_TIME_OUT = 20000;
    private static int DEFAULT_CONNECT_TIME_OUT = 20000;
    private static int DEFAULT_STEP = 10000;
    private static int DEFAULT_ATTEMP = 3;
    private static String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0";
    //
    private static HttpDownloadManager cI;
    //
    private boolean isUsingProxy;
    private Proxy currentProxy;
    private String userAgent;
    private int connectTimeOut = 0;
    private int readTimeOut = 0;

    private HttpDownloadManager() {
    }
//<editor-fold>

    public static HttpDownloadManager getCurrentInstance() {
        if (cI == null) {
            synchronized (HttpDownloadManager.class) {
                if (cI == null) {
                    cI = new HttpDownloadManager();
                }
            }
        }
        return cI;
    }

    public static void init() {
        cI = new HttpDownloadManager();
    }

    public void setProxy(String address, int port) {
        currentProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, port));
    }

    private Proxy getProxy() {
        return currentProxy;
    }

    public boolean isIsUsingProxy() {
        return isUsingProxy;
    }

    public void setIsUsingProxy(boolean isUsingProxy) {
        this.isUsingProxy = isUsingProxy;
    }

    private int getConnectTimeOut() {
        return connectTimeOut == 0 ? DEFAULT_CONNECT_TIME_OUT : connectTimeOut;
    }

    private int getReadTimeOut() {
        return readTimeOut == 0 ? DEFAULT_READ_TIME_OUT : readTimeOut;
    }

    private String getUserAgent() {
        if (userAgent == null) {
            return DEFAULT_USER_AGENT;
        }
        if (userAgent.isEmpty()) {
            return DEFAULT_USER_AGENT;
        }
        return userAgent;
    }
//</editor-fold>

    private static String getStringFromCookies(Map<String, String> cookies) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : cookies.entrySet()) {
            sb.append(item.getKey()).append("=").append(item.getValue()).append(";");
        }
        return sb.toString();
    }

    private static Document getDocumentFromUrl(
            URL url, Map<String, String> cookies, int connectTimeOut, int readTimeOut, boolean isUseProxy)
            throws IOException {
//        System.out.println("Is Use Proxy (?): " + isUseProxy);
        HttpDownloadManager dlmng = getCurrentInstance();
        HttpURLConnection uc;
        if (isUseProxy) {
            uc = (HttpURLConnection) url.openConnection(dlmng.getProxy());
        } else {
            uc = (HttpURLConnection) url.openConnection();
        }

        uc.setRequestProperty("User-Agent", dlmng.getUserAgent());

        if (cookies != null) {
            String c = getStringFromCookies(cookies);
            uc.setRequestProperty("cookie", c);
            uc.setRequestProperty("set-cookie", c);
        }
        uc.setReadTimeout(readTimeOut);
        uc.setConnectTimeout(connectTimeOut);

        Document doc = Jsoup.parse(uc.getInputStream(), null, url.toExternalForm());
        return doc;
    }

    public static Document getDocument(String url) throws IOException {
        return getDocument(url, null);

    }

    public static Document getDocument(String strUrl, Map<String, String> cookies) throws IOException {
        IOException lastEx = null;
        int connectTimeOut;
        int readTimeOut;
        boolean isUseProxy;
        {
            HttpDownloadManager c = getCurrentInstance();
            connectTimeOut = c.getConnectTimeOut();
            readTimeOut = c.getReadTimeOut();
            isUseProxy = c.isIsUsingProxy();
        }
        int tryTime = 0;
        boolean useEncodeString = true;
        boolean caughtFileNotFoundEx = false;
        URL fileUrl;
        URL url = new URL(strUrl);

        boolean isDownloaded = false;
        try {
            fileUrl = HtmlUtilities.encodeUrl(url, useEncodeString);
            boolean isTryAgain = false;
            do {
                try {
                    System.out.println("\tDownloading HTML from: " + fileUrl);
                    // Download Here !
                    Document doc = getDocumentFromUrl(fileUrl,
                            cookies,
                            connectTimeOut,
                            readTimeOut,
                            isUseProxy);
                    return doc;
                } catch (FileNotFoundException ex) {
                    lastEx = ex;
                    if (!caughtFileNotFoundEx) {
                        caughtFileNotFoundEx = true;
                        isTryAgain = true;
                        useEncodeString = !useEncodeString;
                        fileUrl = HtmlUtilities.encodeUrl(url, useEncodeString);
                        System.out.println("\t\tFileNotFoundException: " + ex.getMessage());
                    } else {
                        break;
                    }
                } catch (SocketTimeoutException ex) {
                    lastEx = ex;
                    tryTime++;
                    connectTimeOut += DEFAULT_STEP;
                    readTimeOut += DEFAULT_STEP;
                    isTryAgain = true;
                    System.out.println("\t\tSocketTimeOutExcption, re-try (" + tryTime + ") with timeout = " + connectTimeOut);
//                            Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    lastEx = ex;
                    System.out.println("\t\tIOException: " + ex.getMessage());
                    int httpError = ExceptionUtilities.getHttpErrorCode(ex);
                    if (httpError != -1) {
                        System.out.println("\t\t\tHTTP Error: " + httpError);
                        if (httpError == 400) {
                            useEncodeString = !useEncodeString;
                            fileUrl = HtmlUtilities.encodeUrl(url, useEncodeString);
                        } else {
                            isTryAgain = true;
                            tryTime++;
                            Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        // is not HTTP Error (Code >= 400, != 410,400)
                        isTryAgain = true;
                        tryTime++;
                        Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (isTryAgain && tryTime < DEFAULT_ATTEMP);
            if (!isDownloaded) {
                throw lastEx;
            } else {
                return null;
            }
        } catch (URISyntaxException | EncoderException ex) {
            System.out.println("\t\tCan Process this type of error !");
            Logger.getLogger(MangaDownloader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
