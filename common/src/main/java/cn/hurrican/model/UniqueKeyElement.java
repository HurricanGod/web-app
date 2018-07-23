package cn.hurrican.model;

import cn.hurrican.utils.InstanceUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 17:50
 */
@Data
public class UniqueKeyElement {

    private static HashMap<String, Integer> fieldOrder = new HashMap<>(8);

    static {
        fieldOrder.put("aid", 0);
        fieldOrder.put("platformId", 1);
        fieldOrder.put("openid", 2);
        fieldOrder.put("other", 3);
    }

    private Integer aid;

    private Integer platformId;

    private String openid;

    private String other;

    public static UniqueKeyElement build() {
        return new UniqueKeyElement();
    }

    public UniqueKeyElement aidIs(Integer aid) {
        this.aid = aid;
        return this;
    }


    public UniqueKeyElement platformIdIs(Integer platformId) {
        this.platformId = platformId;
        return this;
    }


    public UniqueKeyElement openidIs(String openid) {
        this.openid = openid;
        return this;
    }

    public UniqueKeyElement otherIs(String other) {
        this.other = other;
        return this;
    }


    public String generatePostfixKey() {
        // key → 非空字段名; value → 值
        Map<String, String> notNullFields = InstanceUtil.getNotNullFields(this);
        String[] orderNotNullField = new String[fieldOrder.size()];
        notNullFields.forEach((k, v) -> {
            Integer index = fieldOrder.get(k);
            orderNotNullField[index] = v;
        });
        return Arrays.stream(orderNotNullField).filter(Objects::nonNull).reduce((x, y) -> x + ":" + y).orElse("");
    }

}
