package cn.hurrican.test;

import cn.hurrican.impl.UserServiceImpl;
import cn.hurrican.service.EmailService;
import cn.hurrican.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

// MockitoTestExecutionListener  DependencyInjectionTestExecutionListener
@ContextConfiguration(classes = UserServiceImpl.class)
//@ContextConfiguration(locations = "classpath:spring-*.xml")
@TestExecutionListeners(listeners = MockitoTestExecutionListener.class)
public class SpringTestNGManyDependency extends AbstractTestNGSpringContextTests {

    @MockBean
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Test
    public void testSaveAndCacheMethod() {
//       when(emailService.sendEmail()).thenReturn("success");

    }
}
