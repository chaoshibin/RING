package com.ring.common.exception;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/3/10
 * @author CHAO 修改日期：2018/3/10
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ArgumentException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ArgumentException(String message) {
        super(message);
    }
}
