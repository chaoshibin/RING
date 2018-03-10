package com.ring.core.service.impl.sys;

import com.ring.api.example.SysUserExample;
import com.ring.api.model.sys.SysUser;
import com.ring.core.mapper.sys.SysUserExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CHAO 2018-03-10 14:43
 */
@Service
public class SysUserServiceImpl extends AbstractService<SysUser, SysUserExample> implements SysUserService {
    @Autowired
    private SysUserExMapper mapper;

    @Override
    public SysUserExMapper getMapper() {
        return mapper;
    }

    @Override
    public SysUser getByUsername(String username) {
        return mapper.getByUsername(username);
    }
}