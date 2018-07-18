package cn.hurrican.test;


import cn.hurrican.rabbitmq.project.model.UniqueKeyElement;
import cn.hurrican.rabbitmq.project.service.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitMqApplicationContext.xml")
public class RabbitMqStartTest {

    @Autowired
    private ProducerService producerService;

    @Test
    public void testSimpleMessageQueue() {
        UniqueKeyElement element = UniqueKeyElement.build().aidIs(1).openidIs("Hurrican");
        producerService.sendData("save_out_log_key", element);
    }
}
