package com.someget.admin.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zyf
 * @date 2022-03-25 23:03
 */
@Data
@Accessors(chain = true)
public class DemoListReqVO {
    /**
     * 根据名字搜索
     */
    private String name;
}
