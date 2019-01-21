package com.ring.common.exception;

import com.ring.api.constant.ResultEnum;

/**
 * 功能描述: RPC异常
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/9
 * @author CHAO 修改日期：2018/2/9
 * @version 1.0.0
 * @since 1.0.0
 */
public final class LockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LockException(String message) {
        super(message);
    }
}
