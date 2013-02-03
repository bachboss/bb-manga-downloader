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

    public MyHttpdServer() throws IOException {
        super(9090, null);
    }

    public void setIm(IMangaInterface im) {
        this.im = im;
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        String url = parms.getProperty("url");
        System.out.println("Received Url = " + url);
        im.addUrl(url);
        return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, "OK");
    }
}
