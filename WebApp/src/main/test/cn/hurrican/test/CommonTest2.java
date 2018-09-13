package cn.hurrican.test;

import cn.hurrican.model.KnownAndActionRestfulRequestArgs;
import cn.hurrican.model.Riddle;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.XmlUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String filepath = "F:\\JavaCode\\web-app\\WebApp\\src\\main\\resources\\txt\\riddle.json";
        String json = FileUtils.readFileToString(new File(filepath));
        System.out.println("json = " + json);
        JSONArray jsonArray = JSONArray.fromObject(json);
        List<Riddle> list = new ArrayList<>(16);
        convertJsonArrayToList(jsonArray, list);

        list.forEach(System.out::println);
    }

    private void convertJsonArrayToList(JSONArray jsonArray, List<Riddle> list) {
        Riddle riddle = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject ele = (JSONObject) jsonArray.get(i);
            riddle = new Riddle();

            riddle.setId((Integer) ele.get("id"));
            riddle.setPlatformId((Integer) ele.get("platformId"));
            riddle.setAid((Integer) ele.get("aid"));
            riddle.setUid((Integer) ele.get("uid"));

            riddle.setImgUrl((String)ele.get("imgUrl"));
            riddle.setQuestion((String)ele.get("question"));
            riddle.setRightIndex(ele.get("rightIndex").toString());
            riddle.setDeleted((Boolean) ele.get("deleted"));
            Object extend = ele.get("extend");
            riddle.setExtend(extend.toString());

            JSONArray scoreList = (JSONArray)ele.get("scoreList");
            JSONArray answerList = (JSONArray) ele.get("answerList");

            riddle.setScoreList(new ArrayList<>(scoreList.size()));
            riddle.setAnswerList(new ArrayList<>(answerList.size()));


            for (int j = 0; j < scoreList.size(); j++) {
                riddle.getScoreList().add(scoreList.get(j).toString());
            }

            for (int j = 0; j < answerList.size(); j++) {
                riddle.getAnswerList().add(answerList.get(j).toString());
            }
            list.add(riddle);
        }
    }
}
