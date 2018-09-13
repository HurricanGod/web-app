package cn.hurrican.rabbitmq.rpc.client.impl;

import cn.hurrican.rabbitmq.rpc.client.service.QueueManageService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QueueManageServiceImpl implements QueueManageService {

    @Autowired
    private Connection connection;


    @Override
    public String rpcCallbackQueue() {
        Channel channel = connection.createChannel(false);
        try {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare("rpc_callback", false, false, false, null);
            return declareOk.getQueue();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
