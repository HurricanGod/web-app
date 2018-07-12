package cn.hurrican.aop;

import java.lang.annotation.*;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 15:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableCache {
}
