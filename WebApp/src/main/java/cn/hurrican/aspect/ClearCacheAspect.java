package cn.hurrican.aspect;

import cn.hurrican.annotation.DelCache;
import cn.hurrican.annotation.KeyParam;
import cn.hurrican.redis.RedisExecutor;
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
 * @Date 2018/7/30
 * @Modified 18:52
 */
@Aspect
@Component
public class ClearCacheAspect {

    @Autowired
    private RedisExecutor executor;

    @Pointcut("@annotation(cn.hurrican.annotation.DelCache)")
    public void clearCachePointcut(){

    }


    @Around("clearCachePointcut() && @annotation(args) ")
    public Object doClear(ProceedingJoinPoint joinPoint, DelCache args){
        Object execResult = null;

        String methodInvokeName = joinPoint.getSignature().toLongString();
        List<Method> itemMethod = Arrays.stream(joinPoint.getTarget().getClass().getDeclaredMethods())
                .filter(e -> e.toString().equals(methodInvokeName)).collect(Collectors.toList());
        Object[] params = joinPoint.getArgs();
        if (itemMethod.size() > 0) {
            Method method = itemMethod.get(0);
            String key = args.prefixKey() + args.postfixKey();
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < methodParameterAnnotations.length; i++) {
                for (int j = 0; j < methodParameterAnnotations[i].length; j++) {
                    Annotation annotation = methodParameterAnnotations[i][j];
                    if (annotation.annotationType().equals(KeyParam.class)) {
                        key = key.replace(((KeyParam) annotation).value(), params[i].toString());
                    }
                }
            }
            delKey(key);
        }
        try {
            execResult = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return execResult;
    }

    private void delKey(String key) {
        executor.doInRedis(instance -> {
            instance.del(key);
        });
    }
}
