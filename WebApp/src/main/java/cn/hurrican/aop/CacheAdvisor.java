/**
 * Created on  13-09-19 20:40
 */
package cn.hurrican.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author Hurrican
 */
public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    public static final String CACHE_ADVISOR_BEAN_NAME = "myCache.internalCacheAdvisor";

    private String[] basePackages;

    public Pointcut getPointcut() {
        CachePointcut pointcut = new CachePointcut(basePackages);
        return pointcut;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
