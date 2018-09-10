package com.ring.api.util;

import com.ring.api.constant.ResultEnum;

import java.io.Serializable;

/**
 * @author CHAO
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
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

    private Result() {
    }

    private Result(String code, String msg, T value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public static <T> Result ok(T value) {
        return Result.create(ResultEnum.OK.getCode(), "成功", value);
    }

    public static Result error(String msg) {
        return Result.create(ResultEnum.ERROR.getCode(), msg);
    }

    public static <T> Result error(String msg, T value) {
        return Result.create(ResultEnum.ERROR.getCode(), msg, value);
    }

    public static Result retry(String msg) {
        return Result.create(ResultEnum.RETRY.getCode(), msg);
    }

    public static <T> Result retry(String msg, T value) {
        return Result.create(ResultEnum.RETRY.getCode(), msg, value);
    }

    public static <T> Result<T> create(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> create(String code, String msg, T value) {
        return new Result<>(code, msg, value);
    }

    public boolean isOk() {
        return ResultEnum.OK.getCode().equals(this.code);
    }

    public boolean isError() {
        return ResultEnum.ERROR.getCode().equals(this.code);
    }

    public boolean isRetry() {
        return ResultEnum.RETRY.getCode().equals(this.code);
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public T getValue() {
        return value;
    }

    private void setValue(T value) {
        this.value = value;
    }
}
