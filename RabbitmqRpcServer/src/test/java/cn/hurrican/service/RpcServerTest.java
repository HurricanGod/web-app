package cn.hurrican.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/9/13
 * @Modified 17:11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RpcServerTest {

    @Autowired
    private Connection connection;


    @Test
    public void startRpcService() throws IOException {
        Channel channel = connection.createChannel(false);
        String queue = "getConfigFileContent";
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queue, false, false, false, null);
        // clear queue
        channel.queuePurge(queue);
        // to comment
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                try {
                    String s = new String(body, "UTF-8");
                    System.out.println("s = " + s);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    channel.basicPublish("", properties.getReplyTo(), replyProps,
                            "Pong".getBytes("UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);

                    synchronized (this) {
                        this.notifyAll();
                    }
                }

            }
        };

        channel.basicConsume(queue, false, consumer);

        while (true) {
            try {
                synchronized (consumer){
                    consumer.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
