package com.someget.admin.facade;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.someget.admin.common.util.RestResponse;
import com.someget.admin.model.vo.DemoAddReqVO;
import com.someget.admin.model.vo.DemoListReqVO;
import com.someget.admin.model.vo.DemoListResVO;
import com.someget.admin.model.vo.TableResponse;
import com.someget.admin.service.impl.DemoTableServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zyf
 * @date 2022-03-25 20:03
 */
@RestController
@RequestMapping("/mng")
public class DemoTableController {

    @Resource
    private DemoTableServiceImpl demoService;


    /**
     * 跳转数据展示页面
     */
    @PostMapping("/demo/list")
    public TableResponse<List<DemoListResVO>> showList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                       DemoListReqVO request) {
        return demoService.getList(new Page<>(page, limit), request);
    }

    /**
     * 跳转数据展示页面
     */
    @PostMapping("/demo/add")
    public RestResponse addDemoData(@RequestBody @Valid DemoAddReqVO demoAddReqVO) {
        demoService.addDemoData(demoAddReqVO);
        return RestResponse.success();
    }



    /**
     * 下拉联动展示单位字典
     * 因为比较懒,这里就直接写死了
     */
    @GetMapping("/demo/unit/list")
    public TableResponse<List<String>> getUnitList(@RequestParam(value = "name") String name) {
        Map<String, List<String>> dicMap = MapUtil.<String, List<String>>builder()
                .put("包子", Lists.newArrayList("个", "箱子"))
                .put("袜子", Lists.newArrayList("双", "包"))
                .put("电脑", Lists.newArrayList("台", "箱"))
                .put("香蕉", Lists.newArrayList("个", "串"))
                .put("裤子", Lists.newArrayList("件", "包"))
                .put("键盘", Lists.newArrayList("个", "组")).build();
        return new TableResponse<>(dicMap.getOrDefault(name, Collections.singletonList("无")), 0L);
    }


}
