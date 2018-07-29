package cn.hurrican.controller;

import cn.hurrican.model.Entry;
import cn.hurrican.model.ResMessage;
import cn.hurrican.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
}
