/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Bach
 */
public class ReflectionUtilities {

    public static Method getStaticMethod(Class c, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method m = c.getDeclaredMethod(methodName, parameterTypes);
        return m;
    }

    public static Method getMethod(Class c, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method m = c.getMethod(methodName, parameterTypes);
        return m;
    }

    public static Object invokeStaticMethod(Method m, Object... parameterData)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return invokeMethod(null, m, parameterData);
    }

    public static Object invokeMethod(Object o, Method m, Object... parameterData)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return m.invoke(o, parameterData);
    }
}
