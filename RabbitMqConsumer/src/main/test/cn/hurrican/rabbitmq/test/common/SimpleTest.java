package cn.hurrican.rabbitmq.test.common;

import cn.hurrican.rabbitmq.consumer.model.AwardMessage;
import cn.hurrican.rabbitmq.consumer.model.GenericMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/25
 * @Modified 16:21
 */
public class SimpleTest {

    @Test
    public void testMethod1() throws IOException {
        GenericMessage message = new AwardMessage().setIndex(1).setPlatformId(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(message);

        Object genericMessage = objectMapper.readValue(s, GenericMessage.class);
        System.out.println(genericMessage.getClass().getName());
        System.out.println(genericMessage);

    }
}
