/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

/**
 *
 * @author Bach
 */
public class MathUtilities {

    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
