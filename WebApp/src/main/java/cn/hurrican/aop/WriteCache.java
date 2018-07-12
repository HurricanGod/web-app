package cn.hurrican.aop;

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

    int type() default KeyType.CHAR_STRING;

    int expire() default 3600;

    String keyPrefix() default CacheConstant.UNDEFINED_NAME;

    double score() default 0;

    int index() default 0;

    String field() default CacheConstant.UNDEFINED_NAME;

    String[] fields() default {};
}
