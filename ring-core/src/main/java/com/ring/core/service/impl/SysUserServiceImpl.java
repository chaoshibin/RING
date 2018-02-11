package com.ring.core.service.impl;

import com.ring.api.example.SysUserExample;
import com.ring.api.model.SysUser;
import com.ring.core.mapper.SysUserExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CHAO 2018-02-06 20:58
 */
@Service
public class SysUserServiceImpl extends AbstractService<SysUser, SysUserExample> implements SysUserService {
    @Autowired
    private SysUserExMapper mapper;

    @Override
    public SysUserExMapper getMapper() {
        return mapper;
    }
}