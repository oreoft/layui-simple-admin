package com.someget.admin.model.vo;

import com.someget.admin.dal.entity.TMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 因为注解没办法拿到Page的泛型, 所以单独创建一个page
 * @author zyf
 * @date 2022-05-14 23:05
 */
@Data
@AllArgsConstructor
public class PageDTO {
    /**
     * 东西的结果
     */
    private List<TMaterial> data;

    /**
     * 有多少数据
     */
    private Long total;
}
