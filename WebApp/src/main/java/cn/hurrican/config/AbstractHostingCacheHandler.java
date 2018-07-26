package cn.hurrican.config;

/**
 * @Author: Hurrican
 * @Description: 未设置过期时间的缓存处理器
 * @Date 2018/7/26
 * @Modified 17:39
 */
public abstract class AbstractHostingCacheHandler {

    /**
     * 处理未设置过期时间的 key
     * @param key Redis key
     */
    public abstract void handleNeverExpireKey(String key);
}
