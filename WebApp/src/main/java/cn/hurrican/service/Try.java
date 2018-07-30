package cn.hurrican.service;

import java.util.Objects;
import java.util.function.Function;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/30
 * @Modified 11:10
 */
public class Try {
    @FunctionalInterface
    public static interface UncheckedFunction<T, R> {

        R apply(T t) throws Exception;
    }


    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }


    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper, R defaultR) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return defaultR;
            }
        };
    }

}
