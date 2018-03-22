package cn.hurrican.anotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    int level() default 0;

    String table() default "controller_error_log";
}
