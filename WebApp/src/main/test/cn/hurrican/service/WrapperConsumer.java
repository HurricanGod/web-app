package cn.hurrican.service;

import java.util.function.Consumer;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/10
 * @Modified 15:58
 */
public interface WrapperConsumer<T> extends Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    @Override
    void accept(T t);
}
