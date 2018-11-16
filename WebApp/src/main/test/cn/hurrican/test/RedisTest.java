package cn.hurrican.test;

import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.utils.DateTimeUtils;
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

    private static final String SET_KEY = "type=set:openid";

    @Before
    public void before(){
        executor.doInRedis(instance -> {

            if(instance.exists(SORTED_SET_KEY)){
                System.out.println("删除 key = " + SORTED_SET_KEY);
                instance.del(SORTED_SET_KEY);
            }
            if(instance.exists(SET_KEY)){
                System.out.println("删除 key = " + SET_KEY);
                instance.del(SET_KEY);
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
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 4; j++) {
                    ipDigit[j] = random.nextInt(3);
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
            Set<String> set = null;
            int count = 9;
            String[] member = new String[count];
            for (int i = 0; i < 8; i++) {
                set = instance.zrangeByScore(SORTED_SET_KEY, 0, 0, 0, count);
                System.out.println("set = " + set);
                if(set != null && set.size() > 0){
                    instance.zrem(SORTED_SET_KEY, set.toArray(member));
                }
            }
            System.out.println(instance.zcard(SORTED_SET_KEY));
            System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
            set = instance.zrangeByScore(SORTED_SET_KEY, 0, 0);
            System.out.println(set);
        });
    }

    @Test
    public void testSet() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            String member = "Hurrican" + i;
            executor.doInRedis(instance -> {
                double score = DateTimeUtils.getTimeAddition(System.currentTimeMillis());
                instance.zadd(SORTED_SET_KEY, 1 + score, member);
            });
            System.out.println("sleep 1s ...");
            Thread.sleep(1000);
        }

        Set<Tuple> tuples = executor.doInRedis(instance -> {
            return instance.zrevrangeByScoreWithScores(SORTED_SET_KEY, Double.MAX_VALUE, Double.MIN_VALUE);
        });

        System.out.println("exec get operation!");
        tuples.forEach(t -> System.out.printf("%s -> %s\n", t.getElement(), t.getScore()));


    }
}
