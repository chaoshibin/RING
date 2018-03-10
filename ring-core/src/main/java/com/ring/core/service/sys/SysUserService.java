package com.ring.core.service.sys;

import com.ring.api.example.SysUserExample;
import com.ring.api.model.sys.SysUser;
import com.ring.core.service.Service;

/**
 * @author CHAO 2018-03-10 14:43
 */
public interface SysUserService extends Service<SysUser, SysUserExample> {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysUser getByUsername(String username);
}