package com.ring.core.service.impl.sys;

import com.ring.api.example.SysUserRoleExample;
import com.ring.api.model.sys.SysUserRole;
import com.ring.core.mapper.sys.SysUserRoleExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.sys.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CHAO 2018-03-10 14:43
 */
@Service
public class SysUserRoleServiceImpl extends AbstractService<SysUserRole, SysUserRoleExample> implements SysUserRoleService {
    @Autowired
    private SysUserRoleExMapper mapper;

    @Override
    public SysUserRoleExMapper getMapper() {
        return mapper;
    }
}