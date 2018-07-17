package cn.hurrican.aspect;

import cn.hurrican.annotation.CacheValue;
import cn.hurrican.annotation.HashField;
import cn.hurrican.annotation.KeyParam;
import cn.hurrican.annotation.WriteCache;
import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;
import cn.hurrican.redis.RedisExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private RedisExecutor executor;


    @Pointcut("@annotation(cn.hurrican.annotation.WriteCache)")
    public boolean cacheValue() {
        return true;
    }

    @Around("cacheValue() && @annotation(args)")
    public Object doCache(ProceedingJoinPoint joinPoint, WriteCache args) {


        String methodInvokeName = joinPoint.getSignature().toLongString();
        List<Method> itemMethod = Arrays.stream(joinPoint.getTarget().getClass().getDeclaredMethods())
                .filter(e -> e.toString().equals(methodInvokeName))
                .collect(Collectors.toList());

        System.out.println("itemMethod.size() = " + itemMethod.size());
        Object[] params = joinPoint.getArgs();
        if (itemMethod.size() > 0) {
            Method method = itemMethod.get(0);
            String key = args.prefixKey() + args.postfixKey();
            Object cacheValue = null;
            String field = null;
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < methodParameterAnnotations.length; i++) {
                for (int j = 0; j < methodParameterAnnotations[i].length; j++) {
                    Annotation annotation = methodParameterAnnotations[i][j];
                    System.out.println(annotation.annotationType().equals(KeyParam.class));
                    if (annotation.annotationType().equals(KeyParam.class)) {
                        key = key.replace(((KeyParam) annotation).value(), params[i].toString());
                    } else if (annotation.annotationType().equals(CacheValue.class)) {
                        cacheValue = params[i];
                    } else if (annotation.annotationType().equals(HashField.class)) {
                        field = params[i].toString();
                    }
                }
            }

            System.out.println("key.toString() = " + key);
            switch (args.type()) {
                case KeyType.INT_STRING:
                case KeyType.DOUBLE_STRING:
                case KeyType.CHAR_STRING:
                    cacheString(args, key, cacheValue);
                    break;
                case KeyType.HASH:
                    cacheObjectToHash(args, key, cacheValue, field);
                    break;
                case KeyType.LIST:
                    cacheObjectToList(args, key, cacheValue);
                    break;
                default:
                    break;
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

    private void cacheObjectToSet(WriteCache args, String key, Object cacheValue){
        executor.doInRedis(instance -> {

        });
    }

    private void cacheObjectToList(WriteCache args, String key, Object cacheValue) {
        executor.doInRedis(instance -> {
            if (cacheValue == null) {
                return;
            }
            Boolean existHashKey = instance.exists(key);
            if (isJavaBasicType(cacheValue)) {
                if (args.enterQueueWay() == CacheConstant.RPUSH) {
                    instance.rpush(key, cacheValue.toString());
                } else {
                    instance.lpush(key, cacheValue.toString());
                }
            } else {
                ObjectMapper mapper = new ObjectMapper();
                String valueString = mapper.writeValueAsString(cacheValue);
                if (args.enterQueueWay() == CacheConstant.RPUSH) {
                    instance.rpush(key, valueString);
                } else {
                    instance.lpush(key, valueString);
                }
            }
            if (!existHashKey && args.expire() != CacheConstant.NEVER_EXPIRE) {
                instance.expire(key, args.expire());
            }
        });
    }

    private void cacheObjectToHash(WriteCache args, String key, Object cacheValue, String field) {
        executor.doInRedis(instance -> {
            Boolean existHashKey = instance.exists(key);
            if (field != null && cacheValue != null) {
                if (isJavaBasicType(cacheValue)) {
                    instance.hset(key, field, cacheValue.toString());

                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    String valueString = mapper.writeValueAsString(cacheValue);
                    instance.hset(key, field, valueString);
                }
                if (!existHashKey && args.expire() != CacheConstant.NEVER_EXPIRE) {
                    instance.expire(key, args.expire());
                }
            }
        });
    }

    private void cacheString(WriteCache args, String key, Object cacheValue) {
        String value = cacheValue == null ?
                args.type() == KeyType.INT_STRING || args.type() == KeyType.DOUBLE_STRING ? "0" : "null"
                : cacheValue.toString();
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
}
