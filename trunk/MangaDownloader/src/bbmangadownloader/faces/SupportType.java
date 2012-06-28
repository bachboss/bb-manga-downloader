/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangadownloader.faces;

/**
 *
 * @author Bach
 */
public enum SupportType {

    NotSupport(0), PartlySupport(1), Support(2);
    private int type;
    private static final String[] TYPE_NAME = {"Not Support", "Partly Support", "Support"};

    private SupportType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return TYPE_NAME[type];
    }
}
