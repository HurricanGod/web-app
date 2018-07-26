package cn.hurrican.service;

import cn.hurrican.annotation.CacheValue;
import cn.hurrican.annotation.KeyParam;
import cn.hurrican.annotation.WriteCache;
import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @WriteCache(type = KeyType.LIST, prefixKey = "type:list:",
            postfixKey = "aid:platformId", pattern = CacheConstant.APPEND_WRITE)
    public void cacheList(@KeyParam("platformId")Integer platformId,
                          @KeyParam("aid") Integer aid,
                          @CacheValue(type = List.class)List<String> value){

    }
}
