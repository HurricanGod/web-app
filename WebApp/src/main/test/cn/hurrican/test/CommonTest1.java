package cn.hurrican.test;

import cn.hurrican.annotation.ReadCache;
import cn.hurrican.model.ColorfulQuestion;
import cn.hurrican.model.Entry;
import cn.hurrican.model.Riddle;
import cn.hurrican.service.DelCacheService;
import cn.hurrican.utils.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommonTest1 {

    @Test
    public void testMethod1() {
        String s = Joiner.on(";").skipNulls().join("abc-", 1);
        System.out.println(s);

        String str = null;
        Optional.ofNullable(str).ifPresent(System.out::println);
    }

    @Test
    public void testMethod2() {
        BiFunction<Integer, Integer, Boolean> function = (integer, integer2) -> (integer & integer2) == 0;

        BiFunction<Integer, Integer, Integer> function2 =
                ((Integer integer, Integer integer2) -> integer | integer2);
        Boolean result = function2.andThen((Integer value) -> value == ((1 << 2) - 1)).apply(1, 2);
        System.out.println("result = " + result);
    }

    @Test
    public void testMethod3() {
        List<String> list = Arrays.asList("BiConsumer.java", "BiFunction.java",
                "Consumer.java", "Function.py",
                "BiConsumer.py", "Hello.py");
        Predicate<String> predicate = (String s) -> s.startsWith("Bi");
        predicate.and((String s) -> s.endsWith("java")).or((String s) -> s.endsWith("py"));
        List<String> collect = list.stream().filter(predicate).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Test
    public void testMethod4() {
        String str = "type=set:authorization_statistics_from_advertise:aid=${aid}".replace("${aid}", "11596");
        String[] split = str.split(":");
        if(split.length == 3){
            System.out.println(split[0]);
            System.out.println(split[2]);

            String[] array = split[1].split("=");
            for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }
        }
    }

    @Test
    public void testMethod5() throws NoSuchMethodException {
        DelCacheService  service = new DelCacheService();

        Method method = service.getClass().getDeclaredMethod("zadd", String.class);
        System.out.println("method.toString() = " + method.toString());

        Annotation[] annotations = method.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {

            if(annotations[i] instanceof ReadCache){
                System.out.println(annotations[i]);
                Class<?> clazz = ((ReadCache) annotations[i]).clazz();
                System.out.println(clazz);
            }
        }

    }

    @Test
    public void testMethod6() throws IOException {
        Entry<String, String> entry = new Entry<>("1", "Hurrican");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(entry);

        System.out.println("s = " + s);
        s = "null";
        Entry value = mapper.readValue(s, Entry.class);
        System.out.println("value = " + value);
    }


    @Test
    public void testMethod7(){
        List<String> list = Arrays.asList("a", "b", "c");
        String[] memberArray = new String[list.size()];
        String[] toArray = list.toArray(memberArray);
        for (int i = 0; i < memberArray.length; i++) {
            System.out.println("memberArray:\t" + memberArray[i]);
            System.out.println("toArray:\t" +toArray[i]);
            System.out.println();
        }
    }

    @Test
    public void testMethod(){
       String response = "{\"access_token\":\"24.d964bdb44e6c6dd80e9d306599bb7ef3.2592000.1537086309.282335-11691521\",\"session_key\":\"9mzdX+OCadFTX3b9a6szil5rwbad+7FT5iH+gXyy1AUNbAPaLlstU+2RVM7ddVbht\\/h3Lq86wme9ehN9pfGMTdHzodbZVw==\",\"scope\":\"audio_voice_assistant_get audio_tts_post public brain_all_scope wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\\u6743\\u9650 vis-classify_flower lpq_\\u5f00\\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi\",\"refresh_token\":\"25.a01af1db041a95cde25d2a6739c1ed73.315360000.1849854309.282335-11691521\",\"session_secret\":\"2b21e6fb333549bd46b4b7f345aa6913\",\"expires_in\":2592000}\n";
        Map map = JSONUtils.toBean(response, Map.class);
        map.forEach((k,v) -> {
            System.out.printf("k = %s\nv = %s \n\n", k, v);
        });
    }

    @Test
    public void testMethod9() throws JsonProcessingException {
        ColorfulQuestion question = ColorfulQuestion.newInstance().setType(1)
                .setOtherShow("除了组合javax.validation.constraints中的注解，还可以自定义校验器（Validator）进行数据校验");
        Class clazz = question.getClass();
        ObjectMapper mapper = new ObjectMapper();

        String s = mapper.writeValueAsString(question);
        System.out.println("s = " + s);

        JSONObject json = JSONUtils.creator(s).getJson();
        System.out.println(json.get("type"));
        System.out.println(json.get("otherShow"));

    }


    @Test
    public void testMethod11(){
        List<Riddle> riddles = new ArrayList<>(16);
        Riddle riddle = new Riddle();
        riddle.setRightIndex("0");
        riddle.setQuestion("Hello is World");
        riddle.setDeleted(false);
        riddle.setPlatformId(0);
        ArrayList<String> answer = new ArrayList<>();
        answer.add("aa");
        answer.add("ab");
        answer.add("ac");
        riddle.setAnswerList(answer);
        for (int i = 0; i < 5; i++) {
            riddles.add(riddle);
        }
        JSONArray jsonArray = JSONArray.fromObject(riddles);
        String json = jsonArray.toString();
        System.out.println(json);


        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        List<Riddle> list = JSONUtils.toList(json, Riddle.class);
        list.forEach(System.out::println);
    }
}
