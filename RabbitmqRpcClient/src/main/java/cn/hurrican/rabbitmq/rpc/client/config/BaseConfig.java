package cn.hurrican.rabbitmq.rpc.client.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = "cn.hurrican.rabbitmq.rpc.client*")
@PropertySource(value = "classpath:rabbitmq.properties")
public class BaseConfig {

    @Autowired
    Environment rabbitEnv;


    @Bean(name = "connectionFactory")
    CachingConnectionFactory initConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(rabbitEnv.getProperty("rabbit.virtual.host"));
        connectionFactory.setHost(rabbitEnv.getProperty("rabbit.hosts"));
        connectionFactory.setPort(rabbitEnv.getProperty("rabbit.port", Integer.class));
        connectionFactory.setUsername(rabbitEnv.getProperty("rabbit.username"));
        connectionFactory.setPassword(rabbitEnv.getProperty("rabbit.password"));
        return connectionFactory;

    }

    @Bean(name = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Connection getConnection(CachingConnectionFactory connectionFactory) {
        return connectionFactory.createConnection();
    }

}
