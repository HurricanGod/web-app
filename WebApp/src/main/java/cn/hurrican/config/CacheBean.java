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


    /** 缓存对象类型 **/
    private Class type;
    private Object value;
    private String field;
    private Double scores;

    private Integer lindex;
    private Integer rindex;

    public static CacheBean build(){
        return new CacheBean();
    }
}
