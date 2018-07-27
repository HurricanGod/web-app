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


    /** 写缓存的模式，默认追加（主要与集合类型的 key 相关） **/
    int pattern() default CacheConstant.APPEND_WRITE;

    /** 写缓存时机，种类有：被代理方法执行前写入缓存 or 被代理方法执行后写入缓存 **/
    int writeOccasion() default CacheConstant.BEFORE;

}
