package cn.hurrican.rabbitmq.consumer.service;

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
        logger.info("异常日志消费者消费信息：{}", json);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
