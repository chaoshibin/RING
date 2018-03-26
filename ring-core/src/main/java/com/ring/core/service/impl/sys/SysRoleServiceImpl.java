package com.ring.core.service.impl.sys;

import com.ring.api.example.SysRoleExample;
import com.ring.api.model.sys.SysRole;
import com.ring.core.mapper.sys.SysRoleExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.sys.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CHAO 2018-03-10 14:43
 */
@Service
public class SysRoleServiceImpl extends AbstractService<SysRole, SysRoleExample> implements SysRoleService {
    @Autowired
    private SysRoleExMapper mapper;

    @Override
    public SysRoleExMapper getMapper() {
        return mapper;
    }
}