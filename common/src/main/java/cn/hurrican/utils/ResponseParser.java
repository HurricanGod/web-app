package cn.hurrican.utils;

import net.sf.json.JSONObject;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/31
 * @Modified 14:58
 */
public interface ResponseParser{

    JSONObject parse(String response);
}
