/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

/**
 *
 * @author Bach
 */
public enum WatcherStatus {

    None(0), Loading(1), Loaded(2);
    private int type;
    private static final String[] TYPE_NAME = {"None", "Loading", "Loaded"};

    private WatcherStatus(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return TYPE_NAME[type];
    }
}
