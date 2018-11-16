package cn.hurrican.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/10/19
 * @Modified 15:25
 */
@Data
@Accessors(chain = true)
@ToString
public class SimpleBean {

    private Integer id;

    private String value;

    public SimpleBean(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public SimpleBean() {
    }
}
