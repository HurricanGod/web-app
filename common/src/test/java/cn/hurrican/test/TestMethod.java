package cn.hurrican.test;

import cn.hurrican.bean.BaseBean;
import cn.hurrican.bean.Bean1;
import cn.hurrican.common.codec.Base64;
import cn.hurrican.model.Riddle;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void testMethod1(){
        Riddle riddle = new Riddle();
        ArrayList<String> answer = new ArrayList<>();
        answer.add("0");
        answer.add("1");
        answer.add("2");
        answer.add("3");
        riddle.setDeleted(false);
        riddle.setQuestion("1+2+2+8=?");
        riddle.setRightIndex("0");
        riddle.setAnswerList(answer);

        ArrayList<Riddle> riddles = new ArrayList<>();
        riddles.add(riddle);
        riddles.add(riddle);
        riddles.add(riddle);

        JSONArray jsonArray = JSONArray.fromObject(riddles);

        System.out.println(jsonArray);

        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        String s = jsonArray.toString();

        List<Riddle> list = JSONUtils.toList(s, Riddle.class);

        Riddle r = list.get(0);
        list.forEach(System.out::println);

    }

    @Test
    public void testMethod2(){
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";

        byte[] b1 = Base64.decodeBase64(iv);
        byte[] b2 = Base64.decodeBase64(sessionKey);
    }


    @Test
    public void testMethod3(){
        Bean1 bean1 = new Bean1();
        BaseBean baseBean = new BaseBean();
        System.out.println(bean1 instanceof BaseBean);
        System.out.println(baseBean instanceof Bean1);

        System.out.println("1.0-0.9 == 0.1 = " + (1.0 - 0.9 == 0.1));
        System.out.println(1.0-0.9);
        System.out.println(1.0);
    }

    @Test
    public void testMethod4(){
        Bean1 bean1 = new Bean1("123", 1);
        JSONObject jsonObject = JSONUtils.creator().put("k1", 1).put("ad", bean1).getJson();
        String json = jsonObject.toString();
        System.out.println(json);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");

        JSONObject builder = JSONUtils.build(json);
        Object ad = builder.get("ad");
        System.out.println("ad = " + ad);
        System.out.println("ad.getClass() = " + ad.getClass());

    }

    @Test
    public void testMethod5(){
        HashMap<String, Object> map = new HashMap<>();
        map.remove("hello");
        System.out.println((new Date()).getTime());
    }




}
