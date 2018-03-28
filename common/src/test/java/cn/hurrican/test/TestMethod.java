package cn.hurrican.test;

import cn.hurrican.utils.DateTimeUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/3/28
 * @Modified 13:29
 */
public class TestMethod {

    @Test
    public void testMethod0(){
        long end = 1525017600000L;

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(end);
        Date date = instance.getTime();
        String s = DateTimeUtils.format(date);
        System.out.println("s = " + s);

        System.out.println("date.after(new Date()) = " + date.after(new Date()));
    }
}
