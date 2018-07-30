package cn.hurrican.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/16
 * @Modified 11:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface HashField {

    /**
     * clazz 为 List.class 表示读缓存时指定的多个 field <br/>
     * clazz 为 String.class 表示读或写缓存时指定的field <br/>
     * @return
     */
    Class clazz() default String.class;
}
