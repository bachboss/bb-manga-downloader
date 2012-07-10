/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus.exception;

import bbmangadownloader.entity.HtmlDocument;

/**
 *
 * @author Bach
 */
public class HtmlParsingException extends Exception {

    public HtmlParsingException() {
        super();
    }

    public HtmlParsingException(HtmlDocument doc) {
        this("Error on parsing: \"" + doc.getUrl() + "\"");
    }

    public HtmlParsingException(HtmlDocument doc, String message) {
        this("Error on parsing: \"" + doc.getUrl() + "\"\nMessage = " + message);
    }

    public HtmlParsingException(String message) {
        super(message);
    }

    public HtmlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public HtmlParsingException(Throwable cause) {
        super(cause);
    }
}
