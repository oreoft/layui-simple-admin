package com.someget.admin.common.sys.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.someget.admin.common.base.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends TreeEntity<Menu> {

	@TableId(type = IdType.AUTO)
	private Long id;

    private String name;

    private String icon;
	/**
     * 链接地址
     */
	private String href;
    /**
     * 打开方式
     */
	private String target;
    /**
     * 是否显示
     */
	private Boolean isShow;

	@TableField("bg_color")
	private String bgColor;
    /**
     * 权限标识
     */
	private String permission;

	@TableField(exist = false)
	private Integer dataCount;

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
