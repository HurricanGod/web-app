package cn.hurrican.rabbitmq.project.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 18:00
 */
public class InstanceUtil {

    /**
     * 获取一个对象非空静态属性
     *
     * @param obj
     * @return
     */
    public static Map<String, String> getNotNullFields(Object obj) {
        HashMap<String, String> map = new HashMap<>(8);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object fieldValue = field.get(obj);
                    if (fieldValue != null) {
                        map.put(field.getName(), fieldValue.toString());
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
