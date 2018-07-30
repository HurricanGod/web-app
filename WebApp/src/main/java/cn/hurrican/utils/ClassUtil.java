/**
 * Created on  13-09-09 17:20
 */
package cn.hurrican.utils;

import org.springframework.asm.Type;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 */
public class ClassUtil {

    private static ConcurrentHashMap<Method, String> methodSigMap = new ConcurrentHashMap();


    public static String getShortClassName(String className) {
        if (className == null) {
            return null;
        }
        String[] ss = className.split("\\.");
        StringBuilder sb = new StringBuilder(className.length());
        for (int i = 0; i < ss.length; i++) {
            String s = ss[i];
            if (i != ss.length - 1) {
                sb.append(s.charAt(0)).append('.');
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static Class<?>[] getAllInterfaces(Object obj) {
        Class<?> c = obj.getClass();
        HashSet<Class<?>> s = new HashSet<>();
        do {
            Class<?>[] its = c.getInterfaces();
            Collections.addAll(s, its);
            c = c.getSuperclass();
        } while (c != null);
        return s.toArray(new Class<?>[s.size()]);
    }

    private static void getMethodSig(StringBuilder sb, Method m) {
        sb.append(m.getName());
        sb.append(Type.getType(m).getDescriptor());
    }

    public static String getMethodSig(Method m) {
        String sig = methodSigMap.get(m);
        if (sig != null) {
            return sig;
        } else {
            StringBuilder sb = new StringBuilder();
            getMethodSig(sb, m);
            sig = sb.toString();
            methodSigMap.put(m, sig);
            return sig;
        }
    }


    /**
     * 判断一个类是否实现了 Map 接口
     *
     * @param clazz 类
     * @return
     */
    public static boolean superTypeIsMap(Class clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.equals(Map.class)) {
            return true;
        }
        return Arrays.stream(clazz.getInterfaces()).collect(Collectors.toSet()).contains(Map.class);
    }

    /**
     * 判断一个类是否实现了 Collection 接口
     * @param clazz
     * @return
     */
    public static boolean superTypeIsCollection(Class clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.equals(Collection.class)) {
            return true;
        }
        return Arrays.stream(clazz.getInterfaces()).collect(Collectors.toSet()).contains(Collection.class);
    }
}
