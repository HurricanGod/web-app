package cn.hurrican.aspect;

import cn.hurrican.anotations.Verify;
import cn.hurrican.model.ResMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class VerifyAspect {

    @Pointcut("@annotation(cn.hurrican.anotations.Verify)")
    public void doVerify() {
    }


    @Around("doVerify() && @annotation(args)")
    public ResMessage verifyPlay(ProceedingJoinPoint joinPoint, Verify args) {

        try {
            int i = 0;
            System.out.println("10/i = " + 10 / i);
        } catch (Exception e) {
//            return ResMessage.creator().logIs(e.getMessage());
        }
        ResMessage resMessage = ResMessage.creator().retCodeEqual(0).logIs("ok");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return ResMessage.creator().logIs(throwable.getMessage());
        }
        return resMessage;
    }
}
