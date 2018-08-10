package cn.hurrican.test;

import cn.hurrican.redis.RedisExecutor;
import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.Random;
import java.util.Set;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/10
 * @Modified 11:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisTest {

    @Autowired
    private RedisExecutor executor;

    private static final String SORTED_SET_KEY = "type=sorted_set:ip";

    @Before
    public void before(){
        executor.doInRedis(instance -> {
            if(instance.exists(SORTED_SET_KEY)){
                System.out.println("删除 key = " + SORTED_SET_KEY);
                instance.del(SORTED_SET_KEY);
            }
        });
    }

    @Test
    public void testAddMembersToSortedSet(){
        executor.doInRedis(instance -> {
            Random random = new Random();
            Integer[] ipDigit = new Integer[4];
            Pipeline pipeline = instance.pipelined();
            String ip = null;
            for (int i = 0; i < 10000; i++) {
                for (int j = 0; j < 4; j++) {
                    ipDigit[j] = random.nextInt(2);
                }
                ip = Joiner.on(".").join(ipDigit);
                System.out.println("ip = " + ip);
                pipeline.zadd(SORTED_SET_KEY, 0, ip);
            }
            pipeline.sync();
            Long total = instance.zcard(SORTED_SET_KEY);
            System.out.println("total = " + total);
        });
    }

    @Test
    public void testZrangeByScore(){
        executor.doInRedis(instance -> {
            Set<Tuple> tuples = instance.zrangeWithScores(SORTED_SET_KEY, 0, 0);
            tuples.forEach(e -> {
                System.out.printf("member = %s\tscore = %s\n", e.getElement(), e.getScore());
            });

            tuples = instance.zrangeWithScores(SORTED_SET_KEY, -1, -1);
            tuples.forEach(e -> {
                System.out.printf("member = %s\tscore = %s\n", e.getElement(), e.getScore());
            });
        });
    }


    @Test
    public void testScard(){
        executor.doInRedis(instance -> {
            Long count = instance.scard("0xFFFFF");
            System.out.println("count = " + count);
        });
    }
}
