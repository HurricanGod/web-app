package cn.hurrican.rabbitmq.producer.controller;

import cn.hurrican.model.ResMessage;
import cn.hurrican.rabbitmq.producer.service.ProducerService;
import cn.hurrican.utils.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce/")
public class ProduceController {

    private static Logger logger = LogManager.getLogger(ProduceController.class);

    @Autowired
    private ProducerService producerService;


    @RequestMapping(value = "/sendMessage.do", produces = "application/json;charset=UTF-8")
    public ResMessage sendMessage(String msg, String name) {
        ResMessage message = ResMessage.creator();
        JSONObject json = JSONUtils.creator().put("msg", msg).put("name", name).getJson();
        producerService.sendDataToFontOutExchange("", json);
        return message;
    }
}
