package com.ring.web.util;


import java.io.Serializable;

/**
 * @author CHAO
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "0";

    /**
     * 状态码
     */
    private String code;
    /**
     * 消息明细
     */
    private String msg;
    /**
     * 结果对象
     */
    private T value;

    public static <T> Result create(T value) {
        return new Result(value);
    }

    public static Result create(String code, String msg) {
        return new Result(code, msg);
    }

    public static <T> Result create(String code, String msg, T value) {
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

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(String code, String msg, T value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
