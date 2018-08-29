package cn.hurrican.service;

import cn.hurrican.impl.UserServiceImpl;
import cn.hurrican.redis.RedisExecutor;

public class MockitoTest {

    /**
     * 定义redisExecutor假的实现
     */
//    @Mock
    private RedisExecutor redisExecutor;

    /**
     * 利用Mockito把RedisExecutor注入到UserServiceImpl
     */
//    @InjectMocks
    private UserServiceImpl userService;

    //    @BeforeMethod(alwaysRun = true)
    public void initMock() {
//        MockitoAnnotations.initMocks(this);
    }

    public void testSaveAndCacheMethod() {
//        when(redisExecutor.doInRedis((instance -> {
//            return instance.get("123");
//        }))).thenReturn("success");

    }
}
