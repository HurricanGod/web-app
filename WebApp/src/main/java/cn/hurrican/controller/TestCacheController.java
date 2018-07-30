package cn.hurrican.controller;

import cn.hurrican.model.Entry;
import cn.hurrican.model.ResMessage;
import cn.hurrican.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/16
 * @Modified 9:38
 */
@Controller
@RequestMapping("/cache")
public class TestCacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/cacheIntString.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testCacheIntString(){
        ResMessage message = ResMessage.creator();

        cacheService.cacheIntString(100, 0, "Hurrican", 100000);
        return message;
    }


    @RequestMapping(value = "/cacheDoubleString.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testCacheDoubleString(){
        ResMessage message = ResMessage.creator();

        cacheService.cacheDoubleString(100, 1.0, 0);

        return message;
    }

    @RequestMapping(value = "/cacheList.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testCacheList(){
        ResMessage message = ResMessage.creator();

        List<Entry> value = new ArrayList<>();
        value.add(new Entry<>("1", "abc"));
        value.add(new Entry<>("2", "abd"));
        value.add(new Entry<>("3", "abe"));
        value.add(new Entry<>("4", "abf"));
        value.add(new Entry<>("5", "abg"));
        cacheService.cacheList(1, 101, value);

        return message;
    }


    @RequestMapping(value = "/readList.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testReadListFromCache(){
        ResMessage message = ResMessage.creator();

        List<Entry> entries = cacheService.readCacheValue(1, 101, 0, 2);
        message.put("entries", entries);

        return message;
    }


    @RequestMapping(value = "/cacheMultiField.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testCacheMapForManyField(){
        ResMessage message = ResMessage.creator();

        HashMap<String, Integer> map = new HashMap<>(8);
        map.put("age", 20);
        map.put("height", 180);
        map.put("width", 0);
       cacheService.cacheMap(0, 10086, map);

        return message;
    }

    @RequestMapping(value = "/cacheOneField.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testOneFieldToMap(String field, String value){
        ResMessage message = ResMessage.creator();
        cacheService.addOneFieldToMap(0, 10086, field, value);
        return message.msg("ok");
    }

    @RequestMapping(value = "/readMultiField.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage testReadMultiFieldFromCache(String field){
        ResMessage message = ResMessage.creator();
        ArrayList<String> list = new ArrayList<>();
        list.add("age");
        list.add("height");
        list.add("width");
        if(field != null){
            list.add(field);
        }

        Map<String, Integer> map = cacheService.readMapFromCache(0, 10086, list);
        message.put("map", map);
        return message;
    }
}
