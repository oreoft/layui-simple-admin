package com.someget.admin.common.realm;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.Role;
import com.someget.admin.common.sys.dal.entity.User;
import com.someget.admin.common.sys.service.UserService;
import com.someget.admin.common.util.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Component(value = "authRealm")
public class AuthRealm extends AuthorizingRealm {

    @Lazy
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByLoginName(shiroUser.getloginName());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<Role> roles = user.getRoleLists();
        Set<String> roleNames = Sets.newHashSet();
        for (Role role : roles) {
            if (StringUtils.isNotBlank(role.getName())) {
                roleNames.add(role.getName());
            }
        }
        Set<Menu> menus = user.getMenus();
        Set<String> permissions = Sets.newHashSet();
        for (Menu menu : menus) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        info.setRoles(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //String username = token.getUsername();
        String username = (String) token.getPrincipal();
        User user = userService.findBaseUserByLoginName(username);
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        user = userService.findUserByLoginName(username);
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        byte[] salt = Encodes.decodeHex(user.getSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(user.getId(), user.getLoginName(), user.getNickName(), user.getIcon(), user.getServiceId()),
                user.getPassword(), //密码
                ByteSource.Util.bytes(salt),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public Integer serviceId;
        public String loginName;
        public String nickName;
        public String icon;


        public ShiroUser(Long id, String loginName, String nickName, String icon, Integer serviceId) {
            this.id = id;
            this.loginName = loginName;
            this.nickName = nickName;
            this.icon = icon;
            this.serviceId = serviceId;
        }

        public String getloginName() {
            return loginName;
        }

        public String getNickName() {
            return nickName;
        }

        public String getIcon() {
            return icon;
        }

        public Long getId() {
            return id;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return nickName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null) {
                return other.loginName == null;
            } else return loginName.equals(other.loginName);
        }
    }
}
