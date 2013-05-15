/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager;

import bbmangadownloader.ult.HtmlUtilities;
import bbmangadownloader.ult.HttpUtilities;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public class HttpDownloadManager {

    public static final DownloadConfig config = new DownloadConfig();
    //

//<editor-fold>
    public static class DownloadConfig {

        private static int DEFAULT_READ_TIME_OUT = 10000;
        private static int DEFAULT_CONNECT_TIME_OUT = 10000;
        private static int DEFAULT_ATTEMP = 5;
        private static String DEFAULT_USER_AGENT =
                "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0";
        private boolean isUsingProxy;
        private Proxy currentProxy;
        private String userAgent;
        private int connectTimeOut = 0;
        private int readTimeOut = 0;

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
    }

//</editor-fold>
    /**
     * Setting timeout, post, cookies, and others of connection
     *
     */
    private static HttpURLConnection getHttpURLConnection(URL url, MyConnection connection, int connectTimeOut, int readTimeOut)
            throws IOException {
//        System.out.println("Is Use Proxy (?): " + isUseProxy);
        DownloadConfig cConfig = HttpDownloadManager.config;
        // Proxy
        HttpURLConnection uc;
        if (cConfig.isIsUsingProxy()) {
            uc = (HttpURLConnection) url.openConnection(cConfig.getProxy());
        } else {
            uc = (HttpURLConnection) url.openConnection();
        }
        // User Agent
        {
            String userAgent = connection.getUserAgent() == null
                    ? cConfig.getUserAgent() : connection.getUserAgent();

            uc.setRequestProperty("User-Agent", userAgent);
        }
        // Referer
        {
            String referer = connection.getReferer();
            if (referer != null) {
                uc.setRequestProperty("Referer", referer);
            }
        }
        // Cookies
        {
            String cookies = connection.getCookie();
            if (cookies != null && !cookies.isEmpty()) {
                uc.setRequestProperty("cookie", cookies);
                uc.setRequestProperty("set-cookie", cookies);
            }
        }
        // Post 
        {
            String postForm = connection.getPost();
            if (postForm != null && !postForm.isEmpty()) {
                uc.setRequestMethod("POST");
                uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                uc.setDoOutput(true);
                uc.setRequestProperty("Content-Length", String.valueOf(postForm.length()));
                DataOutputStream wr = new DataOutputStream(uc.getOutputStream());
                wr.writeBytes(postForm);
                wr.flush();
            }
        }


        uc.setReadTimeout(readTimeOut);
        uc.setConnectTimeout(connectTimeOut);
        return uc;
    }

    /**
     * Try to fix with timeout, url is error
     *
     */
    private static void getConnectionFromConnectionWithFix(MyConnection connection) throws IOException {
        IOException lastEx = null;
        int connectTimeOut = config.getConnectTimeOut();
        int readTimeOut = config.getReadTimeOut();

        int tryTime = 0;
        boolean useEncodeString = false;
        URL url = new URL(connection.getUrl());
        URL downloadURL = url;


        boolean isDownloaded = false;
        while (!isDownloaded) {
            if (tryTime > DownloadConfig.DEFAULT_ATTEMP) {
                break;
            }
            if (bbmangadownloader.BBMangaDownloader.TEST) {
                System.out.println("Http download: " + downloadURL + "\t\tAttemp = " + tryTime);
            }
            try {
                HttpURLConnection urlConnection = getHttpURLConnection(downloadURL, connection, connectTimeOut, readTimeOut);
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    // Success                    
                    connection.setHttpAttribute(urlConnection);
                    return;
                } else {
                    try {
                        downloadURL = HtmlUtilities.encodeUrl(url, useEncodeString);
                        if (!useEncodeString) {
                            useEncodeString = !useEncodeString;
                        }
                    } catch (IOException ex) {
                        lastEx = ex;
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(HttpDownloadManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SocketTimeoutException ex) {
                lastEx = ex;
                readTimeOut *= 2;
                connectTimeOut *= 2;
            } catch (IOException ex) {
                lastEx = ex;
            }
            tryTime++;
        }
        if (lastEx != null) {
            throw lastEx;
        }
    }

    private static Document getDocumentFromConnection(MyConnection connection)
            throws IOException {
        getConnectionFromConnectionWithFix(connection);
        Document doc = Jsoup.parse(connection.getInputStream(),
                connection.getCharSet(),
                connection.getURL().toExternalForm());
        return doc;
    }

    private static InputStream getInputStreamFromConnection(MyConnection connection) throws IOException {
        getConnectionFromConnectionWithFix(connection);
        return connection.getInputStream();
    }

    public static InputStream getInputStreamFromUrl(String url) throws IOException {
        return getInputStreamFromConnection(new MyConnection(url));
    }

    public static MyConnection createConnection(String url) throws IOException {
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
        private boolean gzip = false;
        private HttpURLConnection httpUrlConnection;

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
            String s = HttpUtilities.getStringFromCookies(cookie);
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
            String s = HttpUtilities.getStringFromPostForms(post);
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

        public boolean isGzip() {
            return gzip;
        }

        public Document getDocument() throws IOException {
//            if (httpUrlConnection == null) {
//                getDocumentFromConnection(this);
//            }
            return getDocumentFromConnection(this);
        }

        public InputStream getInputStreamOpen() throws IOException {
//            if (httpUrlConnection == null) {
//                getInputStreamFromConnection(this);
//            }
//            return getInputStream();
            return getInputStreamFromConnection(this);
        }

        private InputStream getInputStream() throws IOException {
            if (httpUrlConnection == null) {
                return null;
            }
            if (gzip) {
                return new GZIPInputStream(httpUrlConnection.getInputStream());
            } else {
                return httpUrlConnection.getInputStream();
            }
        }

        private void setHttpAttribute(HttpURLConnection connection) {
            this.httpUrlConnection = connection;
            // G-zip
            if (connection.getHeaderField("Content-Encoding") != null
                    && connection.getHeaderField("Content-Encoding").equals("gzip")) {
                this.gzip = true;
            } else {
                gzip = false;
            }
        }
    }
}
