package com.ring.core.config.shiro;

import cn.hutool.log.StaticLog;
import com.ring.core.mapper.SysUserExMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chaoshibin
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserExMapper userExMapper;

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @see ：本例中该方法的调用时机为需授权资源被访问时
     * @see ：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        StaticLog.debug("执行Shiro权限认证");

        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        return new SimpleAuthorizationInfo();
        //到数据库查是否有此对象
        /*SysUser user = userExMapper. (loginName);
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            //List<SysMenu> menuList = (List<SysMenu>)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_ADMIN_PERMISSION);
            //用户的角色集合
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
            List<SysRole> roleList = user.getRoles();
            boolean isAdministrator = false;
            List<Integer> roleIdList = new ArrayList<>();
            for (SysRole role : roleList) {
                if (role.getId() == Constants.ANMIN_ID) {
                    isAdministrator = true;
                }
                roleIdList.add(role.getId());
                authorizationInfo.addRole(role.getName());
            }
            List<SysMenu> menuList = new ArrayList<>();
            if (isAdministrator) {
                menuList = menuService.queryAll(new SysMenuQO());
            } else {
                if (roleIdList.size() > 0) {
                    menuList = menuService.queryByRoleIds(roleIdList);
                }
            }
            menuList.forEach(menu -> {
                if (StringUtil.isNotEmpty(menu.getPermission())) {
                    authorizationInfo.addStringPermission(menu.getPermission());
                }
            });
            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            simpleAuthorInfo.addRole("admin");
            //添加权限
//            simpleAuthorInfo.addStringPermission("admin:manage");
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            return authorizationInfo;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;*/
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //查出是否有此用户
      /*  SysUser user = userMapper.findByName(token.getUsername());
        if (user != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
        }*/
        return null;
    }
}
