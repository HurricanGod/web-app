package cn.hurrican.impl;

import cn.hurrican.model.ResMessage;
import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogServiceProducer {

    @Autowired
    private RedisExecutor executor;

    @Pointcut("@annotation(cn.hurrican.anotations.SystemLog)")
    public void controllerAspect() {
    }

    @Pointcut("@annotation(cn.hurrican.anotations.SystemLog)")
    public void doBefore() {
    }

    @Before(value = "doBefore()")
    public void checkValidate(JoinPoint joinPoint) {
        System.out.println("execute do before!");
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        throw new RuntimeException("参数非法！");
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnVal")
    public ResMessage produceLog(JoinPoint joinPoint, Object returnVal) {
        executor.doInRedis((instance -> {
            if (returnVal instanceof ResMessage) {
                String log = ((ResMessage) returnVal).getLog();
                Long count = instance.lpush("log", log);
                System.out.println("count = " + count);

            }
        }));
        return (ResMessage) returnVal;
    }
}
