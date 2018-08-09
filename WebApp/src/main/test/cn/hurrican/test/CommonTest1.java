package cn.hurrican.test;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
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

    }
}
