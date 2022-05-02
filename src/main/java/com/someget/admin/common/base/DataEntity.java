package com.someget.admin.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.someget.admin.common.sys.dal.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class DataEntity<T extends Model<T>> extends BaseEntity<T> {

    /**
     *  创建者
     */
    @TableField(value = "create_by")
    protected Long createId;

    /**
     * 创建日期
     */
    @TableField(value = "ctime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date ctime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    protected Long updateId;

    /**
     * 更新日期
      */
    @TableField(value = "mtime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date mtime;

    /**
     * 删除标记（Y：正常；N：删除；A：审核；）
     */
    @TableField(value = "del_flag")
    protected Boolean delFlag;

    /**
     * 备注
     */
    protected String remarks;

    /**
     * 创建着
     */
    @TableField(exist = false)
    protected User createUser;

    /**
     * 修改者
     */
    @TableField(exist = false)
    protected User updateUser;

}
