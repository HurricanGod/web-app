package cn.hurrican.service;

import cn.hurrican.aop.CacheValue;
import cn.hurrican.aop.KeyParam;
import cn.hurrican.aop.WriteCache;
import cn.hurrican.config.KeyType;
import org.springframework.stereotype.Service;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/16
 * @Modified 9:41
 */
@Service
public class CacheService {

    @WriteCache(type = KeyType.INT_STRING, prefixKey = "type:int_string:", postfixKey = "aid:platformId:openid")
    public void cacheIntString(@KeyParam("aid") Integer aid, @KeyParam("platformId")Integer platformId,
                               @KeyParam("openid")String openid, @CacheValue() Integer value){

    }

    @WriteCache(type = KeyType.INT_STRING, prefixKey = "type:double_string:", postfixKey = "aid:platformId")
    public void cacheDoubleString(@KeyParam("aid") Integer aid, @CacheValue()Double value,
                                  @KeyParam("platformId")Integer platformId){

    }
}
