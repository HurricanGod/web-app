package cn.hurrican.rabbitmq.consumer.service;

import cn.hurrican.model.UniqueKeyElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

@Service("exceptionListener")
public class ExceptionListener implements ChannelAwareMessageListener {

    private static Logger logger = LogManager.getLogger(ExceptionListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("exec ExceptionListener.onMessage()...");
        String json = new String(message.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        UniqueKeyElement uniqueKeyElement = objectMapper.readValue(json, UniqueKeyElement.class);
        System.out.println("uniqueKeyElement = " + uniqueKeyElement);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("消费完消息向服务端发送消费完成确认");
    }
}
