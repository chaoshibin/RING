package com.ring.core.service.sys;

import com.ring.api.example.SysMenuExample;
import com.ring.api.model.sys.SysMenu;
import com.ring.core.service.Service;

import java.util.List;
import java.util.Set;

/**
 * @author CHAO 2018-03-10 14:43
 */
public interface SysMenuService extends Service<SysMenu, SysMenuExample> {
    Set<SysMenu> getByRoleIds(List<Long> list);
}