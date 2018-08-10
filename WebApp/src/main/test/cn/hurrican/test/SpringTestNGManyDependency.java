package cn.hurrican.test;

import cn.hurrican.service.EmailService;
import cn.hurrican.service.UserService;


// MockitoTestExecutionListener  DependencyInjectionTestExecutionListener
//@ContextConfiguration(locations = "classpath:spring-*.xml")
public class SpringTestNGManyDependency  {

    private EmailService emailService;

    private UserService userService;

    public void testSaveAndCacheMethod() {
    }
}
