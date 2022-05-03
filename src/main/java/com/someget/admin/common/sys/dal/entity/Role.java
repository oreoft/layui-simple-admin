package com.someget.admin.common.sys.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends DataEntity<Role> {

	/**
	 * 实体编号（唯一标识）
	 */
	@TableId
	private Long id;

	/**
	 * 角色名称
	 */
	private String name;

	@TableField(exist = false)
	private Set<Menu> menuSet;

	@TableField(exist = false)
	private Set<User> userSet;
}
