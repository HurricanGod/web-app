package cn.hurrican.model;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.ExtensionMethod;

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

    private Integer platformId;


    private Integer type;

    private Integer action;

    private T entity;

    private String genericTypeClassName;
}
