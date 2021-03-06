package cn.hurrican.test;

import cn.hurrican.model.UniqueKeyElement;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.JsonExtendServiceUtils;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/9/21
 * @Modified 11:27
 */

public class OgnlExpressionTest {

    @Test
    public void testMethod0() throws OgnlException {
        OgnlContext context = new OgnlContext();
        // 将员工设置为根对象
        UniqueKeyElement element = new UniqueKeyElement();
        element.setNow(DateTimeUtils.addSpecifiedSecondToDate(new Date(), 3600));
        HashMap<String, Map<String, Map<String, Object>>> rootMap = new HashMap<>(16);
        HashMap<String, Map<String, Object>> son1 = new HashMap<>(16);
        HashMap<String, Map<String, Object>> son2 = new HashMap<>(16);

        Map<String, Object> grandson1 = new HashMap<>(16);
        grandson1.put("element", element);
        grandson1.put("key", 1000);

        Map<String, Object> grandson2 = new HashMap<>(16);
        grandson2.put("ele", element);
        grandson2.put("value", 1000);

        son1.put("grandson1", grandson1);
        son2.put("grandson1", grandson1);
        son2.put("grandson2", grandson2);
        rootMap.put("son1", son1);
        rootMap.put("son2", son2);
        context.put("root", rootMap);
        context.setRoot(rootMap);
        // 构建Ognl表达式的树状表示,用来获取  new java.lang.Date().after(#dept.now)
        Object expression = Ognl.parseExpression("#root.son1.grandson1.key");

        // 解析树状表达式，返回结果
        Object result = Ognl.getValue(expression, context, context.getRoot());

        System.out.println("result = " + result);
    }

    @Test
    public void testMethod1() throws IOException, OgnlException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("txt/init.json");
        String path = resource.getPath();
        System.out.println(path);
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] byteArray = new byte[2048];
        StringBuilder builder = new StringBuilder(8192);
        int len = 0;
        while ((len = inputStream.read(byteArray)) != -1) {
            String str = new String(byteArray, 0, len);
            builder.append(str);
        }
        String json = builder.toString();
        JsonExtendServiceUtils jsonUtil = JsonExtendServiceUtils.getInstance(json);
        Object model = jsonUtil.getValue("model");
        System.out.println("model = " + model);
        Integer c = jsonUtil.getValue("model.c", Integer.class);
        System.out.println("c = " + c);
        jsonUtil.setValue("model.d", Arrays.asList(1,2,3));
        jsonUtil.setValue("model.f_12.a1","1");

        System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        System.out.println(jsonUtil.getJson());

        System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  \n");
        System.out.println(jsonUtil.getValue("model.f_12.a1", Integer.class));
    }

    @Test
    public void testMethod2(){
        Map<String, Object> map = new HashMap<>(16);
        map.put("py", 100);
        map.put("java", 90);
        Map<Integer, Integer> other = new HashMap<>(8);
        other.put(1, 100);
        other.put(2, 90);
        JsonExtendServiceUtils jsonUtil = JsonExtendServiceUtils.getInstance("{}");
        jsonUtil.setValue("a.b.c.d.f", 1200000);
        jsonUtil.setValue("a.b.c.e", "Hello World");
        jsonUtil.setValue("a.b.c.m", map);
        jsonUtil.setValue("b", 99999);
        System.out.println(jsonUtil.getJson());
    }
}
