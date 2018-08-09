package cn.hurrican.redis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.Jedis;

import java.util.Collections;


public class AutoReleaseRedisLockService implements AutoCloseable, ApplicationContextAware {

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String EXPIRE_TIME = "EX";

    private static final Long RELEASE_SUCCESS = 1L;

    public static ApplicationContext contextHolder;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        contextHolder = applicationContext;
    }

    @Override
    public void close() throws Exception {

    }

    public RedisExecutor getExecutor() {
        if (contextHolder != null) {
            return contextHolder.getBean(RedisExecutor.class);
        }
        return null;
    }

    public boolean tryGetDistributedLock(String lockKey, long expireTime, String clientId) {
        RedisExecutor executor = getExecutor();
        if (executor != null) {
            return executor.doInRedis(instance -> {
                String response = instance.set(lockKey, clientId, SET_IF_NOT_EXIST, EXPIRE_TIME, expireTime);
                return LOCK_SUCCESS.equals(response);
            });
        }
        return false;
    }


    /**
     * 释放分布式锁
     *
     * @param jedis    Redis客户端
     * @param lockKey  锁
     * @param clientId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String clientId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(clientId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;


    }


}

