package com.someget.admin.model.vo;

import com.someget.admin.dal.entity.TMaterial;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author zyf
 * @date 2022-03-25 21:03
 */
@Data
@Accessors(chain = true)
public class DemoListResVO {
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

    /**
     * 更新时间
     */
    private LocalDateTime mtime;


    public DemoListResVO(TMaterial tMaterial) {
        // 偷懒, 勿cue
        BeanUtils.copyProperties(tMaterial, this);
    }
}
