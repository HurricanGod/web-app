package service;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NginxConfigFileRpcProviderTest {

    private static Logger logger = LogManager.getLogger(NginxConfigFileRpcProviderTest.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.59.66");
        factory.setUsername("mqadmin");
        factory.setPassword("hurrican");
        factory.setVirtualHost("rpc_master");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
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
                consumer.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
