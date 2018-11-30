package cn.hurrican.rabbitmq.producer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static Logger logger = LogManager.getLogger(ProducerService.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendData(String key, Object obj) {
        amqpTemplate.convertAndSend("mq-exchange", key, obj);
    }


    public void sendDataToFontOutExchange(String exchangeName, Object obj) {
        amqpTemplate.convertAndSend(exchangeName, null, obj);
    }



    public void sendDataToTopicExchange(String exchangeName, Object obj, String routeKeyName){
        amqpTemplate.convertAndSend(exchangeName, routeKeyName, obj);
        System.out.println("send msg to topic exchange");
    }
}
