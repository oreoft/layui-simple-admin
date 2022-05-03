package com.someget.admin.common.sys.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Sets;
import com.someget.admin.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends DataEntity<User> {

    /**
     * 登录名
     */
	@TableField("login_name")
	private String loginName;
    /**
     * 昵称
     */
	@TableField(value = "nick_name")
	private String nickName;
    /**
     * 密码
     */
	private String password;
    /**
     * shiro加密盐
     */
	private String salt;
    /**
     * 手机号码
     */
	@TableField(value = "phone")
	private String phone ;
    /**
     * 邮箱地址
     */
	private String email;
	
	/**
	 * 账户是否锁定
	 */
	private Boolean locked;
	
    /**
     * 租户id
     */
	@TableField(value = "service_id")
	private Integer serviceId;


	private String icon;

	@TableField(exist=false)
	private Set<Role> roleLists = Sets.newHashSet();
	
	@TableField(exist=false)
	private Set<Menu> menus = Sets.newHashSet();

	@TableField(exist=false)
	private String roleName;
}
