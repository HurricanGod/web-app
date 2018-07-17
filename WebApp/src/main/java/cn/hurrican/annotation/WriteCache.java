package cn.hurrican.annotation;

import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;

import java.lang.annotation.*;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 17:28
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WriteCache {

    /**
     * 缓存 key 所属的类型
     * @return
     */
    int type() default KeyType.CHAR_STRING;

    /**
     * 缓存有效时间，单位：秒
     * @return
     */
    int expire() default 3600;

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


    int enterQueueWay() default CacheConstant.RPUSH;


    double score() default 0;

    int index() default 0;

    String field() default CacheConstant.UNDEFINED_NAME;

    String[] fields() default {};
}
