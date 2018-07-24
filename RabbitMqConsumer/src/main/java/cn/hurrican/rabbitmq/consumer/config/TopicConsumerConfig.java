package cn.hurrican.rabbitmq.consumer.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 11:00
 */
@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class TopicConsumerConfig {

    private static Logger logger = LogManager.getLogger(TopicConsumerConfig.class);

    @Autowired
    Environment env;


    @Bean(name = "persistenceQueueForAward")
    Queue getPersistenceQueueForAward() {
        return new Queue(env.getProperty("rabbitmq.persistence.queue.for.award"),
                true, false, false);
    }

    @Bean(name = "persistenceQueueForLog")
    Queue getPersistenceQueueForLog(){
        return new Queue(env.getProperty("rabbitmq.persistence.queue.for.log"),
                true, false, false);
    }

    @Bean(name = "topicExchange")
    TopicExchange initPersistenceTopicExchange() {
        return new TopicExchange(env.getProperty("rabbitmq.persistence.exchange.for.award"),
                true, false, null);
    }

    @Bean(name = "bindingToAwardQueue")
    Binding bindingExchangeAndAwardQueue(Queue persistenceQueueForAward, TopicExchange topicExchange) {
        return BindingBuilder.bind(persistenceQueueForAward).to(topicExchange).with(env.getProperty("rabbitmq.topic.exchange.awardrecord.queue.pattern"));
    }

    @Bean(name = "bindingToLogQueue")
    Binding bindingExchangeAndLogQueue(Queue persistenceQueueForLog, TopicExchange topicExchange) {
        return BindingBuilder.bind(persistenceQueueForLog).to(topicExchange).with(env.getProperty("rabbitmq.topic.exchange.logqueue.pattern"));
    }






    @Bean(name = "messageListenerContainer")
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory cachingConnectionFactory,
                                                                   ChannelAwareMessageListener awardMessageListener, Queue...queues){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(cachingConnectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        container.setMessageListener(awardMessageListener);
        String queueString = env.getProperty("rabbitmq.topic.pattern.listen.queue.list");
        List<Queue> itemQueueList = new ArrayList<>(queues.length);
        if(queueString != null){
            String[] queueNameArray = queueString.split(",");
            if(queueNameArray.length > 0 && queues.length > 0){
                Set<String> itemQueueNameSet = Arrays.stream(queueNameArray).collect(Collectors.toSet());
                itemQueueList = Arrays.stream(queues).filter(q -> itemQueueNameSet.contains(q.getName())).collect(Collectors.toList());
            }
        }
        Queue[] itemQueue = new Queue[itemQueueList.size()];
        container.setQueues(itemQueueList.toArray(itemQueue));
        container.setConsumerArguments(Collections.singletonMap("x-priority", 10));
        Integer concurrentConsumers = env.getProperty("rabbitmq.concurrent.consumer.count", Integer.class, 2);
        container.setConcurrentConsumers(concurrentConsumers);
        container.setMaxConcurrentConsumers(concurrentConsumers + 5);
        return container;
    }



}
