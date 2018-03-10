package com.ring.api.constant;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/11
 * @author CHAO 修改日期：2018/2/11
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ResultEnum {

    OK(200, "success"),
    LACK_PARAM(50005, "参数不合法");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
