package cn.hurrican.impl;

import cn.hurrican.model.ResMessage;
import cn.hurrican.redis.RedisExecutor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogServiceProducer {

    @Autowired
    @Qualifier(value = "redisExecutor")
    private RedisExecutor executor;

    @Pointcut("@annotation(cn.hurrican.anotations.SystemLog)")
    public void controllerAspect() {
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnVal")
    public void produceLog(JoinPoint joinPoint, Object returnVal) {
        executor.doInRedis((instance -> {
            if (returnVal instanceof ResMessage) {
                String log = ((ResMessage) returnVal).getLog();
                Long count = instance.lpush("log", log);
                System.out.println("count = " + count);
            }
        }));
    }
}
