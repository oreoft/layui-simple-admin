package com.someget.admin.facade.veiw;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


/**
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Controller
@RequestMapping("/mng")
public class ViewDemoTableController {

    /**
     * 跳转数据展示页面
     */
    @GetMapping("/demo/list")
    public String showList(Model model) {
        return "mng/demoList";
    }

    /**
     * 跳转数据添加页面
     */
    @GetMapping("/demo/add")
    public String addList(Model model) {
        // 我实在是太懒了, 大家懂就好, 这里假装mock了点数据
        List<Map<String, String>> nameDic = Lists.newArrayList(
                MapUtil.builder("id", "1").put("name", "包子").build(),
                MapUtil.builder("id", "2").put("name", "袜子").build(),
                MapUtil.builder("id", "3").put("name", "电脑").build(),
                MapUtil.builder("id", "4").put("name", "香蕉").build(),
                MapUtil.builder("id", "5").put("name", "裤子").build(),
                MapUtil.builder("id", "6").put("name", "键盘").build(),
                MapUtil.builder("id", "7").put("name", "实在编不下去了").build()
        );
        model.addAttribute("materialDicList", nameDic);
        return "mng/demoAdd";
    }

}
