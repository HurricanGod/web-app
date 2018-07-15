package cn.hurrican.config;

import cn.hurrican.aop.CacheAdvisor;
import cn.hurrican.aop.EnableCache;
import cn.hurrican.aop.EnableCacheMethodInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;


@Configuration
public class ProxyConfiguration implements ApplicationContextAware, ImportAware {

    private AnnotationAttributes enableCacheAttributes;

    private ApplicationContext context;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableCacheAttributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableCache.class.getName(), false));
        if (this.enableCacheAttributes == null) {
            throw new IllegalArgumentException(
                    "@EnableAsync is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Bean(name = CacheAdvisor.CACHE_ADVISOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAdvisor cacheAdvisor() {
        System.out.println("start exec cacheAdvisor() ...");
        EnableCacheMethodInterceptor cacheInterceptor = new EnableCacheMethodInterceptor();
        cacheInterceptor.setApplicationContext(context);

        CacheAdvisor advisor = new CacheAdvisor();
        advisor.setAdviceBeanName(CacheAdvisor.CACHE_ADVISOR_BEAN_NAME);
        advisor.setAdvice(cacheInterceptor);
        advisor.setBasePackages(this.enableCacheAttributes.getStringArray("basePackages"));
        advisor.setOrder(this.enableCacheAttributes.<Integer>getNumber("order"));
        return advisor;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public AnnotationAttributes getEnableCacheAttributes() {
        return enableCacheAttributes;
    }

    public void setEnableCacheAttributes(AnnotationAttributes enableCacheAttributes) {
        this.enableCacheAttributes = enableCacheAttributes;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
