package cn.hurrican.test;


import cn.hurrican.rabbitmq.project.model.UniqueKeyElement;
import cn.hurrican.rabbitmq.project.service.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitMqApplicationContext.xml")
public class RabbitMqStartTest {

    @Autowired
    private ProducerService producerService;

    @Test
    public void testSimpleMessageQueue() throws InterruptedException {
        UniqueKeyElement element = UniqueKeyElement.build().aidIs(1).openidIs("Hurrican");
        Random random = new Random();
        for (int i = 0; i < 60; i++) {
            element.setPlatformId(i);
            producerService.sendData("save_out_log_key", element);
            Thread.sleep(random.nextInt(1000));
        }
    }
}
