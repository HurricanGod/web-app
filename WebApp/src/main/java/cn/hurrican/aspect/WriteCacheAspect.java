package cn.hurrican.aspect;

import cn.hurrican.annotation.*;
import cn.hurrican.config.AbstractHostingCacheHandler;
import cn.hurrican.config.CacheBean;
import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;
import cn.hurrican.exception.RedisKeyMismatchRuntimeException;
import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.utils.ClassUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/16
 * @Modified 9:26
 */
@Aspect
@Component
public class WriteCacheAspect {

    private static Logger logger = LogManager.getLogger(WriteCacheAspect.class);

    @Autowired
    private RedisExecutor executor;


    @Pointcut("@annotation(cn.hurrican.annotation.WriteCache)")
    public boolean cacheValue() {
        return true;
    }

    @Around("cacheValue() && @annotation(args)")
    public Object doCache(ProceedingJoinPoint joinPoint, WriteCache args) {

        boolean hadWritten = false;
        String methodInvokeName = joinPoint.getSignature().toLongString();
        List<Method> itemMethod = Arrays.stream(joinPoint.getTarget().getClass().getDeclaredMethods())
                .filter(e -> e.toString().equals(methodInvokeName))
                .collect(Collectors.toList());

        System.out.println("itemMethod.size() = " + itemMethod.size());
        Object[] params = joinPoint.getArgs();
        if (itemMethod.size() > 0) {
            Method method = itemMethod.get(0);
            String key = args.prefixKey() + args.postfixKey();
            CacheBean cacheBean = CacheBean.build();
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < methodParameterAnnotations.length; i++) {
                for (int j = 0; j < methodParameterAnnotations[i].length; j++) {
                    Annotation annotation = methodParameterAnnotations[i][j];
                    System.out.println(annotation.annotationType().equals(KeyParam.class));
                    if (annotation.annotationType().equals(KeyParam.class)) {
                        key = key.replace(((KeyParam) annotation).value(), params[i].toString());
                    } else if (annotation.annotationType().equals(CacheValue.class)) {
                        cacheBean.setValue(params[i]);
                        cacheBean.setType(((CacheValue)annotation).type());
                    } else if (annotation.annotationType().equals(HashField.class)) {
                        cacheBean.setField(new String[]{params[i].toString()});
                    } else if (annotation.annotationType().equals(ZSetScore.class)) {

                    }
                }
            }

            System.out.println("key.toString() = " + key);
            if(args.writeOccasion() == CacheConstant.BEFORE){
                switch (args.type()) {
                    case KeyType.INT_STRING:
                    case KeyType.DOUBLE_STRING:
                    case KeyType.CHAR_STRING:
                        cacheString(args, key, cacheBean);
                        break;
                    case KeyType.HASH:
                        cacheObjectToHash(args, key, cacheBean);
                        break;
                    case KeyType.LIST:
                        cacheObjectToList(args, key, cacheBean);
                        break;
                    case KeyType.SORTED_SET:
                        cacheObjectToSortedSet(args, key, cacheBean);
                        break;
                    default:
                        break;
                }
                hadWritten = true;
            }
        }

        Object execResult = null;
        try {
            execResult = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return execResult;
    }

    private void cacheObjectToSet(WriteCache args, String key, CacheBean cacheBean, AbstractHostingCacheHandler handler){
        executor.doInRedis(instance -> {
            if (cacheBean.getValue() == null) {
                return;
            }
            // 判断 set key 是否存在，方便后面设置过期时间
            Boolean existSetKey = instance.exists(key);
            // 删除写模式下先删除 key 在保存
            if(args.pattern() == CacheConstant.DEL_WRITE){
                instance.del(key);
            }
            ObjectMapper mapper = new ObjectMapper();
            // 判断 cacheBean 中的 value 是否为集合类型
            if(isCollectionType(cacheBean)){
                Collection collection = (Collection) cacheBean.getValue();
                String[] valueArray = new String[collection.size()];
                int index = 0;
                for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
                    Object object = iterator.next();
                    valueArray[index++] = mapper.writeValueAsString(object);
                }
                instance.sadd(key, valueArray);
            }else{
                instance.sadd(key, mapper.writeValueAsString(cacheBean.getValue()));
            }
            if (!existSetKey && args.expire() != CacheConstant.NEVER_EXPIRE) {
                instance.expire(key, args.expire());
            }else{
                handler.handleNeverExpireKey(key);
            }
        });
    }

    /**
     * 判断要缓存的对象是否是集合类型
     * @param cacheBean
     * @return
     */
    private Boolean isCollectionType(CacheBean cacheBean) {
        return Optional.ofNullable(cacheBean.getType())
                        .map(e -> Arrays.stream(e.getInterfaces()).collect(Collectors.toSet()).contains(Collection.class))
                        .orElse(false);
    }

    private void cacheObjectToList(WriteCache args, String key, CacheBean cacheBean) {
        executor.doInRedis(instance -> {
            if (cacheBean.getValue() == null) {
                return;
            }
            Boolean existHashKey = instance.exists(key);
            if(args.pattern() == CacheConstant.DEL_WRITE){
                instance.del(key);
            }
            ObjectMapper mapper = new ObjectMapper();
            // 要缓存的值为集合类型
            if(isCollectionType(cacheBean)){
                Collection collection = (Collection) cacheBean.getValue();
                int index = 0;
                String[] valueArray = new String[collection.size()];
                // 将集合对象转化为 String 数组
                for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
                   try{
                       Object object = iterator.next();
                       valueArray[index++] = mapper.writeValueAsString(object);
                   }catch( Exception e){
                       logger.error(e);
                   }
                }
                if (args.enterQueueWay() == CacheConstant.RPUSH) {
                    instance.rpush(key, valueArray);
                } else {
                    instance.lpush(key, valueArray);
                }
            }else{
                if (args.enterQueueWay() == CacheConstant.RPUSH) {
                    instance.rpush(key, mapper.writeValueAsString(cacheBean.getValue()));
                } else {
                    instance.lpush(key, mapper.writeValueAsString(cacheBean.getValue()));
                }
            }
            if (!existHashKey && args.expire() != CacheConstant.NEVER_EXPIRE) {
                instance.expire(key, args.expire());
            }
        });
    }

    private void cacheObjectToHash(WriteCache args, String key, CacheBean cacheBean) {
        executor.doInRedis(instance -> {
            Boolean existHashKey = instance.exists(key);
            if (ClassUtil.superTypeIsMap(cacheBean.getType())) {
                instance.hmset(key, convertToMap(cacheBean));
            }else{
                String[] fieldArray = cacheBean.getField();
                if (fieldArray != null && fieldArray.length > 0 && cacheBean.getValue() != null) {
                    if (isJavaBasicType(cacheBean.getValue())) {
                        instance.hset(key, fieldArray[0], cacheBean.getValue().toString());
                    } else {
                        ObjectMapper mapper = new ObjectMapper();
                        String valueString = mapper.writeValueAsString(cacheBean.getValue());
                        instance.hset(key, fieldArray[0], valueString);
                    }
                }else{
                    throw new RedisKeyMismatchRuntimeException("往 hash 存储结构里缓存1个字段时，field 和 value都不允许为 null");
                }
            }
            if (!existHashKey && args.expire() != CacheConstant.NEVER_EXPIRE) {
                instance.expire(key, args.expire());
            }
        });
    }


    private void cacheObjectToSortedSet(WriteCache args, String key, CacheBean cacheBean) {
        executor.doInRedis(instance -> {
            Boolean existSortedSetKey = instance.exists(key);
            if (ClassUtil.superTypeIsMap(cacheBean.getType())) {
                instance.zadd(key, (Map<String, Double>) cacheBean.getValue());
            } else {
                if (cacheBean.getScores() == null || cacheBean.getValue() == null) {
                    throw new RedisKeyMismatchRuntimeException("store value to sorted set expect score and value must not null,but there has one more values are null!");
                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    instance.zadd(key, cacheBean.getScores(), mapper.writeValueAsString(cacheBean.getValue()));
                }
            }

            if (!existSortedSetKey && args.expire() != CacheConstant.NEVER_EXPIRE) {
                instance.expire(key, args.expire());
            }
        });
    }

    private Map<String, String> convertToMap(CacheBean cacheBean){
        HashMap<String, String> map = new HashMap<>(16);
        Object value = cacheBean.getValue();
        ObjectMapper mapper = new ObjectMapper();
        if(value instanceof Map){
            ((Map) value).forEach((k,v) ->{
                try {
                    map.put(mapper.writeValueAsString(k), mapper.writeValueAsString(v));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    logger.error("convertToMap()方法发生异常：" + e);
                }
            });
        }
        return map;
    }



    private void cacheString(WriteCache args, String key, CacheBean cacheBean) {
        String value = cacheBean.getValue() == null ?
                args.type() == KeyType.INT_STRING || args.type() == KeyType.DOUBLE_STRING ? "0" : "null"
                : cacheBean.getValue().toString();
        executor.doInRedis(instance -> {
            if (args.expire() != CacheConstant.NEVER_EXPIRE) {
                instance.setex(key, args.expire(), value);
            } else {
                instance.set(key, value);
            }
        });
    }

    private boolean isJavaBasicType(Object cacheValue) {
        if (cacheValue instanceof Number) {
            return true;
        } else if (cacheValue instanceof String) {
            return true;
        } else if (cacheValue instanceof Character) {
            return true;
        }
        return false;
    }

    private boolean isMapType(Class clazz){
        if(clazz == null){
            return false;
        }
        if(clazz.equals(Map.class)){
            return true;
        }
        return Arrays.stream(clazz.getInterfaces()).collect(Collectors.toSet()).contains(Map.class);
    }
}
