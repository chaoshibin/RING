package com.ring.core.mapper.sys;

import com.ring.api.model.sys.SysUser;
import com.ring.core.mapper.base.sys.SysUserMapper;

/**
 * @author CHAO 2018-03-10 14:43
 */
public interface SysUserExMapper extends SysUserMapper {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysUser getByUsername(String username);

    int updateDeptIdIncr();
}