package service;


import cn.hurrican.rabbitmq.rpc.client.config.BaseConfig;
import cn.hurrican.rabbitmq.rpc.client.service.NginxConfigFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Import({BaseConfig.class})
public class NginxConfigFileServiceTest {

    @Autowired
    private NginxConfigFileService nginxConfigFileService;


    @Test
    public void testGetConfigFileContent() {
        try {
            nginxConfigFileService.getConfigFileContent("localhost", "getConfigFileContent");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
