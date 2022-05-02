package com.someget.admin.common.base;

import com.someget.admin.common.realm.AuthRealm;
import org.apache.shiro.SecurityUtils;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public class MySysUser {
    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Long id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getloginName();
    }

    public static String nickName() {
        return ShiroUser().getNickName();
    }

    public static Integer serviceId() {
        return ShiroUser().getServiceId();
    }

    public static AuthRealm.ShiroUser ShiroUser() {
        AuthRealm.ShiroUser user = (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}
