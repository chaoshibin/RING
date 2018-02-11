package com.ring.core.config.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/7
 * @author CHAO 修改日期：2018/2/7
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShiroAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return super.onAccessDenied(servletRequest,servletResponse);
    }
}
