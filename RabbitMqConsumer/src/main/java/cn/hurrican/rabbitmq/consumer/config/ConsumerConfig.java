package cn.hurrican.rabbitmq.consumer.config;

import cn.hurrican.rabbitmq.consumer.service.ExceptionListener;
import cn.hurrican.rabbitmq.consumer.utils.FastJsonMessageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
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
public class ConsumerConfig {

    private static Logger logger = LogManager.getLogger(ConsumerConfig.class);


    @Autowired
    Environment env;

    @Bean(name = "cachingConnectionFactory")
    ConnectionFactory initConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(env.getProperty("rabbit.hosts"));
        connectionFactory.setPort(env.getProperty("rabbit.port", Integer.class));
        connectionFactory.setUsername(env.getProperty("rabbit.username"));
        connectionFactory.setPassword(env.getProperty("rabbit.password"));
        return connectionFactory;

    }

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


    @Bean(name = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory cachingConnectionFactory) {
        return new RabbitAdmin(cachingConnectionFactory);
    }

    @Bean(name = "jsonMessageConverter")
    public FastJsonMessageConverter getFastJsonMessageConverter(){
        return new FastJsonMessageConverter();
    }


    @Bean
    public SimpleMessageListenerContainer getLogListenerContainer(ConnectionFactory cachingConnectionFactory, ExceptionListener exceptionListener){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setMessageListener(exceptionListener);
        return container;
    }


    @Bean(name = "messageListenerContainer")
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory cachingConnectionFactory,
                                                                   ChannelAwareMessageListener awardMessageListener, Queue...queues){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(cachingConnectionFactory);

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



    /**
     * AmqpTemplate配置，AmqpTemplate接口定义了发送和接收消息的基本操作
     *
     * @param cachingConnectionFactory 连接工厂
     * @return
     */
    @Bean(name = "rabbitTemplateForAward")
    public AmqpTemplate rabbitTemplate(ConnectionFactory cachingConnectionFactory, FastJsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setExchange("topic_exchange");
        return rabbitTemplate;
    }


}
