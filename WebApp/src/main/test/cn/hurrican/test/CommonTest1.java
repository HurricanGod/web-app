package cn.hurrican.test;

import cn.hurrican.annotation.ReadCache;
import cn.hurrican.model.ColorfulQuestion;
import cn.hurrican.model.Entry;
import cn.hurrican.service.DelCacheService;
import cn.hurrican.utils.CommonUtils;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
        long today = DateTimeUtils.getTimestampOnlyDate(System.currentTimeMillis());
        System.out.println("today = " + today);

        long timestamp = DateTimeUtils.getTimestampOnlyDate(today, -1);
        System.out.println("timestamp = " + timestamp);
        System.out.println();

        timestamp = DateTimeUtils.getTimestampOnlyDate(today, -2);
        System.out.println("timestamp = " + timestamp);
        System.out.println();

        timestamp = DateTimeUtils.getTimestampOnlyDate(today, 1);
        System.out.println("timestamp = " + timestamp);
        System.out.println();

    }

    @Test
    public void testMethod(){
        System.out.println(List.class.isAssignableFrom(ArrayList.class));
        System.out.println(ArrayList.class.isAssignableFrom(List.class));

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
        for (int i = 0; i < 1000; i++) {
            List<Integer> code = CommonUtils.generateCode(6, 0);
            System.out.println("size = " + new HashSet<>(code).size() + "\tcode = " + code);
        }
    }

    @Test
    public void testMethod12(){
        List<Integer> list = new ArrayList<>(16);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(100));
        }
        System.out.println(list);
        System.out.println();
        List<Integer> filterList = list.stream().filter(e -> e > 50).collect(Collectors.toList());
        System.out.println(filterList);
        System.out.println();
        if(filterList.size() > 0){
            filterList.remove(0);
        }
        System.out.println(filterList);
    }

    @Test
    public void testMethod13(){
        String enc = "UTF-8";
        String utm = "baidu.%E5%BE%AE%E4%BF%A1%E6%8A%95%E7%A5%A8.HX_%E5%BE%AE%E4%BF%A1%E6%8A%95%E7%A5%A8.post";
        try {
            String keyWord = URLDecoder.decode(utm, enc);
            System.out.println(keyWord);

            String[] array = keyWord.split("\\.");
            System.out.println(array.length);
            for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
