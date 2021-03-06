package com.ring.core.mapper.sys;

import com.ring.api.model.sys.SysUserRole;
import com.ring.core.mapper.base.sys.SysUserRoleMapper;

import java.util.List;

/**
 * @author CHAO 2018-03-10 14:43
 */
public interface SysUserRoleExMapper extends SysUserRoleMapper {

    /**
     * 通过用户ID查询角色
     *
     * @param userId
     * @return
     */
    List<SysUserRole> getRolesByUserId(Integer userId);
}