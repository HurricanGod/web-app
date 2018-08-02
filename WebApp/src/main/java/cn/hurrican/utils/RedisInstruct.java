package cn.hurrican.utils;

import cn.hurrican.config.KeyType;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description: Integer、Double、String 类型之间转换器
 * @Date 2018/8/2
 * @Modified 12:23
 */
public class RedisInstruct {

    public static Map<Integer, Map<Integer, Method>> INSTRUCT_SET = new HashMap<>(16);

    static {
        HashMap<String, Integer> map = new HashMap<>(32);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrange(java.lang.String,long,long)", 1);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrevrange(java.lang.String,long,long)", -1);

        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeByScore(java.lang.String,double,double)", 2);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrevrangeByScore(java.lang.String,double,double)", -2);

        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeByScore(java.lang.String,double,double,int,int)", 3);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrevrangeByScore(java.lang.String,double,double,int,int)", -3);

        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeWithScores(java.lang.String,long,long)", 4);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrevrangeWithScores(java.lang.String,long,long)", -4);

        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeByScoreWithScores(java.lang.String,double,double)", 5);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrevrangeByScoreWithScores(java.lang.String,double,double)", -5);

        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeByScoreWithScores(java.lang.String,double,double,int,int)", 6);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrevrangeByScoreWithScores(java.lang.String,double,double,int,int)", -6);

        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeByLex(java.lang.String,java.lang.String,java.lang.String)", 7);
        map.put("public java.util.Set redis.clients.jedis.Jedis.zrangeByLex(java.lang.String,java.lang.String,java.lang.String,int,int)", 8);

        map.put("public java.lang.Long redis.clients.jedis.Jedis.zrank(java.lang.String,java.lang.String)", 9);
        map.put("public java.lang.Long redis.clients.jedis.Jedis.zrevrank(java.lang.String,java.lang.String)", -9);

        map.put("public java.lang.Long redis.clients.jedis.Jedis.zcount(java.lang.String,double,double)", 10);
        map.put("public java.lang.Double redis.clients.jedis.Jedis.zscore(java.lang.String,java.lang.String)", 11);
        map.put("public java.lang.Long redis.clients.jedis.Jedis.zcard(java.lang.String)", 12);

        Class<Jedis> jedisClass = Jedis.class;
        Map<Integer, Method> sortedSetMethods = new HashMap<>(32);
        Arrays.stream(jedisClass.getDeclaredMethods()).filter(m -> map.keySet().contains(m.toString()))
                .forEach(method -> {
                    Integer index = map.get(method.toString());
                    if (index != null) {
                        sortedSetMethods.put(index, method);
                    }
                });

        INSTRUCT_SET.put(KeyType.SORTED_SET, sortedSetMethods);
    }

}
