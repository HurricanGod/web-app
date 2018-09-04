package cn.hurrican.test;

import cn.hurrican.model.ResMessage;
import cn.hurrican.service.LotteryService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/9/4
 * @Modified 16:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AspectTest {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void testMethod1(){
        System.out.println(lotteryService);
        ResMessage res = lotteryService.doLottery("ocOeEjhbPGbQZwq0E6K2sGxez0Q8");
        System.out.println(res);
    }

    @Test
    public void testMethod2(){
        ResMessage res = lotteryService.doLottery("ocOeEjhbPGbQZwq0E6K2sGxez0Q8", Maps.newHashMap());
        System.out.println(res);
    }

    @Test
    public void testMethod3(){
        ResMessage res = lotteryService.doLottery("ocOeEjhbPGbQZwq0E6K2sGxez0Q8", Maps.newHashMap(), true);
        System.out.println(res);
    }
}
