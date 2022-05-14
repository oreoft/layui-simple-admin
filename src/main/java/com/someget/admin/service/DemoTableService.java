package com.someget.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.someget.admin.dal.entity.TMaterial;
import com.someget.admin.model.vo.DemoAddReqVO;
import com.someget.admin.model.vo.DemoListReqVO;
import com.someget.admin.model.vo.DemoListResVO;
import com.someget.admin.model.vo.TableResponse;

import java.util.List;

/**
 * demo接口
 * @author zyf
 * @date 2022-05-01 16:05
 */
public interface DemoTableService {

    /**
     * 获取展示表单数据
     * @param page 分页信息
     * @param request 请求入参
     * @return 因为需要返回count, 所以在service返回response
     */
    TableResponse<List<DemoListResVO>> getList(Page<TMaterial> page, DemoListReqVO request);

    /**
     * 添加数据演示数据
     */
    void addDemoData(DemoAddReqVO demoAddReqVO);
}
