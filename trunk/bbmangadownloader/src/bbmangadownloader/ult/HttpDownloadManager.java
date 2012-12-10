/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.EncoderException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public class HttpDownloadManager {

    private static int DEFAULT_READ_TIME_OUT = 60000;
    private static int DEFAULT_CONNECT_TIME_OUT = 60000;
//    private static int DEFAULT_STEP = 10000;
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

    public static String getStringFromPostForms(Map<String, String> postForm) throws UnsupportedEncodingException {
        if (postForm != null && !postForm.isEmpty()) {
            Set<Entry<String, String>> data = postForm.entrySet();
            StringBuilder content = new StringBuilder();
            boolean isFirst = true;
            for (Entry<String, String> entry : data) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    content.append('&');
                }
                content.append(entry.getKey()).append('=').
                        append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return content.toString();
        } else {
            return "";
        }
    }

    public static String getStringFromCookies(Map<String, String> cookies) {
        if (cookies != null && !cookies.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : cookies.entrySet()) {
                sb.append(item.getKey()).append("=").append(item.getValue()).append(";");
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Lowest level of HTTP Connection Others are wrapper
     */
    private static HttpURLConnection getHttpURLConnection(MyConnection connection, int connectTimeOut, int readTimeOut) throws IOException {
//        System.out.println("Is Use Proxy (?): " + isUseProxy);
        String userAgent = connection.getUserAgent() == null ? DEFAULT_USER_AGENT : connection.getUserAgent();
        URL url = new URL(connection.getUrl());

        HttpDownloadManager dlmng = getCurrentInstance();
        HttpURLConnection uc;
        if (dlmng.isIsUsingProxy()) {
            uc = (HttpURLConnection) url.openConnection(dlmng.getProxy());
        } else {
            uc = (HttpURLConnection) url.openConnection();
        }

        uc.setRequestProperty("User-Agent", userAgent);

        {
            String cookies = connection.getCookie();
            if (cookies != null && !cookies.isEmpty()) {
                uc.setRequestProperty("cookie", cookies);
                uc.setRequestProperty("set-cookie", cookies);
            }
        }

        {
            String postForm = connection.getPost();
            if (postForm != null && !postForm.isEmpty()) {
                uc.setRequestMethod("POST");
                uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                uc.setDoOutput(true);
                uc.setRequestProperty("Content-Length", String.valueOf(postForm.length()));
                DataOutputStream wr = new DataOutputStream(uc.getOutputStream());
                try {
                    wr.writeBytes(postForm);
                    wr.flush();
                } catch (Exception ex) {
                    Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        {
            String referer = connection.getReferer();
            if (referer != null) {
                uc.setRequestProperty("Referer", referer);
            }
        }

        uc.setReadTimeout(readTimeOut);
        uc.setConnectTimeout(connectTimeOut);

        return uc;
    }

    private static Document getDocumentFromUrl(MyConnection connection, int connectTimeOut, int readTimeOut)
            throws IOException {
//        System.out.println("Is Use Proxy (?): " + isUseProxy);                
        HttpURLConnection uc = getHttpURLConnection(connection, connectTimeOut, readTimeOut);
        Document doc = Jsoup.parse(uc.getInputStream(),
                connection.getCharSet(),
                connection.getURL().toExternalForm());
        return doc;
    }

    private static InputStream getInputStreamFromUrl(MyConnection connection) throws IOException {
        int connectTimeOut;
        int readTimeOut;
        {
            HttpDownloadManager c = getCurrentInstance();
            connectTimeOut = c.getConnectTimeOut();
            readTimeOut = c.getReadTimeOut();
        }
        HttpURLConnection uc = getHttpURLConnection(connection, connectTimeOut, readTimeOut);
        return uc.getInputStream();
    }

    @Deprecated
    /**
     * Use HttpDownloadManager.getInputStreamFromUrl instead
     */
    public static InputStream getInputStreamFromUrl(String url) throws IOException {
        MyConnection c = new MyConnection(url);
        return getInputStreamFromUrl(c);
    }

    private static Document getDocumentFromConnection(MyConnection connection) throws IOException {
        IOException lastEx = null;
        int connectTimeOut;
        int readTimeOut;
        {
            HttpDownloadManager c = getCurrentInstance();
            connectTimeOut = c.getConnectTimeOut();
            readTimeOut = c.getReadTimeOut();
        }
        int tryTime = 0;
        boolean useEncodeString = true;
        boolean caughtFileNotFoundEx = false;
        URL fileUrl;
        URL url = new URL(connection.getUrl());

        boolean isDownloaded = false;
        try {
            fileUrl = HtmlUtilities.encodeUrl(url, useEncodeString);
            boolean isTryAgain = false;
            do {
                try {
                    System.out.println("\tDownloading HTML from: " + fileUrl);
                    // Download Here !
                    Document doc = getDocumentFromUrl(connection, connectTimeOut, readTimeOut);
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
                    isTryAgain = true;
                    System.out.println("\t\tSocketTimeOutExcption, re-try (" + tryTime + ") with timeout = " + connectTimeOut);
//                            Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        // is not HTTP Error (Code >= 400, != 410,400)
                        isTryAgain = true;
                        tryTime++;
                        Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (isTryAgain && tryTime < DEFAULT_ATTEMP);
            if (!isDownloaded) {
                throw lastEx;
            } else {
                return null;
            }
        } catch (URISyntaxException ex) {
            System.out.println("\t\tCan Process this type of error !");
            Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (EncoderException ex) {
            System.out.println("\t\tCan Process this type of error !");
            Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static MyConnection createConnection(String url) throws MalformedURLException {
        return new MyConnection(url);
    }

    public static final class MyConnection {

        private String post;
        private String cookie;
        private String url;
        private String charSet;
        private String userAgent;
        private String referer;
        private URL URL;

        private MyConnection(String url) throws MalformedURLException {
            url(url);
        }

        public MyConnection url(String url) throws MalformedURLException {
            this.url = url;
            this.URL = new URL(url);
            return this;
        }

        public MyConnection URL(URL URL) {
            this.URL = URL;
            this.url = URL.toString();
            return this;
        }

        public String getReferer() {
            return referer;
        }

        public MyConnection referer(String referer) {
            this.referer = referer;
            return this;
        }

        public String getCharSet() {
            return charSet;
        }

        public MyConnection charSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public String getCookie() {
            return cookie;
        }

        public MyConnection cookie(String cookie) {
            this.cookie = cookie;
            return this;
        }

        public MyConnection cookie(Map<String, String> cookie) {
            String s = getStringFromCookies(cookie);
            if (s == null || s.isEmpty()) {
                this.cookie = null;
            } else {
                this.cookie = s;
            }
            return this;
        }

        public String getPost() {
            return post;
        }

        public MyConnection post(String post) {
            this.post = post;
            return this;
        }

        public MyConnection post(Map<String, String> post) throws UnsupportedEncodingException {
            String s = getStringFromPostForms(post);
            if (s == null || s.isEmpty()) {
                this.post = null;
            } else {
                this.post = s;
            }
            return this;
        }

        public String getUrl() {
            return url;
        }

        public URL getURL() {
            return URL;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public MyConnection userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Document getDocument() throws IOException {
            return getDocumentFromConnection(this);
        }

        public InputStream getInputStream() throws IOException {
            return getInputStreamFromUrl(this);
        }
    }
}
