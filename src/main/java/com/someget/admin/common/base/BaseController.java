package com.someget.admin.common.base;

import com.someget.admin.common.sys.service.MenuService;
import com.someget.admin.common.sys.service.RoleService;
import com.someget.admin.common.sys.service.UserService;

import javax.annotation.Resource;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public class BaseController {
	@Resource
	protected UserService userService;

	@Resource
	protected MenuService menuService;

	@Resource
	protected RoleService roleService;
}
