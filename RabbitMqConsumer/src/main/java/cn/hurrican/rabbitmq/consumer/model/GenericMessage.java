package cn.hurrican.rabbitmq.consumer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 18:00
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class GenericMessage<T> {

    private String className;

    private Integer index;

    private Integer platformId;

    private Integer type;

    private Integer action;

    private T entity;

}
