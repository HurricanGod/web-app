package cn.hurrican.config;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/26
 * @Modified 16:33
 */
@Data
@Accessors(chain = true)
@ToString
public class CacheBean {


    private Class type;
    private Object value;
    private String field;
    private Double scores;

    public static CacheBean build(){
        return new CacheBean();
    }
}
