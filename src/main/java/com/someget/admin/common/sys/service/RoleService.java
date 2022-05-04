package com.someget.admin.common.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.Role;

import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public interface RoleService extends IService<Role> {

    Role saveRole(Role role);

    Role getRoleById(Long id);

    void updateRole(Role role);

    void deleteRole(Role role);

    void saveRoleMenus(Long id, Set<Menu> menuSet);

    void dropRoleMenus(Long id);

    Integer getRoleNameCount(String name);

    List<Role> selectAll();
	
}
