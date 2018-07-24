package cn.hurrican.rabbitmq.consumer.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExecResult<T> {

    private int code;

    private T result;

    private String msg;

    public static <T> ExecResult<T> build() {
        return new ExecResult<>();
    }
}
