package cn.hurrican.service;

import cn.hurrican.annotation.ReadCache;
import cn.hurrican.config.CacheBean;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/15
 * @Modified 12:17
 */
public class DelCacheService {

    @ReadCache(clazz = CacheBean.class)
    public void zadd(String key){

    }
}
