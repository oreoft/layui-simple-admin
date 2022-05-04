package com.someget.admin.common.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.someget.admin.common.sys.dal.entity.Role;
import com.someget.admin.common.sys.dal.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public interface UserService extends IService<User> {

	User findUserByLoginName(String name);

	User findUserByNickName(String nickName);

	User findUserById(Long id);

	User saveUser(User user);

	User updateUser(User user);

	void saveUserRoles(Long id, Set<Role> roleSet);

	void dropUserRolesByUserId(Long id);

	int userCount(String param);

	void deleteUser(User user);

	Map selectUserMenuCount();

	User findBaseUserByLoginName(String name);

	User findUserBymobile(String mobile);

	List<User> pageListData(long roleId, Integer serviceId, Page<User> pageparam);

	User findById(Long id);

	User getCurrentUser();

	void logout();

	List<User> batchUserByPk(List<Long> userIds);
}
