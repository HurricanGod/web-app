package cn.hurrican.annotation;

import cn.hurrican.config.CacheConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/30
 * @Modified 18:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DelCache {

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


    Class<?> notifyEvent() default Object.class;

}
