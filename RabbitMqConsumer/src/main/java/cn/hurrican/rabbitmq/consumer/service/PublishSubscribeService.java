package cn.hurrican.rabbitmq.consumer.service;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/24
 * @Modified 10:34
 */
@Service
public class PublishSubscribeService {

    @Autowired
    @Qualifier(value = "cachingConnectionFactory")
    private CachingConnectionFactory connectionFactory;

    @Autowired
    @Qualifier(value = "basicListenerContainer")
    private SimpleMessageListenerContainer basicListenerContainer;


    public void subscribeMessage(String exchangeName, Boolean durableOfExchange, Boolean autoDeleteOfExchange,
                                 String queueName, Boolean autoDeleteOfQueue, Boolean durableOfQueue, Boolean exclusive){
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        try {
            Map<String, Object> arguments = new HashMap<>(4);
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, durableOfExchange, autoDeleteOfExchange, arguments);
            channel.queueDeclare(queueName, durableOfQueue, exclusive, autoDeleteOfQueue, null);
            channel.queueBind(queueName, exchangeName, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(String queueName, ChannelAwareMessageListener listener){
        Queue queue = new Queue(queueName, false, false, true);
        basicListenerContainer.setQueues(queue);
        basicListenerContainer.setMessageListener(listener);
    }

}
