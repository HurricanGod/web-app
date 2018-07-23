package cn.hurrican.rabbitmq.consumer.config;

import cn.hurrican.rabbitmq.consumer.service.ExceptionListener;
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
    public SimpleMessageListenerContainer getLogListenerContainer(ConnectionFactory cachingConnectionFactory,
                                                                  ExceptionListener exceptionListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setMessageListener(exceptionListener);
        return container;
    }


}
