package com.someget.admin.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @date 2022-03-25 23:03
 */
@Data
@Accessors(chain = true)
public class DemoAddReqVO {
    /**
     * 名字
     */
    @NotNull
    private String name;

    /**
     * 单位
     */
    @NotNull
    private String unit;

    /**
     * 预警值2
     */
    @Min(value = 1, message = "最小1")
    @Max(value = 100000, message = "最大100000")
    private String heavyWarningNum;

    /**
     * 预警值1
     */
    @Min(value = 1, message = "最小1")
    @Max(value = 100000, message = "最大100000")
    private String lightWarningNum;
}
