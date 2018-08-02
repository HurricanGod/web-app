package cn.hurrican.annotation;

import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/27
 * @Modified 9:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ReadCache {

    /**
     * Redis key 类型,详情见 {@link KeyType}
     **/
    int type() default KeyType.CHAR_STRING;

    /**
     * 被代理方法返回值的最小类型，默认为 String.class <br/>
     * <b>最小类型：</b>
     * <ul>
     * <li>若方法返回类型不是集合类型，则该类型为最小类型</li>
     * <li>若方法返回类型为集合类型，则最小类型为集合元素的类型</li>
     * </ul>
     * <b>例如：</b><br/>
     * 返回类型为 List &lt User &gt ，则最小类型为 User.class
     * @return 被代理方法返回值的最小类型，默认为 String.class
     */
    Class<?> clazz() default String.class;

    /**
     * Redis key 前缀
     * @return
     */
    String prefixKey() default CacheConstant.UNDEFINED_NAME;

    /**
     * Redis key 后缀
     * @return
     */
    String postfixKey() default "";

    /**- - - - - - - - - - - - - Sorted Set 单独使用 - - - - - - - - - - - - -**/
    /**
     * 对排序集进行查询时指定是顺序还是逆序 <br/>
     * 若为 CacheConstant.ASC，进行的操作诸如 instance.zrange()  ...<br/>
     * 若为 CacheConstant.DESC，进行的操作诸如 instance.zrevrange()  ...
     *
     * @return
     */
    int order() default CacheConstant.ASC;

    /**
     * Redis Sorted Set 指令
     * {@link SortedSetInstruct}
     *
     * @return
     */
    int instruct() default 0;

}
