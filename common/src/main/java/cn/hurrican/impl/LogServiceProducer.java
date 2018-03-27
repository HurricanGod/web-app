package cn.hurrican.impl;

import cn.hurrican.model.ResMessage;
import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.service.LogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogServiceProducer {

    private static Logger logger = LogManager.getLogger(LogServiceProducer.class);

    @Autowired
    private RedisExecutor executor;

    @Pointcut("@annotation(cn.hurrican.anotations.SystemLog)")
    public void controllerAspect() {
    }

    @Pointcut("@annotation(cn.hurrican.anotations.SystemLog)")
    public void doBefore() {
    }

    @Pointcut("@annotation(cn.hurrican.anotations.SystemLog)")
    public void doAround(){}

    @Around(value = "doAround()")
    public ResMessage enhanceTarget(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("args = " + args[i]);
        }
//        if(args.length == 2){
//            return ResMessage.creator().logIs("校验失败！");
//        }
        Object res = joinPoint.proceed();
        return (ResMessage) res;
    }


    @Before(value = "doBefore()")
    public void checkValidate(JoinPoint joinPoint) {
        System.out.println("execute do before!");
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnVal")
    public ResMessage produceLog(JoinPoint joinPoint, Object returnVal) {
//        executor.doInRedis((instance -> {
//            if (returnVal instanceof ResMessage) {
//                String log = ((ResMessage) returnVal).getLog();
//                Long count = instance.lpush("log", log);
//                System.out.println("count = " + count);
//
//            }
//        }));
        logger.info("exec produceLog");
        System.out.println("exec produceLog");
        return (ResMessage) returnVal;
    }
}
