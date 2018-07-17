package cn.hurrican.rabbitmq.project.service;

import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

public class SaveOutLogListener implements ChannelAwareMessageListener {

    private static Logger logger = LogManager.getLogger(SaveOutLogListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(message.toString());
        logger.info("消费消息：{}", message.toString());

        // 接收信息
        String json = new String(message.getBody());

        // 确认消息被消费了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("消费完消息向服务端发送消费完成确认");
    }
}
