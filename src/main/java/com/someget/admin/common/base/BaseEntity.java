package com.someget.admin.common.base;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity支持类
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntity<T extends Model<T>> extends Model<T>  {
    /**
     * 实体编号（唯一标识）
     */
    protected Long id;
}
