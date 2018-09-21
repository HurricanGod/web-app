package cn.hurrican.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;

import java.util.regex.Pattern;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/9/21
 * @Modified 11:42
 */

public class JsonExtendServiceUtils {

    public static JsonExtendServiceUtils getInstance(){
        return new JsonExtendServiceUtils();
    }

    private OgnlContext context = new OgnlContext();

    private JSONObject json;

    public JsonExtendServiceUtils init(String expand2){
        json = StringUtils.isNotBlank(expand2) ? JSONObject.fromObject(expand2) :new JSONObject();
        context.setRoot(json);
        return this;
    }

    public String getJson(){
        return json != null ? json.toString() : "{}";
    }

    public Object getValue(String qualifiedKey){
        try {
            Object expression = Ognl.parseExpression(qualifiedKey);
            return Ognl.getValue(expression, context, context.getRoot());
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getValue(String qualifiedKey, Class<T> clazz){
        try {
            Object expression = Ognl.parseExpression(qualifiedKey);
            return (T) Ognl.getValue(expression, context, context.getRoot(), clazz);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonExtendServiceUtils setValue(String qualifiedKey, Object value){
        try {
            Object tree = Ognl.parseExpression(qualifiedKey);
            Ognl.setValue(tree, context.getRoot(), value);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return this;
    }

    private static Pattern pattern = Pattern.compile("[1-9][0-9]*");


    public static Long toLong(Object tmpValue){
        return tmpValue == null ? null :
                tmpValue instanceof Integer ? new Long((Integer)tmpValue):
                        tmpValue instanceof Long ? (Long) tmpValue :
                                ((tmpValue instanceof String) && (pattern.matcher((String) tmpValue).find()) )?
                                        Long.valueOf((String) tmpValue):null;
    }


}
