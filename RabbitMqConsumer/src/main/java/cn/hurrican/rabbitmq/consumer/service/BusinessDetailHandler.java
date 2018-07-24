package cn.hurrican.rabbitmq.consumer.service;

import org.springframework.amqp.core.Message;

public interface BusinessDetailHandler {

    void invoke(Message message);


    default boolean supportClass(String className) {
        return false;
    }
}
