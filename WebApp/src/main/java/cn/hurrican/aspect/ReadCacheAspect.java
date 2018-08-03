package cn.hurrican.aspect;

import cn.hurrican.annotation.HashField;
import cn.hurrican.annotation.KeyParam;
import cn.hurrican.annotation.ListIndex;
import cn.hurrican.annotation.ReadCache;
import cn.hurrican.annotation.SortedSetInstruct;
import cn.hurrican.annotation.ZSetScore;
import cn.hurrican.config.CacheBean;
import cn.hurrican.config.CacheConstant;
import cn.hurrican.config.KeyType;
import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.service.Try;
import cn.hurrican.utils.ClassUtil;
import cn.hurrican.utils.RedisInstruct;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/27
 * @Modified 9:27
 */
@Aspect
@Component
public class ReadCacheAspect {

    private static Logger logger = LogManager.getLogger(ReadCacheAspect.class);

    @Autowired
    private RedisExecutor executor;

    @Pointcut("@annotation(cn.hurrican.annotation.ReadCache)")
    public void readValueFromCache() {

    }

    @Around(value = "readValueFromCache() && @annotation(args)")
    public Object readCache(ProceedingJoinPoint joinPoint, ReadCache args) {
        Object execResult = null;
        String methodInvokeName = joinPoint.getSignature().toLongString();
        List<Method> itemMethod = Arrays.stream(joinPoint.getTarget().getClass().getDeclaredMethods())
                .filter(e -> e.toString().equals(methodInvokeName)).collect(Collectors.toList());
        if (itemMethod.size() > 0) {
            Method method = itemMethod.get(0);
            Class<?> returnType = method.getReturnType();
            CacheBean cacheBean = prepareReadCache(joinPoint, args, method);

            if (ClassUtil.superTypeIsCollection(returnType) || ClassUtil.superTypeIsMap(returnType)) {
                switch (args.type()) {
                    case KeyType.LIST:
                        execResult = readListFromCache(cacheBean);
                        break;
                    case KeyType.HASH:
                        execResult = readMapFromCache(cacheBean, false);
                        break;
                    case KeyType.SET:
                        execResult = readSetFromCache(cacheBean);
                        break;
                    case KeyType.SORTED_SET:
                        if (!SortedSetInstruct.LEGAL_SET.contains(args.instruct())) {
                            throw new RuntimeException(String.format("注解 @ReadCache 中当 type = KeyType.SORTED_SET 时 instruct = %s 非法", args.instruct()));
                        }

                        break;
                    default:
                        break;
                }
            } else {
                switch (args.type()) {
                    case KeyType.LIST:
                        execResult = readOneOfListByIndexFromCache(cacheBean);
                        break;
                    case KeyType.HASH:
                        execResult = readMapFromCache(cacheBean, true);
                        break;
                    default:
                        execResult = readGeneralStringValueFromCache(cacheBean, args);
                        break;
                }
            }
        }
        if (execResult != null) {
            return execResult;
        }
        try {
            execResult = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return execResult;
    }

    private CacheBean prepareReadCache(ProceedingJoinPoint joinPoint, ReadCache args, Method method) {
        CacheBean cacheBean = CacheBean.build();
        cacheBean.setType(args.clazz());
        String key = args.prefixKey() + args.postfixKey();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] params = joinPoint.getArgs();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation.annotationType().equals(KeyParam.class)) {
                    key = key.replace(((KeyParam) annotation).value(), params[i].toString());
                } else if (annotation.annotationType().equals(ListIndex.class)) {
                    int indexType = ((ListIndex) annotation).indexType();
                    if (indexType == CacheConstant.LEFT_INDEX) {
                        cacheBean.setLindex((Integer) params[i]);
                    } else {
                        cacheBean.setRindex((Integer) params[i]);
                    }
                } else if (annotation.annotationType().equals(HashField.class)) {
                    if (ClassUtil.superTypeIsCollection(((HashField) annotation).clazz())) {
                        String[] fieldArray = new String[((Collection<String>) params[i]).size()];
                        cacheBean.setField(((Collection<String>) params[i]).toArray(fieldArray));
                    } else {
                        cacheBean.setField(new String[]{params[i].toString()});
                    }
                } else if (annotation.annotationType().equals(ZSetScore.class)) {
                    if (((ZSetScore) annotation).param() == CacheConstant.LEFT_INDEX) {
                        cacheBean.setLindex((Integer) params[i]);
                    } else if (((ZSetScore) annotation).param() == CacheConstant.RIGHT_INDEX) {
                        cacheBean.setRindex((Integer) params[i]);
                    } else if (((ZSetScore) annotation).param() == CacheConstant.MIN_SCORE) {
                        cacheBean.setMinScore((Double) params[i]);
                    } else if (((ZSetScore) annotation).param() == CacheConstant.MAX_SCORE) {
                        cacheBean.setMaxScore((Double) params[i]);
                    }
                }
            }
        }
        cacheBean.setKey(key);
        return cacheBean;
    }

    private Object readSortedSetValueFromCache(CacheBean cacheBean, ReadCache args) {
        return executor.doInRedis(instance -> {
            Method method = RedisInstruct.INSTRUCT_SET.get(KeyType.SORTED_SET).get(args.instruct());
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] methodParams = new Object[parameterTypes.length];
            methodParams[0] = cacheBean.getKey();
            for (int i = 1; i < parameterTypes.length; i++) {
                if(i == 1 && parameterTypes[i].equals(double.class)){
                    methodParams[i] = cacheBean.getMinScore();
                }else if( i == 1 && parameterTypes[i].equals(long.class) ){
                    methodParams[i] = cacheBean.getLindex();
                }else if( i == 1 && parameterTypes[i].equals(String.class) ){
                    methodParams[i] = cacheBean.getMember();
                }else if( i == 2 && parameterTypes[i].equals(double.class) ){
                    methodParams[i] = cacheBean.getMaxScore();
                }else if( i == 2 && parameterTypes[i].equals(long.class) ){
                    methodParams[i] = cacheBean.getRindex();
                }else if( i == 2 && parameterTypes[i].equals(String.class) ){
                    methodParams[i] = cacheBean.getRmember();
                }else if( i == 3 && parameterTypes[i].equals(int.class) ){
                    methodParams[i] = cacheBean.getLindex();
                }else if( i == 4 && parameterTypes[i].equals(int.class) ){
                    methodParams[i] = cacheBean.getRindex();
                }
            }
            return method.invoke(instance, methodParams);
        });
    }


    private Object readGeneralStringValueFromCache(CacheBean cacheBean, ReadCache args) {
        return executor.doInRedis(instance -> {
            String key = cacheBean.getKey();
            if (!instance.exists(key)) {
                return null;
            }
            String cacheString = instance.get(cacheBean.getKey());
            Object returnValue;
            switch (args.type()) {
                case KeyType.INT_STRING:
                    returnValue = Integer.valueOf(cacheString);
                    break;
                case KeyType.DOUBLE_STRING:
                    returnValue = Double.valueOf(cacheString);
                    break;
                default:
                    returnValue = cacheString;
                    break;
            }
            return returnValue;
        });
    }

    private Object readOneOfListByIndexFromCache(CacheBean cacheBean) {
        return executor.doInRedis(instance -> {
            String key = cacheBean.getKey();
            if (!instance.exists(key)) {
                return null;
            }
            Integer index = Optional.ofNullable(cacheBean.getLindex()).orElse(cacheBean.getRindex());
            if (index == null) {
                throw new RuntimeException("查询list存储结构必须指定索引号！");
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = instance.lindex(key, index);
            return Optional.ofNullable(json).map(Try.of((ele -> mapper.readValue(ele, cacheBean.getType())), null)).orElse(null);
        });
    }

    private Object readListFromCache(CacheBean cacheBean) {
        return executor.doInRedis(instance -> {
            String key = cacheBean.getKey();
            if (!instance.exists(key)) {
                return null;
            }
            ArrayList<Object> objects = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            if (cacheBean.getLindex() != null && cacheBean.getRindex() != null) {
                List<String> jsonList = instance.lrange(key, cacheBean.getLindex(), cacheBean.getRindex());
                jsonList.forEach(ele -> {
                    try {
                        objects.add(mapper.readValue(ele, cacheBean.getType()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                });
            } else {
                Integer index = Optional.ofNullable(cacheBean.getLindex()).orElse(cacheBean.getRindex());
                if (index == null) {
                    throw new RuntimeException("查询list存储结构必须指定索引号！");
                }
                String json = instance.lindex(key, index);
                Object value = mapper.readValue(json, cacheBean.getType());

                objects.add(value);
            }
            return objects;
        });
    }

    private Object readMapFromCache(CacheBean cacheBean, boolean isOnlyField) {
        return executor.doInRedis(instance -> {
            String key = cacheBean.getKey();
            if (!instance.exists(key)) {
                return null;
            }
            HashMap<String, Object> resultMap = new HashMap<>(8);
            ObjectMapper mapper = new ObjectMapper();
            List<String> resultList = instance.hmget(key, cacheBean.getField());
            if (resultList != null && resultList.size() > 0) {
                for (int i = 0; i < cacheBean.getField().length; i++) {
                    try {
                        if (resultList.get(i) != null) {
                            resultMap.put(cacheBean.getField()[i], mapper.readValue(resultList.get(i), cacheBean.getType()));
                        } else {
                            resultMap.put(cacheBean.getField()[i], null);
                        }
                    } catch (Exception e) {
                        logger.error(e);
                        e.printStackTrace();
                    }
                }
            }
            if (isOnlyField && cacheBean.getField() != null && cacheBean.getField().length == 1) {
                return resultMap.get(cacheBean.getField()[0]);
            }
            return resultMap;
        });
    }


    private Object readSetFromCache(CacheBean cacheBean) {
        return executor.doInRedis(instance -> {
            String key = cacheBean.getKey();
            if (!instance.exists(key)) {
                return null;
            }
            Set<String> smembers = instance.smembers(key);
            if (smembers != null && smembers.size() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                return smembers.stream().map(Try.of(m -> mapper.readValue(m, cacheBean.getType()), null))
                        .collect(Collectors.toSet());
            }
            return null;
        });
    }

}
