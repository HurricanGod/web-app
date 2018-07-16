package cn.hurrican.controller;

import cn.hurrican.model.QueryResult;
import cn.hurrican.model.ResMessage;
import cn.hurrican.model.UniqueKeyElement;
import cn.hurrican.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
