package cn.hurrican.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description: AOP相关的异常基类
 * @Date 2018/3/27
 * @Modified 11:34
 */
public class BaseAspectRuntimeException extends RuntimeException {

    private int exceptionCode;
    private String log;
    private Map<String, Object> returnVal = new HashMap<>();

    public static BaseAspectRuntimeException happend(int code, String msg) {
        return new BaseAspectRuntimeException(code, msg);
    }

    public BaseAspectRuntimeException put(String key, Object val) {
        this.returnVal.put(key, val);
        return this;
    }

    public BaseAspectRuntimeException returnMapEqual(Map<String, Object> model) {
        this.returnVal = model;
        return this;
    }

    public BaseAspectRuntimeException putAll(Map<String, Object> model) {
        this.returnVal.putAll(model);
        return this;
    }

    public BaseAspectRuntimeException logEqual(String log) {
        this.log = log;
        return this;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BaseAspectRuntimeException(int exceptionCode, String log) {
        this.exceptionCode = exceptionCode;
        this.log = log;
    }

    public BaseAspectRuntimeException(int exceptionCode, Map<String, Object> args) {
        this.exceptionCode = exceptionCode;
        this.returnVal = args;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BaseAspectRuntimeException(String message, int exceptionCode, String log) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.log = log;
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public BaseAspectRuntimeException(Throwable cause, int exceptionCode, Map<String, Object> returnVal) {
        super(cause);
        this.exceptionCode = exceptionCode;
        this.returnVal = returnVal;
    }

    public BaseAspectRuntimeException(Throwable cause, int exceptionCode) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Map<String, Object> getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(Map<String, Object> returnVal) {
        this.returnVal = returnVal;
    }
}
