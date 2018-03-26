package com.ring.core.service.impl.sys;

import com.ring.api.example.SysRoleMenuExample;
import com.ring.api.model.sys.SysRoleMenu;
import com.ring.core.mapper.sys.SysRoleMenuExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.sys.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CHAO 2018-03-10 14:43
 */
@Service
public class SysRoleMenuServiceImpl extends AbstractService<SysRoleMenu, SysRoleMenuExample> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuExMapper mapper;

    @Override
    public SysRoleMenuExMapper getMapper() {
        return mapper;
    }
}