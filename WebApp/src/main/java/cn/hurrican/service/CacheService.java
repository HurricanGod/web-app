package cn.hurrican.service;

import cn.hurrican.annotation.*;
import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;
import cn.hurrican.model.Entry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @WriteCache(type = KeyType.LIST, prefixKey = "type:list:", postfixKey = "aid:platformId", pattern = CacheConstant.DEL_WRITE)
    public void cacheList(@KeyParam("platformId")Integer platformId,
                          @KeyParam("aid") Integer aid,
                          @CacheValue(type = List.class)List<Entry> value){

    }


    @ReadCache(type = KeyType.LIST, prefixKey = "type:list:", postfixKey = "aid:platformId", clazz = Entry.class)
    public List<Entry> readCacheValue(@KeyParam("platformId") Integer platformId,
                                      @KeyParam("aid") Integer aid,
                                      @ListIndex Integer lindex,
                                      @ListIndex(indexType = CacheConstant.RIGHT_INDEX) Integer rindex) {

        return null;
    }


    @WriteCache(type = KeyType.HASH, prefixKey = "type:hash:", postfixKey = "aid:platformId")
    public void cacheMap(@KeyParam("platformId") Integer platformId,
                         @KeyParam("aid") Integer aid,
                         @CacheValue(type = Map.class) Map<String,Integer> map){

    }

    @ReadCache(type = KeyType.HASH, prefixKey = "type:hash:", postfixKey = "aid:platformId", clazz = Integer.class)
    public Map<String, Integer> readMapFromCache(@KeyParam("platformId") Integer platformId,
                                                 @KeyParam("aid") Integer aid,
                                                 @HashField(clazz = List.class) List<String> fieldList){
        return null;
    }

    @WriteCache(type = KeyType.HASH, prefixKey = "type:hash:", postfixKey = "aid:platformId")
    public void addOneFieldToMap(@KeyParam("platformId") Integer platformId,
                                 @KeyParam("aid") Integer aid,
                                 @HashField String field,
                                 @CacheValue Object value){

    }
}
