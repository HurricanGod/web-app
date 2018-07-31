package cn.hurrican.annotation;


import cn.hurrican.config.CacheConstant;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlushCache {

    /**
     * Java 实例的最小类型，默认为 String.class <br/>
     * <b>最小类型：</b>
     * <ul>
     * <li>若待更新的对象为基本Java类型对应的包装类实例或String实例，则最小类型为对应的包装类或String</li>
     * <li>若待更新的对象为集合类型，则最小类型为集合元素的类型</li>
     * </ul>
     *
     * @return
     */
    Class<?> valueType() default String.class;


    /**
     * 缓存 key 前缀
     *
     * @return
     */
    String prefixKey() default CacheConstant.UNDEFINED_NAME;

    /**
     * 缓存 key 后缀
     *
     * @return
     */
    String postfixKey() default "";

}
