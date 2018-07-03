package cn.hurrican.anotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Verify {

    boolean needVerifySonething() default false;
}
