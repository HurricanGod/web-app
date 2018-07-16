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
 * @Date 2018/7/16
 * @Modified 9:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface KeyParam {
    String value() default CacheConstant.UNDEFINED_NAME;
}
