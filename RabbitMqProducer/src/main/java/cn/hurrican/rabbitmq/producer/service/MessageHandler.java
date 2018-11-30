package cn.hurrican.rabbitmq.producer.service;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/11/30
 * @Modified 17:23
 */
public interface MessageHandler {


    Object invoke(Object... args);

}
