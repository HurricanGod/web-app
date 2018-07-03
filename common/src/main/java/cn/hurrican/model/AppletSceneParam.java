package cn.hurrican.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class AppletSceneParam {

    private AppletSceneParam() {

    }

    private Integer uid;

    private String className;

    private Object instance;

    public static AppletSceneParam init(String className, Map<String, Object> properties) {
        if (className == null) {
            return null;
        }
        try {
            Class<?> obj = Class.forName(className);
            Object instance = obj.newInstance();
            List<Method> methods = Arrays.stream(obj.getMethods())
                    .filter(m -> m.getName().contains("set"))
                    .collect(Collectors.toList());
            Map<String, Method> methodMap = new HashMap<>(properties.size());
            methods.forEach(m -> {
                String name = m.getName();
                int lindex = name.indexOf("set") + 3;
                String property = name.substring(lindex, name.length()).toLowerCase();
                methodMap.put(property, m);
            });
            for (Map.Entry<String, Method> entry : methodMap.entrySet()) {
                try {
                    entry.getValue().invoke(instance, properties.get(entry.getKey()));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            AppletSceneParam appletSceneParam = new AppletSceneParam();
            appletSceneParam.setClassName(className);
            appletSceneParam.setInstance(instance);
            return appletSceneParam;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
