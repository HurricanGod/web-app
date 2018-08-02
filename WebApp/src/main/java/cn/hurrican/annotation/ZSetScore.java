package cn.hurrican.annotation;

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
     * Redis sorted set 命令需提供的参数 <br/>
     * <ul>
     *     <li>CacheConstant.LEFT_INDEX 用于指定 start 参数</li>
     *     <li>CacheConstant.RIGHT_INDEX 用于指定 end 参数</li>
     *     <li>CacheConstant.MIN_SCORE 用于指定 min_score 参数</li>
     *     <li>CacheConstant.MAX_SCORE 用于指定 max_score 参数</li>
     * </ul>
     */
    int param();

}
