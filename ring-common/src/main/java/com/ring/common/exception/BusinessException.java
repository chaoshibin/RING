package com.ring.common.exception;

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

    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }


    public BusinessException(ResultEnum result) {
        super(result.getMsg());
        this.code = result.getCode();
    }

    public String getCode() {
        return code;
    }
}
