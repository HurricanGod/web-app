package cn.hurrican.service;

import cn.hurrican.impl.UserServiceImpl;
import cn.hurrican.redis.RedisExecutor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

public class MockitoTest {

    /**
     * 定义redisExecutor假的实现
     */
    @Mock
    private RedisExecutor redisExecutor;

    /**
     * 利用Mockito把RedisExecutor注入到UserServiceImpl
     */
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeMethod(alwaysRun = true)
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveAndCacheMethod() {
        when(redisExecutor.doInRedis((instance -> {
            return instance.get("123");
        }))).thenReturn("success");

    }
}
