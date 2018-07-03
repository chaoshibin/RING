package com.ring.api.util;

import java.io.Serializable;

/**
 * @author CHAO
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer SUCCESS = 200;
    private static final Integer ERROR = 500;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息明细
     */
    private String msg;
    /**
     * 结果对象
     */
    private T value;

    public static Result getErrorInstance() {
        return Result.error("服务内部错误");
    }

    public static <T> Result info(T value) {
        return new Result(value);
    }

    public static Result error(String msg) {
        return new Result(ERROR, msg);
    }

    public static <T> Result create(T value) {
        return new Result(value);
    }

    public static Result create(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static <T> Result create(Integer code, String msg, T value) {
        return new Result(code, msg, value);
    }

    public boolean isOk() {
        return SUCCESS.equals(this.code);
    }

    public boolean isError() {
        return !isOk();
    }

    private Result() {
    }

    private Result(T value) {
        this.code = SUCCESS;
        this.msg = "成功";
        this.value = value;
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(Integer code, String msg, T value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
