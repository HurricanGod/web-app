package cn.hurrican.test;

import cn.hurrican.config.CacheBean;
import cn.hurrican.model.ColorfulQuestion;
import cn.hurrican.model.Entry;
import cn.hurrican.model.Questionnaire;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.JSONUtils;
import cn.hurrican.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/26
 * @Modified 10:46
 */
public class ComonTest {

    @Test
    public void testMethod0() throws IOException {
        ColorfulQuestion question = new ColorfulQuestion().setCorrectShow(new Entry<>("机制", "/img/hello.png"));
        question.setPageTemplateId(123).setWrongShow(new Entry<>("射雕", "/img/hello.png"));
        String s = JSONUtils.toString(question);
        System.out.println("net.sf.json.JSONObject序列化结果 = \n" + s);
        ColorfulQuestion bean = JSONUtils.toBean(s, ColorfulQuestion.class);
        System.out.println("net.sf.json.JSONObject反序列化结果 = \n" + bean);

        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(question);
        System.out.println("com.fasterxml.jackson.databind.ObjectMapper序列化结果 = \n " + json);
        ColorfulQuestion colorfulQuestion = objectMapper.readValue(json, ColorfulQuestion.class);
        System.out.println("com.fasterxml.jackson.databind.ObjectMapper反序列化结果 = \n " + colorfulQuestion);

        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        ArrayList<Entry<String, String>> entries = new ArrayList<>();
        entries.add(new Entry<>("a1","1"));
        entries.add(new Entry<>("a2","2"));
        entries.add(new Entry<>("a3","3"));
        entries.add(new Entry<>("a4","4"));

        String entryString = objectMapper.writeValueAsString(entries);
        System.out.println("entryString = " + entryString);
        List list = objectMapper.readValue(entryString, List.class);
        System.out.println("list = " + list);
        for(Object ele : list){
            System.out.println(ele);
        }
    }

    @Test
    public void testMethod1() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Entry<String, String>> entries = new ArrayList<>();
        entries.add(new Entry<>("答案1","如果有图片，这个字段就是图片路径"));
        entries.add(new Entry<>("答案2","如果有图片，这个字段就是图片路径"));
        entries.add(new Entry<>("答案3","如果有图片，这个字段就是图片路径"));


        ColorfulQuestion question = new ColorfulQuestion();
        question.setPageTemplateId(123).setRightIndex(0)
                .setCorrectShow(new Entry<>("答对时弹出的文案提示", "弹出的文案提示里需要的图片路径"))
                .setWrongShow(new Entry<>("答错时弹出的文案提示", "弹出的文案提示里需要的图片路径"))
                .setQuestion(new Entry<>("怎样用最短的时间过河", "如果有图片，这个字段就是图片路径"))
                .setType(1).setCandidateAnswer(entries);

        ArrayList<ColorfulQuestion> list = new ArrayList<>();
        list.add(question);
        list.add(question);
        String json = mapper.writeValueAsString(list);
        System.out.println(json);
        JavaType javaType = ObjectMapperUtils.getCollectionType(mapper, ArrayList.class, ColorfulQuestion.class);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        List<ColorfulQuestion> newList = mapper.readValue(json, javaType);
        newList.forEach(System.out::println);

    }

    @Test
    public void testMethod3() throws IOException {
        String json = "[{\"pageTemplateId\":123,\"type\":1,\"question\":{\"key\":\"怎样用最短的时间过河\",\"value\":\"如果有图片，这个字段就是图片路径\"},\"candidateAnswer\":[{\"key\":\"答案1\",\"value\":\"如果有图片，这个字段就是图片路径\"},{\"key\":\"答案2\",\"value\":\"如果有图片，这个字段就是图片路径\"},{\"key\":\"答案3\",\"value\":\"如果有图片，这个字段就是图片路径\"}],\"rightIndex\":0,\"correctShow\":{\"key\":\"答对时弹出的文案提示\",\"value\":\"弹出的文案提示里需要的图片路径\"},\"wrongShow\":{\"key\":\"答错时弹出的文案提示\",\"value\":\"弹出的文案提示里需要的图片路径\"},\"otherShow\":null,\"extend\":null},{\"uid\":null,\"platformId\":null,\"pageTemplateId\":123,\"type\":1,\"question\":{\"key\":\"怎样用最短的时间过河\",\"value\":\"如果有图片，这个字段就是图片路径\"},\"candidateAnswer\":[{\"key\":\"答案1\",\"value\":\"如果有图片，这个字段就是图片路径\"},{\"key\":\"答案2\",\"value\":\"如果有图片，这个字段就是图片路径\"},{\"key\":\"答案3\",\"value\":\"如果有图片，这个字段就是图片路径\"}],\"rightIndex\":0,\"correctShow\":{\"key\":\"答对时弹出的文案提示\",\"value\":\"弹出的文案提示里需要的图片路径\"},\"wrongShow\":{\"key\":\"答错时弹出的文案提示\",\"value\":\"弹出的文案提示里需要的图片路径\"},\"otherShow\":null,\"extend\":null}]\n";
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = ObjectMapperUtils.getCollectionType(mapper, ArrayList.class, ColorfulQuestion.class);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        List<ColorfulQuestion> newList = mapper.readValue(json, javaType);
        newList.forEach(System.out::println);
    }

    @Test
    public void testMethod4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        Object obj = list;
        Collection<Object> collectionValue = (Collection<Object>) obj;
        collectionValue.forEach(System.out::println);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        ArrayList<Entry<String, String>> entries = new ArrayList<>();
        entries.add(new Entry<>("答案1","如果有图片，这个字段就是图片路径"));
        entries.add(new Entry<>("答案2","如果有图片，这个字段就是图片路径"));
        entries.add(new Entry<>("答案3","如果有图片，这个字段就是图片路径"));

        ColorfulQuestion question = new ColorfulQuestion();
        question.setPageTemplateId(123).setRightIndex(0)
                .setCorrectShow(new Entry<>("答对时弹出的文案提示", "弹出的文案提示里需要的图片路径"))
                .setWrongShow(new Entry<>("答错时弹出的文案提示", "弹出的文案提示里需要的图片路径"))
                .setQuestion(new Entry<>("怎样用最短的时间过河", "如果有图片，这个字段就是图片路径"))
                .setType(1).setCandidateAnswer(entries);
        HashSet<ColorfulQuestion> set = new HashSet<>();
        set.add(question.setId(1));
        set.add(question.setId(2));
        set.add(question.setId(3));
        obj = set;
        collectionValue = (Collection<Object>) obj;
        collectionValue.forEach(System.out::println);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        Object[] objects = collectionValue.toArray();
        for(Object o : objects){
            System.out.println(mapper.writeValueAsString(o));
        }
    }

    @Test
    public void testMethod5(){
        CacheBean cacheBean = CacheBean.build();

        Boolean isCollection = Optional.ofNullable(cacheBean.getType())
                .map(e -> Arrays.stream(e.getInterfaces()).collect(Collectors.toSet())
                        .contains(Collection.class))
                .orElse(false);
        System.out.println(isCollection);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        cacheBean.setType(Set.class);
        isCollection = Optional.ofNullable(cacheBean.getType())
                .map(e -> Arrays.stream(e.getInterfaces()).collect(Collectors.toSet())
                        .contains(Collection.class))
                .orElse(false);
        System.out.println(isCollection);
    }

    @Test
    public void testMethod6() throws IOException {
        List<String> list = new ArrayList<>(10);
        list.add("aaa");
        list.add("abb");
        list.add("abc");

        Object obj = list;
        Collection<String> collection = (Collection<String>) obj;
        collection.forEach(System.out::println);
        System.out.println("\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");


        ObjectMapper mapper = new ObjectMapper();
        String key = "1.2";
        String s = mapper.writeValueAsString(key);
        System.out.println("s = " + s);
        String value = mapper.readValue(s, String.class);
        System.out.println("value = " + value);
    }

    @Test
    public void testMethod7(){
        String format = String.format("%s:%s:%s", 100, 10, "Hello");
        System.out.println("format = " + format);
        boolean equals = Objects.equals(format, "100:10:Hello");
        System.out.println("equals = " + equals);
    }


    @Test
    public void testMethod10(){
        ArrayList<Entry<String, String>> entries = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", "Wanderful");
        jsonObject.put("count", 10);

        entries.add(new Entry<>("a", "Hurrican"));
        entries.add(new Entry<>("b", "SuperHurrican"));
        entries.add(new Entry<>("c", jsonObject.toString()));

        Questionnaire q = new Questionnaire();
        q.setAid(1);
        q.setUid(1);
        q.setAid(11296);
        q.setPageTemplateId(128);
        q.setAnswerNotBlank(false);
        q.setHadDeleted(false);
        q.setOptionType(1);
        q.setQuestionType(1);
        q.setShowOrder(1);
        q.setOptionList(Arrays.asList("a.....", "b....", "c...."));
        q.setScoreList(entries);
        q.setQuestionContent("Are you ok?");
        q.setExtendString(jsonObject.toString());

        ArrayList<Questionnaire> arrayList = new ArrayList<>();
        arrayList.add(q);
        arrayList.add(q);
        System.out.println(JSONArray.fromObject(arrayList).toString());
    }


    @Test
    public void testMethod11() {
        String hello = ((Function<String, String>) s1 -> s1 + s1)
                .compose((Function<String, String>) String::toUpperCase).apply("Hello ");
        System.out.println(hello);

        String line = ((Function<String, String>) s1 -> Joiner.on(" = ").join(s1, 10))
                .compose(s2 -> "int " + s2)
                .andThen(str -> str + " ;").apply("a");
        System.out.println(line);
    }

    @Test
    public void testMethod12(){
        List<Integer> collect = Stream.of(Arrays.asList(1, 6, 9),
                Arrays.asList(1, 2), Arrays.asList(8, 12, 3))
                .flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void testMethod13() throws ParseException {
        Date date1 = DateTimeUtils.getSimpleDate("2018-10-16 0:0:0");
        long start = DateTimeUtils.parseDateToTimestamp(date1);
        Date date2 = DateTimeUtils.getSimpleDate("2018-11-16 0:0:0");
        long end = DateTimeUtils.parseDateToTimestamp(date2);
        System.out.println(end -start);

    }

    @Test
    public void testMethod14(){
        ArrayList<Integer> list = new ArrayList<>(16);
        list.add(6);
        list.add(8);
        list.add(5);
        list.add(1);
        list.add(112);
        list.add(10);
        List<Integer> collect = list.stream().sorted((e1, e2) -> e1 < e2 ? -1 : 0).collect(Collectors.toList());
        System.out.println(collect);
    }


}
