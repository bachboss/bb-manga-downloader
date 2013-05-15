package nanohttpd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static nanohttpd.NanoHTTPD.HTTP_FORBIDDEN;
import static nanohttpd.NanoHTTPD.HTTP_INTERNALERROR;
import static nanohttpd.NanoHTTPD.HTTP_NOTFOUND;
import static nanohttpd.NanoHTTPD.HTTP_NOTMODIFIED;
import static nanohttpd.NanoHTTPD.HTTP_OK;
import static nanohttpd.NanoHTTPD.HTTP_PARTIALCONTENT;
import static nanohttpd.NanoHTTPD.HTTP_RANGE_NOT_SATISFIABLE;
import static nanohttpd.NanoHTTPD.HTTP_REDIRECT;
import static nanohttpd.NanoHTTPD.MIME_DEFAULT_BINARY;
import static nanohttpd.NanoHTTPD.MIME_HTML;
import static nanohttpd.NanoHTTPD.MIME_PLAINTEXT;

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

    @Override
    public Response serveFile(String uri, Properties header, File homeDir,
            boolean allowDirectoryListing) {
        Response res = null;

        // Make sure we won't die of an exception later
        if (!homeDir.isDirectory()) {
            res = new Response(HTTP_INTERNALERROR, MIME_PLAINTEXT,
                    "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.");
        }
        if (res == null) {
            // Remove URL arguments
            uri = uri.trim().replace(File.separatorChar, '/');
            if (uri.indexOf('?') >= 0) {
                uri = uri.substring(0, uri.indexOf('?'));
            }
            // Prohibit getting out of current directory
            if (uri.startsWith("..") || uri.endsWith("..") || uri.indexOf("../") >= 0) {
                res = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT,
                        "FORBIDDEN: Won't serve ../ for security reasons.");
            }
        }

        File f = new File(homeDir, uri);
        if (res == null && !f.exists()) {
            res = new Response(HTTP_NOTFOUND, MIME_PLAINTEXT,
                    "Error 404, file not found.");
        }

        // List the directory, if necessary
        if (res == null && f.isDirectory()) {
            // Browsers get confused without '/' after the
            // directory, send a redirect.
            if (!uri.endsWith("/")) {
                uri += "/";
                res = new Response(HTTP_REDIRECT, MIME_HTML,
                        "<html><body>Redirected: <a href=\"" + uri + "\">"
                        + uri + "</a></body></html>");
                res.addHeader("Location", uri);
            }

            if (res == null) {
                // First try index.html and index.htm
                if (new File(f, "index.html").exists()) {
                    f = new File(homeDir, uri + "/index.html");
                } else if (new File(f, "index.htm").exists()) {
                    f = new File(homeDir, uri + "/index.htm");
                } // No index file, list the directory if it is readable
                else if (allowDirectoryListing && f.canRead()) {
                    res = listDirectory(f, uri);
                } else {
                    res = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT,
                            "FORBIDDEN: No directory listing.");
                }
            }
        }

        try {
            if (res == null) {
                // Get MIME type from file name extension, if possible
                String mime = null;
                int dot = f.getCanonicalPath().lastIndexOf('.');
                if (dot >= 0) {
                    mime = (String) theMimeTypes.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
                }
                if (mime == null) {
                    mime = MIME_DEFAULT_BINARY;
                }

                // Calculate etag
                String etag = Integer.toHexString((f.getAbsolutePath() + f.lastModified() + "" + f.length()).hashCode());

                // Support (simple) skipping:
                long startFrom = 0;
                long endAt = -1;
                String range = header.getProperty("range");
                if (range != null) {
                    if (range.startsWith("bytes=")) {
                        range = range.substring("bytes=".length());
                        int minus = range.indexOf('-');
                        try {
                            if (minus > 0) {
                                startFrom = Long.parseLong(range.substring(0, minus));
                                endAt = Long.parseLong(range.substring(minus + 1));
                            }
                        } catch (NumberFormatException nfe) {
                        }
                    }
                }

                // Change return code and add Content-Range header when skipping is requested
                long fileLen = f.length();
                if (range != null && startFrom >= 0) {
                    if (startFrom >= fileLen) {
                        res = new Response(HTTP_RANGE_NOT_SATISFIABLE, MIME_PLAINTEXT, "");
                        res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                        if (mime.startsWith("application/")) {
                            res.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                        }
                        res.addHeader("ETag", etag);
                    } else {
                        if (endAt < 0) {
                            endAt = fileLen - 1;
                        }
                        long newLen = endAt - startFrom + 1;
                        if (newLen < 0) {
                            newLen = 0;
                        }

                        final long dataLen = newLen;
                        FileInputStream fis = new FileInputStream(f) {
                            @Override
                            public int available() throws IOException {
                                return (int) dataLen;
                            }
                        };
                        fis.skip(startFrom);

                        res = new Response(HTTP_PARTIALCONTENT, mime, fis);
                        res.addHeader("Content-Length", "" + dataLen);
                        res.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                        if (mime.startsWith("application/")) {
                            res.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                        }
                        res.addHeader("ETag", etag);
                    }
                } else {
                    if (etag.equals(header.getProperty("if-none-match"))) {
                        res = new Response(HTTP_NOTMODIFIED, mime, "");
                    } else {
                        res = new Response(HTTP_OK, mime, new FileInputStream(f));
                        res.addHeader("Content-Length", "" + fileLen);
                        if (mime.startsWith("application/")) {
                            res.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                        }
                        res.addHeader("ETag", etag);
                    }
                }
            }
        } catch (IOException ioe) {
            res = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
        }

        res.addHeader("Accept-Ranges", "bytes"); // Announce that the file server accepts partial content requestes
        return res;
    }

    private Response listDirectory(File f, String uri) {
        String[] files = f.list();
        StringBuilder msg = new StringBuilder("<html>");
        msg.append("<title>").append(f.getName()).append("</title>").
                append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
                .append("<body><h1>Directory ").append(uri).append("</h1><br/>");

        if (uri.length() > 1) {
            String u = uri.substring(0, uri.length() - 1);
            int slash = u.lastIndexOf('/');
            if (slash >= 0 && slash < u.length()) {
                msg.append("<b><a href=\"").
                        append(uri.substring(0, slash + 1)).
                        append("\">..</a></b><br/>");
            }
        }

        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                File curFile = new File(f, files[i]);
                boolean dir = curFile.isDirectory();
                if (dir) {
//                    msg.append("<b>");
                    files[i] += "/";
                }

                msg.append("<a href=\"").
                        append(encodeUri(uri + files[i])).
                        append("\">").
                        append(files[i]).
                        append("</a>");

                // Show file size
                if (curFile.isFile()) {
                    msg.append("&nbsp;<font size=2>(").append(getFileSize(curFile)).append(")</font>");
                }
                msg.append("<br/>");
                if (dir) {
//                    msg.append("</b>");
                }
            }
        }
        msg.append("</body></html>");
        return new Response(HTTP_OK, MIME_HTML, msg.toString());
    }

    private String getFileSize(File curFile) {
        long len = curFile.length();
        String msg;
        if (len < 1024) {
            msg = len + " B";
        } else if (len < 1024 * 1024) {
            msg = len / 1024 + "." + (len % 1024 / 10 % 100) + " KB";
        } else {
            msg = len / (1024 * 1024) + "." + len % (1024 * 1024) / 10 % 100 + " MB";
        }
        return msg;
    }
}
