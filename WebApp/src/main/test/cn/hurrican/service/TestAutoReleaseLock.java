package cn.hurrican.service;

import cn.hurrican.redis.AutoReleaseLock;
import cn.hurrican.redis.RedisExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestAutoReleaseLock {

    @Autowired
    private RedisExecutor executor;

    @Test
    public void testMethod1() {
        try (AutoReleaseLock lock = new AutoReleaseLock(executor, "type:str_lock", 5, "Thread-1")) {
            boolean successLock = lock.tryGetDistributedLock();
            System.out.println("successLock = " + successLock);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("executor == null = " + (executor == null));
        System.out.println("done...");

        executor.doInRedis(instance -> {

        });
    }
}
