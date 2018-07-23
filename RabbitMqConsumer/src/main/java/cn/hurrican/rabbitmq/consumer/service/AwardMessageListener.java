package cn.hurrican.rabbitmq.consumer.service;

import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 11:00
 */
@Service("awardMessageListener")
public class AwardMessageListener implements ChannelAwareMessageListener {

    private static Logger logger = LogManager.getLogger(AwardMessageListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("exec AwardMessageListener.onMessage()");
        // 接收信息
        String json = new String(message.getBody());
        logger.info("消费消息：{}", json);

        // 确认消息被消费了, MessageProperties.getDeliveryTag() 是 Tag 的 id，由生产者生成
        // 第二个 boolean 参数：false 表示当前一个消息收到， true 表示确认所有 consumer 收到消息了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("消费完消息向服务端发送消费完成确认");
    }
}
