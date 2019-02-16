package com.ring.core.config.dubbo;

import com.alibaba.dubbo.rpc.*;
import com.ring.common.util.CodecUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * Copyright (C), 2019-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2019/1/29 11:31
 * Description:
 */
public class LogFilter implements Filter {
    private final static String REQUEST_ID = "requestId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (StringUtils.isBlank(RpcContext.getContext().getAttachment(REQUEST_ID))) {
            String requestId = CodecUtil.createUUID();
            RpcContext.getContext().setAttachment(REQUEST_ID, requestId);
            MDC.put(REQUEST_ID, requestId);
        }
        return invoker.invoke(invocation);
    }
}
