package com.someget.admin.common.util;

import lombok.Data;

import java.util.List;

/**
 * layer的返回格式
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Data
public class LayerData<T> {
    private Integer code = 0;
    private Long count;
    private List<T> data;
    private String msg = "";
}
