package nanohttpd;

import java.io.File;
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
public class FileBrowserHttpdServer extends NanoHTTPD {

    public FileBrowserHttpdServer(int port, File directory) throws IOException {
        super(port, directory);
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        return serveFile(uri, header, myRootDir, true);
    }
}
