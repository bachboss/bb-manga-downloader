package nanohttpd;

import bbmangadownloader.gui.IMangaInterface;
import java.io.IOException;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bach
 */
public class MyHttpdServer extends NanoHTTPD {

    private IMangaInterface im;
    public static final int HTTP_PORT = 25560;

    public MyHttpdServer() throws IOException {
        super(HTTP_PORT, null);
    }

    public void setIm(IMangaInterface im) {
        this.im = im;
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        String url = parms.getProperty("url");
        if (uri.startsWith("/extension/")) {
            System.out.println("Received Url = " + url);
            if (im != null) {
                im.addUrl(url);
            }
            return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, "OK");
        } else {
            return new NanoHTTPD.Response(HTTP_NOTFOUND, MIME_HTML, "Unknown Request");
        }
    }
}
