package com.someget.admin.dal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展示物品表的实体类
 *
 * @author zyf
 * @date 2022-03-25 23:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TMaterial extends BaseEntity {
    /**
     * 名称
     */
    private String name;

    /**
     * 预警值1
     */
    private Integer lightWarningNum;

    /**
     * 预警值2
     */
    private Integer heavyWarningNum;

    /**
     * 现存数量
     */
    private Integer num;

    /**
     * 单位
     */
    private String unit;

}
