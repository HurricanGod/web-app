package cn.hurrican.config;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 17:37
 */
public interface CacheConstant {

    String UNDEFINED_NAME = "$$undefined$$";

    /** 缓存中的 key 用不过期 **/
    int NEVER_EXPIRE = -1;

    /** 进入 Redis list 的方式 **/
    int RPUSH = 0;

    /** 写缓存的模式，删除后写入 **/
    int DEL_WRITE = 0;

    /** 写缓存的模式，追加的方式写入 **/
    int APPEND_WRITE = 1;

    /** 增强前写入缓存 **/
    int BEFORE = 0;

    /** 增强后写入缓存 **/
    int AFTER = 1;

    int LEFT_INDEX = 0;

    int RIGHT_INDEX = -1;

    /**
     * sorted set 查找时指定结果集为顺序
     */
    int ASC = 0;

    /**
     * sorted set 查找时指定结果集为逆序
     */
    int DESC = 1;

    int MIN_SCORE = 0;

    int MAX_SCORE = -1;

}
