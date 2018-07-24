package cn.hurrican.rabbitmq.consumer.config;

import cn.hurrican.rabbitmq.consumer.service.ExceptionListener;
import cn.hurrican.rabbitmq.consumer.service.InfoLogQueueListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 11:00
 */
@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class FanoutConsumerConfig {

    @Autowired
    Environment env;

    @Bean(name = "fanoutExchangeForException")
    public FanoutExchange initExceptionFanoutExchange() {
        String exchangeName = env.getProperty("rabbitmq.fanout.exchange.name.for.exception");
        return new FanoutExchange(exchangeName, true, false);
    }

    @Bean(name = "exceptionQueue")
    public Queue initExceptionQueue() {
        String queueName = env.getProperty("rabbitmq.fanout.exchange.queue.for.exception");
        return new Queue(queueName, true, false, false);
    }


    @Bean(name = "infoQueue")
    public Queue initInfoQueue() {
        String queueName = env.getProperty("rabbitmq.fanout.exchange.queue.for.info");
        return new Queue(queueName, true, false, false);
    }

    @Bean(name = "exceptionQueueBinding")
    public Binding initExceptionQueueBinding(FanoutExchange fanoutExchangeForException, Queue exceptionQueue) {
        return BindingBuilder.bind(exceptionQueue).to(fanoutExchangeForException);
    }


    @Bean(name = "infoQueueBinding")
    public Binding initInfoQueueBinding(FanoutExchange fanoutExchangeForException, Queue infoQueue) {
        return BindingBuilder.bind(infoQueue).to(fanoutExchangeForException);
    }


    @Bean
    public SimpleMessageListenerContainer infoLogConsumerListenerContainer(ConnectionFactory cachingConnectionFactory,
                                                                           InfoLogQueueListener infoLogListener, Queue infoQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setMessageListener(infoLogListener);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(infoQueue);
        container.setConcurrentConsumers(2);
        container.setMaxConcurrentConsumers(5);
        return container;
    }


    @Bean
    public SimpleMessageListenerContainer exceptionLogConsumerListenerContainer(ConnectionFactory cachingConnectionFactory,
                                                                                ExceptionListener exceptionListener, Queue exceptionQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setConcurrentConsumers(2);
        container.setMaxConcurrentConsumers(5);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(exceptionListener);
        container.setQueues(exceptionQueue);
        return container;
    }


}
