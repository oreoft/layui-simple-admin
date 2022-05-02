package com.someget.admin.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 字段的自动填充
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Slf4j
@Component
public class SysMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("正在调用该insert填充字段方法");
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("正在调用该update填充字段方法");
    }
}
