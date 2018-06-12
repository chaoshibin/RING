package com.ring.web.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ring.core.util.ServletRequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/11
 * @author CHAO 修改日期：2018/2/11
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractController {
    protected static final Log LOG = LogFactory.get();

    public HttpServletRequest getRequest() {
        return ServletRequestContextHolder.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletRequestContextHolder.getResponse();
    }
}
