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

    OK("OK", "成功"),
    ERROR("ERROR", "失败"),
    RETRY("RETRY", "重试"),
    LACK_PARAM("50005", "参数不合法"),
    INTERNAL_SERVER_ERROR("500", "服务内部错误");

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
