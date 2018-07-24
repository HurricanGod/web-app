package cn.hurrican.rabbitmq.test.service;

import cn.hurrican.rabbitmq.consumer.service.InfoLogQueueListener;
import cn.hurrican.rabbitmq.consumer.service.PublishSubscribeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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

    @Autowired
    private PublishSubscribeService publishSubscribeService;

    @Autowired
    @Qualifier(value = "infoLogListener")
    private InfoLogQueueListener infoLogQueueListener;

    @Autowired
    private List<ChannelAwareMessageListener> list;

    @Test
    public void testConsume() throws InterruptedException {
        Thread.sleep(15000);
        System.out.println("finish...");
    }


    @Test
    public void testFanoutConsume() throws InterruptedException {
        if (list != null) {
            System.out.println("list.size() = " + list.size());
            list.forEach(l -> System.out.println(l.getClass()));
        }
        Thread.sleep(15000);
        System.out.println("finish.........");
    }

    @Test
    public void dynamicCreateFanoutConsume(){
        for (int i = 0; i < 10; i++) {
            publishSubscribeService.subscribeMessage("fanout1", false, true,
                    "queue" + i, true, false, false);
        }
    }


    @Test
    public void testConsumeNonDurableQueue() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            publishSubscribeService.receiveMessage("queue" + i, infoLogQueueListener);
        }
        Thread.sleep(30000);
        System.out.println("End........");
    }
}
