package cn.hurrican.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/31
 * @Modified 15:00
 */

public class JsonResponseParser implements ResponseParser {
    @Override
    public JSONObject parse(String response) {
        if(StringUtils.isNotBlank(response)){
            return JSONObject.fromObject(response);
        }else{
            return new JSONObject();
        }
    }
}
