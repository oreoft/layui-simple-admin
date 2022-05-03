package com.someget.admin.common.sys.dal.entity.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单vo
 *
 * @author zyf
 * @date 2022-04-17 15:04
 */
@Data
public class ShowMenu implements Serializable {
    private Long id;
    private Long pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread = false;
    private List<ShowMenu> children = Lists.newArrayList();
}
