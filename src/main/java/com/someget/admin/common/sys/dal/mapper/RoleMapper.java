package com.someget.admin.common.sys.dal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public interface RoleMapper extends BaseMapper<Role> {

    Role selectRoleById(@Param("id") Long id);

    void saveRoleMenus(@Param("roleId") Long id, @Param("menus") Set<Menu> menus);

    void dropRoleMenus(@Param("roleId") Long roleId);

    void dropRoleUsers(@Param("roleId") Long roleId);
}