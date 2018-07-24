package cn.hurrican.rabbitmq.consumer.service;

import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

/**
 * @author Hurrican
 */
@Service("infoLogListener")
public class InfoLogQueueListener implements ChannelAwareMessageListener {

    private static Logger logger = LogManager.getLogger(InfoLogQueueListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("exec InfoLogQueueListener.onMessage()");

        // 接收信息
        String json = new String(message.getBody());
        logger.info("消费消息：{}", json);
        System.out.println("消费消息："  + json);

        // 确认消息被消费了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        logger.info("消费完消息向服务端发送消费完成确认");
    }
}
