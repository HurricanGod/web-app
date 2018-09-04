package cn.hurrican.test;

import cn.hurrican.model.KnownAndActionRestfulRequestArgs;
import cn.hurrican.service.KnownAndActionCtServiceImpl;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.XmlUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/31
 * @Modified 11:50
 */

public class CommonTest2 {

    @Test
    public void testMethod0() throws Exception {
        String subscribeTime = DateTimeUtils.format(new Date());
        KnownAndActionRestfulRequestArgs args = new KnownAndActionRestfulRequestArgs();
        args.setExposedOpenid("ohVP20A0_-P1F4nKhvdLmJ_CfpWY");
        args .setSceneId("123456");
        args.setSubscribeTime(subscribeTime);
        args.setAppId("10038");


        String xml = XmlUtils.convertToXml(args, "UTF-8");
        System.out.println(xml);
    }

    @Test
    public void testMethod1() throws IOException {
        KnownAndActionCtServiceImpl service = new KnownAndActionCtServiceImpl();
        service.syncAwardRecordToThirdSystem(null, 123468);
    }
}
