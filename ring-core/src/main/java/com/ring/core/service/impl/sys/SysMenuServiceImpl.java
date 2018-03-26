package com.ring.core.service.impl.sys;

import com.ring.api.example.SysMenuExample;
import com.ring.api.model.sys.SysMenu;
import com.ring.core.mapper.sys.SysMenuExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.sys.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author CHAO 2018-03-10 14:43
 */
@Service
public class SysMenuServiceImpl extends AbstractService<SysMenu, SysMenuExample> implements SysMenuService {
    @Autowired
    private SysMenuExMapper mapper;

    @Override
    public SysMenuExMapper getMapper() {
        return mapper;
    }

    @Override
    public Set<SysMenu> getByRoleIds(List<Long> list) {
        return mapper.getByRoleIds(list);
    }
}