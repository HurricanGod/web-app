package cn.hurrican.aop;

import cn.hurrican.annotation.EnableCache;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 11:28
 */
@Component
public class EnableCacheMethodInterceptor implements ApplicationContextAware, MethodInterceptor {

    private static Logger logger = LogManager.getLogger(EnableCacheMethodInterceptor.class);

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * Implement this method to perform extra treatments before and
     * after the invocation. Polite implementations would certainly
     * like to invoke {@link Joinpoint#proceed()}.
     *
     * @param invocation the method invocation joinpoint
     * @return the result of the call to {@link
     * Joinpoint#proceed()}, might be intercepted by the
     * interceptor.
     * @throws Throwable if the interceptors or the
     *                   target-object throws an exception.
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        System.out.println("exec invoke()");
        logger.info("exec invoke()");
        Map<String, Object> enableCacheServiceMap = context.getBeansWithAnnotation(EnableCache.class);
        Object result = null;
        try{
            result = invocation.proceed();
        }catch( Exception e){
            e.printStackTrace();
            logger.error("发生异常：\t{}", e);
        }

        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
