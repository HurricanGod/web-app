package cn.hurrican.test;

import cn.hurrican.apache.rocket.msg.queue.utils.JSONUtils;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Optional;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/9
 * @Modified 13:28
 */
public class JsonTest {

    @Test
    public void testMethod1(){
        String json = "{\n" +
                "    \"awardCycleTime\": \"5\",\n" +
                "    \"unlockPrizeCount\": \"3\",\n" +
                "    \"loadingPic\": \"url(\\\"http://newgametest.weijuju.com/res//image/mobile/newgame/opening_bg2.jpg\\\")\",\n" +
                "    \"loadingLogo\": \"url(\\\"http://newgametest.weijuju.com/res//image/mobile/newgame/opening_logo.png\\\")\",\n" +
                "    \"lotteryPic\": \"url(\\\"http://newgametest.weijuju.com/res//image/mobile/newgame/style_4/banner.png?v=6\\\")\",\n" +
                "    \"lotteryLogo\": \"url(\\\"http://newgametest.weijuju.com/res//image/mobile/newgame/lotto_logo.png?v=5\\\")\",\n" +
                "    \"loadingLink\": \"\",\n" +
                "    \"isOpenClick\": false,\n" +
                "    \"openClickPic\": \"http://newgametest.weijuju.com/res//image/mobile/newgame/open_click_btn.png\",\n" +
                "    \"copyrightLink\": \"\",\n" +
                "    \"globalAwardVirtualNum\": \"0\",\n" +
                "    \"jumpLinkBtn\": \"1\",\n" +
                "    \"advertise\": {\n" +
                "        \"adType\": 0\n" +
                "    },\n" +
                "    \"timeLimit\": \"0.1\",\n" +
                "    \"merIntro\": \"深圳花儿绽放网络科技股份有限公司\",\n" +
                "    \"address\": \"广东省深圳市南山区\",\n" +
                "    \"tel\": \"15012345678\"\n" +
                "}";

        JSONObject extendOperation = JSONObject.fromObject(json);
        Double minutes = Optional.ofNullable(JSONUtils.convertToDouble(extendOperation
                .get("timeLimit"))).map(e -> e * 60).orElse(60D);
        System.out.println("minutes = " + minutes);
    }
}
