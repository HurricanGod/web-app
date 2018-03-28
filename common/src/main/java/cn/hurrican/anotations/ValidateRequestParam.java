package cn.hurrican.anotations;

import java.lang.annotation.*;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/3/28
 * @Modified 11:22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateRequestParam {
    Class<?>[] support();

}
