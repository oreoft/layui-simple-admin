package com.someget.admin.common.sys.dal.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树vo
 *
 * @author zyf
 * @date 2022-04-17 15:04
 */
@Data
public class ZtreeVO implements Serializable {

    private Long id;

    private Long pid;

    private String name;

    private String url;

    private Boolean open = true;

    private Boolean isParent;

    private String icon;

    private List<ZtreeVO> children;
}
