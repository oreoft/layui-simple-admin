package com.someget.admin.common.sys.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.someget.admin.common.sys.dal.entity.Role;
import com.someget.admin.common.sys.dal.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserByMap(Map<String, Object> map);

    void saveUserRoles(@Param("userId")Long id, @Param("roleIds") Set<Role> roles);

    void dropUserRolesByUserId(@Param("userId")Long userId);

    Map selectUserMenuCount();
    
    User selectBaseUserByMap(Map<String, Object> map);

	List<User> pageListData(@Param("roleId")Long roleId, @Param("serviceId")Integer serviceId ,@Param("pageparam") Page<User> pageparam);
    
}
