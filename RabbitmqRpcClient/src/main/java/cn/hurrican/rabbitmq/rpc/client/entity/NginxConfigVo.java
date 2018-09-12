package cn.hurrican.rabbitmq.rpc.client.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NginxConfigVo {

    private Integer id;

    private Long timestamp;

}
