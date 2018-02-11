package com.ring.common.util;

import com.ring.api.constant.ResultEnum;

/**
 * 功能描述: 业务异常
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/9
 * @author CHAO 修改日期：2018/2/9
 * @version 1.0.0
 * @since 1.0.0
 */
public final class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;


    public BusinessException(ResultEnum result) {
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
