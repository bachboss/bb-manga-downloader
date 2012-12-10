/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.gui.model;

import bbmangadownloader.entity.Server;
import bbmangadownloader.faces.ServerManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

/**
 *
 * @author Bach
 */
public class ScannerComboboxModel extends ListComboBoxModel<Server> {

    private static final Map<String, Server> mapScaner = ServerManager.getMapScannerClone();
    private static final List<Server> listScanerWrapper;

    static {
        listScanerWrapper = new ArrayList<Server>(mapScaner.values());
    }

    public ScannerComboboxModel() {
        super(listScanerWrapper);
    }

    private ScannerComboboxModel(List<Server> list) {
        super(list);
    }
}
