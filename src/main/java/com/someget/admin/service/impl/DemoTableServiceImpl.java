package com.someget.admin.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.someget.admin.common.sys.dal.entity.User;
import com.someget.admin.common.sys.service.UserService;
import com.someget.admin.dal.entity.TMaterial;
import com.someget.admin.dal.repository.MaterialRepository;
import com.someget.admin.model.vo.*;
import com.someget.admin.service.DemoTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.someget.admin.dal.repository.MaterialRepository.EMPTY_BLANK;

/**
 * demo业务实现
 *
 * @author zyf
 * @date 2022-04-17 15:04
 */
@Service
public class DemoTableServiceImpl implements DemoTableService {

    @Resource
    private MaterialRepository materialRepository;

    @Resource
    private UserService userService;

    public static final Long FIRST_PAGE = 1L;

    @Override
    public TableResponse<List<DemoListResVO>> getList(Page<TMaterial> page, DemoListReqVO request) {
        // 如果有查询条件, 则组织一下
        String name = request.getName();
        // 查询数据, 保护一下首页, 首页从缓存读
        PageDTO pageData;
        if (FIRST_PAGE.equals(page.getCurrent())) {
            // 如果是首页的话,name变成" "方便注解拼参数, 但是查表会过来空格串(这是其实是违反注解的用法, 不要模仿)
            pageData = materialRepository.selectFirstPage(Optional.ofNullable(name)
                    .filter(CharSequenceUtil::isNotBlank).orElse(EMPTY_BLANK));
            if (pageData == null) {
                pageData  = new PageDTO(Collections.emptyList(), 0L);
            }
        } else {
            pageData = materialRepository.selectPage(page, name);
        }
        // 清洗vo, 业务不复杂就直接vo了
        List<DemoListResVO> result = pageData.getData().stream().map(DemoListResVO::new).collect(Collectors.toList());
        return new TableResponse<>(result, page.getTotal());
    }

    @Override
    public void addDemoData(DemoAddReqVO demoAddReqVO) {
        // 因为demo也是用这份代码, 防止线上有人刷库, 所以做一下校验...
        boolean illegality = StringUtils.length(demoAddReqVO.getName()) > 10 || StringUtils.length(demoAddReqVO.getUnit()) > 10;
        Assert.isFalse(illegality, "在搞破坏么");
        TMaterial tMaterial = new TMaterial();
        BeanUtils.copyProperties(demoAddReqVO, tMaterial);
        User currentUser = userService.getCurrentUser();
        // 插入默认值
        tMaterial.setServiceId(currentUser.getServiceId());
        // 初始化0
        tMaterial.setNum(0);
        try {
            materialRepository.insert(tMaterial);
        } catch (DuplicateKeyException exception) {
            // 先借用一下
            throw new IllegalArgumentException("好像这个东西已经存在了哦~");
        }
    }
}
