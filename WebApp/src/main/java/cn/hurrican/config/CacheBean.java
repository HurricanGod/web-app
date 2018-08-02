package cn.hurrican.config;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/26
 * @Modified 16:33
 */
@Data
@Accessors(chain = true)
@ToString
public class CacheBean {


    /**
     * 缓存Java实例的类型
     **/
    private Class type;

    /**
     * 缓存到 Redis 中的 key
     */
    private String key;

    /**
     * 要放入缓存的Java实例
     **/
    private Object value;

    /**
     * 指定要存取 Redis hash 存储结构的字段
     **/
    private String[] field;

    private Integer lindex;

    private Integer rindex;

    /**
     * 对 Redis 中的 sorted set 进行存取时指定的 score
     **/
    private Double scores;

    private Double minScore;

    private Double maxScore;

    public static CacheBean build() {
        return new CacheBean();
    }
}
