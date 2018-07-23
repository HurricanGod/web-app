package cn.hurrican.rabbitmq.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 10:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RabbitMqConsumerTest {

    @Autowired
    private AmqpTemplate template;

    @Test
    public void testConsume() throws InterruptedException {
        Thread.sleep(15000);
        System.out.println("finish...");
    }
}
