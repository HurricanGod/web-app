package cn.hurrican.rabbitmq.consumer.config;

import cn.hurrican.rabbitmq.consumer.utils.FastJsonMessageConverter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class ConsumerBaseConfig {

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


    @Bean(name = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory cachingConnectionFactory) {
        return new RabbitAdmin(cachingConnectionFactory);
    }

    @Bean(name = "jsonMessageConverter")
    public FastJsonMessageConverter getFastJsonMessageConverter() {
        return new FastJsonMessageConverter();
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
        return rabbitTemplate;
    }


}
