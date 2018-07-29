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
 * @Modified 11:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface ZSetScore {

    /**
     * 参数类型，默认为 Double <br/>
     * 若要使用 Jedis.zadd(final String key, final Map<String, Double> scoreMembers)往  <br/>
     * sorted set 添加多个元素，可以将 clazz 设置为Map.class
     *
     * @return
     */
    Class<?> clazz() default Double.class;

    /**
     * 使用 zrange()时用于指定是 start 还是 size
     *
     * @return
     */
    int rangeType() default CacheConstant.LEFT_INDEX;

    /**
     * 使用 zrangeByScore() 时,用于指定是 min_score 还是 max_score
     *
     * @return
     */
    int scoreRange() default CacheConstant.MIN_SCORE;

    /**
     * 对排序集进行查询时指定是顺序还是逆序 <br/>
     * 若为 CacheConstant.ASC，进行的操作诸如 instance.zrange()  ...<br/>
     * 若为 CacheConstant.DESC，进行的操作诸如 instance.zrevrange()  ...
     *
     * @return
     */
    int order() default CacheConstant.ASC;
}
