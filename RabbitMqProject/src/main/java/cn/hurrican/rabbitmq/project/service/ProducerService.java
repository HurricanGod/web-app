package cn.hurrican.rabbitmq.project.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendData(String key, Object obj) {
        amqpTemplate.convertAndSend(key, obj);
    }


    public void sendDateToExchange(String exchangeName, Object obj) {
        amqpTemplate.convertAndSend(exchangeName, null, obj);
    }
}
