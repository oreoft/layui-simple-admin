package com.someget.admin.dal.repository;

import cn.hutool.core.text.CharSequenceUtil;
import cn.someget.cache.anno.Cache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.someget.admin.dal.entity.TMaterial;
import com.someget.admin.dal.mapper.MaterialMapper;
import com.someget.admin.model.vo.PageDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * demo数据对应的rep
 *
 * @author zyf
 * @date 2022-05-14 22:05
 */
@Repository
public class MaterialRepository {

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String MATERIAL_FIRST_PAGE_KEY = "admin:material:first-page:%s";

    public static final String EMPTY_BLANK = "empty-blank";


    @Cache(prefix = MATERIAL_FIRST_PAGE_KEY, clazz = Page.class)
    public PageDTO selectFirstPage(String name) {
        LambdaQueryWrapper<TMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (CharSequenceUtil.isNotBlank(name) && !EMPTY_BLANK.equals(name)) {
            lambdaQueryWrapper.like(TMaterial::getName, name);
        }
        Page<TMaterial> result = materialMapper.selectPage(new Page<>(1, 10), lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(result.getRecords())) {
            return null;
        }
        return new PageDTO(result.getRecords(), result.getTotal());
    }


    /**
     * 普通分页查询
     */
    public PageDTO selectPage(Page<TMaterial> page, String name) {
        LambdaQueryWrapper<TMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (CharSequenceUtil.isNotEmpty(name)) {
            lambdaQueryWrapper.like(TMaterial::getName, name);
        }
        Page<TMaterial> result = materialMapper.selectPage(page, lambdaQueryWrapper);
        return new PageDTO(result.getRecords(), result.getTotal());
    }

    /**
     * 插入一条数据
     */
    public void insert(TMaterial tMaterial) {
        try {
            materialMapper.insert(tMaterial);
        } finally {
            removeCache(tMaterial.getName());
        }
    }

    /**
     * 清理缓存
     */
    private void removeCache(String name) {
        redisTemplate.delete(String.format(MATERIAL_FIRST_PAGE_KEY, name));
        redisTemplate.delete(String.format(MATERIAL_FIRST_PAGE_KEY, EMPTY_BLANK));
    }
}
