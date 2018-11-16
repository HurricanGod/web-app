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

    private static  Pattern pattern = Pattern.compile("[1-9][0-9]*");


    public static JsonExtendServiceUtils getInstance(String json){
        return new JsonExtendServiceUtils().init(json);
    }


    private OgnlContext context = new OgnlContext();

    private JSONObject json;

    private Object tmpValue;

    public JsonExtendServiceUtils init(String expand2){
        json = StringUtils.isNotBlank(expand2) ? JSONObject.fromObject(expand2) :new JSONObject();
        context.setRoot(json);
        return this;
    }

    public String getJson(){
        return json != null ? json.toString() : "{}";
    }

    /**
     * 根据全限定 key 获取 value
     * @param qualifiedKey 全限定 key,形式为： <b> model.activity</b>
     * @return
     */
    public Object getValue(String qualifiedKey){
        try {
            Object expression = Ognl.parseExpression(qualifiedKey);
            return Ognl.getValue(expression, context, context.getRoot());
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonExtendServiceUtils getSaftValue(String qualifiedKey){
        tmpValue = getValue(qualifiedKey);
        return this;
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

    private void prepareSetValue(String[] ele){
        if(ele.length <= 1){
            return;
        }
        String key = null;
        for (int i = 0, size = ele.length - 1; i < size; i++) {
            String[] dest = new String[i+1];
            System.arraycopy(ele, 0, dest, 0, i+1);
            key = i == 0 ? dest[i] : StringUtils.join(dest, '.');
            System.out.println("key = " + key);
            try {
                Object tree = Ognl.parseExpression(key);
                Object oldValue = Ognl.getValue(tree, context, context.getRoot());
                if(oldValue == null){
                    Ognl.setValue(tree, context.getRoot(), new JSONObject());
                }
            } catch (OgnlException e) {
                e.printStackTrace();
            }
        }

    }

    public JsonExtendServiceUtils setValue(String qualifiedKey, Object value){
        if(StringUtils.isBlank(qualifiedKey)){
            throw new RuntimeException("param qualifiedKey must not blank!");
        }
        try {
            String[] split = qualifiedKey.split("\\.");
            Object tree = Ognl.parseExpression(qualifiedKey);
            prepareSetValue(split);
            Ognl.setValue(tree, context.getRoot(), value);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Long toLong(){
        return tmpValue == null ? null :
                tmpValue instanceof Integer ? new Long((Integer)tmpValue):
                        tmpValue instanceof Long ? (Long) tmpValue :
                                tmpValue instanceof String  && pattern.matcher((String) tmpValue).find()?
                                        Long.valueOf((String) tmpValue):null;
    }

    public String toStr(){
        return (String)tmpValue;
    }



}
