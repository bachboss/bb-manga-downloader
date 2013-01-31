/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

/**
 *
 * @author Bach
 */
public class _99770 extends _99mh {

    private static final String BASED_URL = "http://mh.99770.cc";
    private static final String URL_JS_FILE = "http://mh.99770.cc/x/i.js";
    private static final String DEFAULT_TRANS = "99770";
    private static final String DEFAULT_LIST_ABC = "http://mh.99770.cc/ComicABC/";

    @Override
    protected String getBaseUrl() {
        return BASED_URL;
    }

    @Override
    protected String getJSUrl() {
        return URL_JS_FILE;
    }

    @Override
    protected String getDefaultTranslator() {
        return DEFAULT_TRANS;
    }

    @Override
    protected String getDefaultList() {
        return DEFAULT_LIST_ABC;
    }
}
