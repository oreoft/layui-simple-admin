package com.someget.admin.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所有角色的枚举
 * @author zyf
 * @date 2022-03-25 23:03
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

	/**
	 * 所有角色
	 */
	ROOT(1L,"超级管理员")
	;
	/**
	 * id
	 */
	private final Long id;
	/**
	 * 名字
	 */
	private final String name;
	
}
