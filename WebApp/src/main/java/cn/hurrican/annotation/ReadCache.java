package cn.hurrican.annotation;

import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/27
 * @Modified 9:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ReadCache {

    /** Redis key 类型 **/
    int type() default KeyType.CHAR_STRING;

    /** 方法返回值类型 **/
    Class<?> valueClazz() default String.class;

    /**
     * 缓存 key 前缀
     * @return
     */
    String prefixKey() default CacheConstant.UNDEFINED_NAME;

    /**
     * 缓存 key 后缀
     * @return
     */
    String postfixKey() default "";

}
