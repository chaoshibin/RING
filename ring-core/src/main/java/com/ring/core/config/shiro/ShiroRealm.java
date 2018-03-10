package com.ring.core.config.shiro;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ring.api.model.sys.SysMenu;
import com.ring.api.model.sys.SysRole;
import com.ring.api.model.sys.SysUser;
import com.ring.common.exception.ArgumentException;
import com.ring.common.util.Constant;
import com.ring.core.service.sys.SysMenuService;
import com.ring.core.service.sys.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author chaoshibin
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    protected static final Log LOG = LogFactory.get();
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysMenuService menuService;

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @see ：本例中该方法的调用时机为需授权资源被访问时
     * @see ：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOG.debug("执行shiro权限认证");
        //获取当前登录输入的用户名
        String username = (String) super.getAvailablePrincipal(principalCollection);
        SysUser user = userService.getByUsername(username);
        /* 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法 */
        if (user == null) {
            /** 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址 */
            return null;
        }
        /* 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）*/
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //List<SysMenu> menuList = (List<SysMenu>)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_ADMIN_PERMISSION);
        List<SysRole> roleList = user.getRoles();
        boolean isAdministrator = false;
        List<Long> roleIdList = new ArrayList<>();
        for (SysRole role : roleList) {
            roleIdList.add(role.getId());
            authorizationInfo.addRole(role.getRoleName());
            if (role.getId().equals(Constant.SUPER_ADMIN_ID)) {
                isAdministrator = true;
                break;
            }
        }
        List<SysMenu> menuList = new ArrayList<>();
        if (isAdministrator) {
            menuList = menuService.selectAll(new SysMenu());
        } else {
            if (roleIdList.size() > 0) {
                Set<SysMenu> menuSet = menuService.getByRoleIds(roleIdList);
                for (SysMenu sysMenu : menuSet) {
                    menuList.add(sysMenu);
                }
            }
        }
        menuList.forEach(menu -> {
            if (StringUtils.isNotEmpty(menu.getPermission())) {
                authorizationInfo.addStringPermission(menu.getPermission());
            }
        });
        return authorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        /*UsernamePasswordToken对象用来存放提交的登录信息*/
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SysUser user = userService.getByUsername(token.getUsername());
        if (user == null) {
            throw new ArgumentException("用户不存在");
        }
         /* 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验 */
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
    }
}
