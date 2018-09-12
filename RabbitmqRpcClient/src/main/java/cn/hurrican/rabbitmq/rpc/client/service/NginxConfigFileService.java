package cn.hurrican.rabbitmq.rpc.client.service;

import cn.hurrican.rabbitmq.rpc.client.entity.NginxConfigVo;

import java.io.IOException;

public interface NginxConfigFileService {

    NginxConfigVo getConfigFileContent(String ip, String functionName) throws IOException, InterruptedException;

}
