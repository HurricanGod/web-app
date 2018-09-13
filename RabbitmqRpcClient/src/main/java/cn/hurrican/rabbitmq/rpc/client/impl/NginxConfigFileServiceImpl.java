package cn.hurrican.rabbitmq.rpc.client.impl;

import cn.hurrican.rabbitmq.rpc.client.entity.NginxConfigVo;
import cn.hurrican.rabbitmq.rpc.client.service.NginxConfigFileService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class NginxConfigFileServiceImpl implements NginxConfigFileService {

    private static Logger logger = LogManager.getLogger(NginxConfigFileServiceImpl.class);

    @Autowired
    private Connection connection;

    @Override
    public NginxConfigVo getConfigFileContent(String ip, String functionName) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        Channel channel = connection.createChannel(false);
        String callbackQueue = channel.queueDeclare().getQueue();


        String correlationId = UUID.randomUUID().toString();
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .correlationId(correlationId).replyTo(callbackQueue).build();


        ArrayBlockingQueue<String> response = new ArrayBlockingQueue<>(1);
        channel.basicPublish("", functionName, basicProperties, ip.getBytes());
        String consumerTag = channel.basicConsume(callbackQueue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(correlationId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        String callbackResult = response.take();
        channel.basicCancel(consumerTag);
        logger.info("callbackResult = " + callbackResult);
        long cost = System.currentTimeMillis() - start;
        logger.info("cost = " + cost + "\n");
        Random random = new Random();
        return new NginxConfigVo().setId(random.nextInt(Integer.MAX_VALUE))
                .setTimestamp(System.currentTimeMillis());
    }


}
