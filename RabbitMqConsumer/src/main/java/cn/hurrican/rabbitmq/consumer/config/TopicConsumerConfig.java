package cn.hurrican.rabbitmq.consumer.config;

import cn.hurrican.rabbitmq.consumer.service.ExceptionListener;
import cn.hurrican.rabbitmq.consumer.utils.FastJsonMessageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Arrays;
import java.util.Collections;
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

    // precondition_failed
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
        Queue[] itemQueue = new Queue[queues.length];
        int index = 0;
        if(queueString != null){
            String[] queueNameArray = queueString.split(",");
            if(queueNameArray.length > 0 && queues.length > 0){
                Set<String> itemQueueNameSet = Arrays.stream(queueNameArray).collect(Collectors.toSet());
                for (int i = 0; i < queues.length; i++) {
                    logger.info("queue.name = {}", queues[i].getName());
                    if(itemQueueNameSet.contains(queues[i].getName())){
                        itemQueue[index++] = queues[i];
                    }
                }
            }
        }
        container.setQueues(itemQueue);
        container.setConsumerArguments(Collections.singletonMap("x-priority", 10));
        Integer concurrentConsumers = env.getProperty("rabbitmq.concurrent.consumer.count", Integer.class, 2);
        container.setConcurrentConsumers(concurrentConsumers);
        container.setMaxConcurrentConsumers(concurrentConsumers + 5);
        return container;
    }



}
