package cn.hurrican.aspect;

import cn.hurrican.annotation.LotteryEnhance;
import cn.hurrican.model.ResMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/9/4
 * @Modified 16:11
 */
@Aspect
@Component
public class LotteryEnhanceAspect {


    @Pointcut("@annotation(cn.hurrican.annotation.LotteryEnhance)")
    public void enhance(){

    }

    @Around("enhance() && @annotation(args)")
    public Object doEnhance(ProceedingJoinPoint joinPoint, LotteryEnhance args){
        Object result = null;
        String s = joinPoint.toLongString();
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if(result instanceof ResMessage){
            ((ResMessage) result).put(s, "success exec enhance logic");

        }
        return result;
    }
}
