package cn.hurrican.test;

import cn.hurrican.service.EmailService;
import cn.hurrican.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

// MockitoTestExecutionListener  DependencyInjectionTestExecutionListener
//@ContextConfiguration(classes = UserServiceImpl.class)
//@ContextConfiguration(locations = "classpath:spring-*.xml")
//@TestExecutionListeners(listeners = MockitoTestExecutionListener.class)
public class SpringTestNGManyDependency /**extends AbstractTestNGSpringContextTests*/
{

    //    @MockBean
    private EmailService emailService;

    @Autowired
    private UserService userService;

    //    @Test
    public void testSaveAndCacheMethod() {
//       when(emailService.sendEmail()).thenReturn("success");

    }
}
