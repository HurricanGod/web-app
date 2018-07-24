package cn.hurrican.test;


import cn.hurrican.model.ResMessage;
import cn.hurrican.model.UniqueKeyElement;
import cn.hurrican.rabbitmq.producer.service.ProducerService;
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
    public void testMultiConsumerMsgQueue() throws InterruptedException {
        UniqueKeyElement element = UniqueKeyElement.build().aidIs(1).openidIs("Hurrican");
        Random random = new Random();
        for (int i = 0; i < 120; i++) {
            element.setPlatformId(i);
            producerService.sendData("save_out_log_key", element);
            Thread.sleep(random.nextInt(1000));
        }
    }

    @Test
    public void testPublishSubscribeMsgQueue() throws InterruptedException {
        UniqueKeyElement element = UniqueKeyElement.build().aidIs(1).openidIs("Hurrican");
        Random random = new Random();
        Integer millis = 0;
        for (int i = 0; i < 10; i++) {
            element.setPlatformId(i);
            producerService.sendDataToFontOutExchange("fanout1", element.platformIdIs(i));
            millis = random.nextInt(1000);
            System.out.println("sleep ${sec} ms".replace("${sec}", millis.toString()));
            Thread.sleep(millis);
        }
    }


    @Test
    public void testTopicPattern() {
        ResMessage msg = ResMessage.creator().msg("test message");
        for (int i = 0; i < 20; i++) {
            msg.setRetCode(i);
            if (i % 2 == 0) {
                producerService.sendDataToTopicExchange("topic_exchange", msg, "awardRecord.abc");
            } else {
                producerService.sendDataToTopicExchange("topic_exchange", msg, "awardRecord.hello.log");
            }
        }
    }


}
