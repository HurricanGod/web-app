package cn.hurrican.service;

import cn.hurrican.annotation.LotteryEnhance;
import cn.hurrican.model.ResMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/9/4
 * @Modified 15:46
 */

@Service
public class LotteryService {

    private static Logger logger = LogManager.getLogger(LotteryService.class);

    @LotteryEnhance
    public ResMessage doLottery(){
        System.out.println("exec no parameter doLottery method");
        return ResMessage.creator().msg("no parameter doLottery method!");
    }

//    @LotteryEnhance
    public ResMessage doLottery(String openid){
        System.out.println("exec doLottery method for one args");
        return doLottery(openid, new HashMap<>(4));
    }

    @LotteryEnhance
    public ResMessage doLottery(String openid, Map<String, Object> param){
        System.out.println("exec two args doLottery method!");
        return doLottery(openid, param, false);
    }


    @LotteryEnhance
    public ResMessage doLottery(String openid, Map<String, Object> param, boolean isJudge){
        System.out.println("exec three args doLottery method!");
        return ResMessage.creator();
    }
}
