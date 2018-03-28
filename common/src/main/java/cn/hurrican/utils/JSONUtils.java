package cn.hurrican.utils;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONUtils {

    private JSONObject json = new JSONObject();

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }



    public static JSONUtils creator() {
	    return new JSONUtils();
    }

    public static JSONUtils creator(String content) {
	    JSONUtils util = new JSONUtils();
	    if (StringUtils.isNotBlank(content)) {
		    util.setJson(JSONObject.fromObject(content));
        }
	    return util;
    }

	/**
	 * 创建JSONObject对象<br/>
	 * <p>若参数content非空则调用 <b>JSONObject.fromObject(content)</b></p>
	 * <p>否则直接 new 一个 <b>JSONObject对象</b></p>
	 *
	 * @param content
	 * @return
	 */
	public static JSONObject build(String content) {
		if (StringUtils.isNotBlank(content)) {
			return JSONObject.fromObject(content);
		}
		return new JSONObject();
	}

    public JSONUtils put(Object key, Object value) {
	    this.getJson().put(key, value);
	    return this;
    }


	private static JsonConfig defaultJsonConfig;
	static{
		defaultJsonConfig = new JsonConfig();
		defaultJsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
	}
	
	public static <T> String toString(List<T> data){
		return toString(data , defaultJsonConfig);
	}
	
	public static <T> String toString(T data){
		if (data == null) {
			return "null";
		}
		return toString(data , defaultJsonConfig);
	}
	
	public static <T> String toString(List<T> data , JsonConfig jsonConfig){
		return JSONArray.fromObject(data , jsonConfig==null? defaultJsonConfig : jsonConfig).toString();
	}
	
	public static <T> String toString(T data , JsonConfig jsonConfig){
		return JSONObject.fromObject(data , jsonConfig==null? defaultJsonConfig : jsonConfig).toString();
	}
	
	public static <T> List<T> toList(String data ,  Class<T> target){
		return toList(data ,  target , defaultJsonConfig);
	}
	
	public static <T> T toBean(String data ,  Class<T> target){
		return toBean(data ,  target , defaultJsonConfig);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String data ,  Class<T> target , JsonConfig jsonConfig){
		if(StringUtils.isBlank(data)){
			return null;
		}
		return (T)JSONObject.toBean(JSONObject.fromObject(data, jsonConfig==null? defaultJsonConfig : jsonConfig), target);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String data ,  Class<T> target , JsonConfig jsonConfig){
		if(StringUtils.isBlank(data)){
			return new ArrayList<T>();
		}
		return (List<T>) JSONArray.toCollection(JSONArray.fromObject(data , jsonConfig==null? defaultJsonConfig : jsonConfig) , target);
	}
	
	
	/**
	 * 全限定key不存在时返回 null
	 * @author hurrican
	 * @param json JSONObject
	 * @param key JSONObject中定位key的全限定key
	 * @return Object,全限定key不存在时返回 null
     * <p> 获取JSONObject中key对应的值,若不存在返回 null </p>
     * <b>使用Demo：</b>
     * <p> JSONObject对象内容如下： </p>
     * <pre>{security": {"dateRecycleCoinTotal": 10,"dateDropCoinTotal": 56},ruleRuntimeInfo": { "rule0": { "exceptionTime": 1 } }}</pre>
     * <p>要获取 key 为 exceptionTime 的值可以这样调用本方法：</p>
     * <strong>Object obj = <br/>getValueByQualifiedKey(jsonObject, "ruleRuntimeInfo.rule0.exceptionTime")</strong><br/>
	 * 返回结果为： 1
	 */
	 public static Object getValueByQualifiedKey(JSONObject json,String key){
	        if (key == null || json == null) {
	            return null;
	        }
	        String[] keys = key.split("\\.");
	        Object o = null;
	        for (int i = 0; i < keys.length; i++) {
	            String key1 = keys[i];
	            if (json.containsKey(key1)) {
	                o = json.get(key1);
	                if(o instanceof JSONObject){
	                    json = (JSONObject) o;
	                }
	            }else{
	                return null;
	            }
	        }
	        return o;
	    }

    /**
     * <b>全限定key</b>的定义如下：<br/>
     * <p>设 json 字符串为：<strong>{"a":1, "b":{"k1": 1,"k2": "demo"}}</strong> <br/>
     * 字符串 <b>"demo" <b/>的全限定key为："b.k2"
     * </p>
     *
     * @param jsonString Json格式字符串
     * @param key        JSONObject中定位key的全限定key
     * @return Object 或 null
     */
    public static Object getValueByQualifiedKey(String jsonString, String key) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return getValueByQualifiedKey(jsonObject, key);
    }
	 /**
	  * 使用全限定键名设置JSONObject对应的key
	  * @param json JSONObject对象
	  * @param qualifiedKey 全限定键名，格式为"xxx.y.zz"
	  * @param newValue 全限定键对应的值
	  * @return Boolean 如果全限定键存在并设置成功则返回true,全限定键不存在或参数json为null或参数qualifiedKey为null返回false
	  */
	 public static Boolean setJsonObjectValueByQualifiedKey(JSONObject json,String qualifiedKey,Object newValue){
	        if (qualifiedKey == null || json == null) {
	            return false;
	        }
	        String[] keys = qualifiedKey.split("\\.");

	        int index = keys.length - 1;
	        for (int i = 0; i < index; i++) {
	            String key = keys[i];
	            if (json.containsKey(key)){
	                Object obj = json.get(key);
	                if (obj instanceof JSONObject){
	                    json = (JSONObject)obj;
	                }
	            }else{
	                return false;
	            }
	        }
	        if(json.containsKey(keys[index])){
	            json.put(keys[index], newValue);
	            return true;
	        }
	        return false;
	    }
	
	
	
	/******************测试*********************/
	public static void main(String[] args) {
		User user1 = new User();
//		user1.setCreateTime(new Date());
		user1.setName("user1");
		
		User user2 = new User();
		user2.setCreateTime(new Date());
		user2.setName("user2");
		
		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		
		
		String userData1 = JSONUtils.toString(user1);
		System.out.println(userData1);
		User user1FromString = JSONUtils.toBean(userData1, User.class);
		System.out.println(user1FromString);
		
		System.out.println(JSONUtils.toString(userList));
		
		String userStr = JSONUtils.toString(userList);
		List<User> parseUserList = JSONUtils.toList(userStr, User.class);
		System.out.println(parseUserList);
		for(User user : parseUserList){
			System.out.println(user.getName());
			System.out.println(getSimpleDate(user.getCreateTime()));
		}
	}

	/**
	 * 转换Date类型为字符串类型
	 *
	 * @param value
	 * @return
	 */
	public static String getSimpleDate(Date value) {
		return getSimpleDate(value, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换Date类型为字符串类型
	 *
	 * @param value
	 * @return
	 */
	public static String getSimpleDate(Date value, String pattern) {
		if(null == value){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(value);
	}


	public static class User{
		private String name;
		private Date createTime;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
	}
}
