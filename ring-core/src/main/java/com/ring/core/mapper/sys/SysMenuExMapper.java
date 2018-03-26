package com.ring.core.mapper.sys;

import com.ring.api.model.sys.SysMenu;
import com.ring.core.mapper.base.sys.SysMenuMapper;

import java.util.List;
import java.util.Set;

/**
 * @author CHAO 2018-03-10 14:43
 */
public interface SysMenuExMapper extends SysMenuMapper {
    Set<SysMenu> getByRoleIds(List<Long> list);
}