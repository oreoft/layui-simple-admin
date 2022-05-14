package com.someget.admin.dal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.someget.admin.dal.entity.TMaterial;
import org.apache.ibatis.annotations.Mapper;

/**
 * 演示物品表的dao
 * @author zyf
 * @date 2022-03-25 23:03
 */
@Mapper
public interface MaterialMapper extends BaseMapper<TMaterial> {
}
