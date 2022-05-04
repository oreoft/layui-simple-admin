package com.someget.admin.common.sys.facade;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.someget.admin.common.base.BaseController;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.vo.ZtreeVO;
import com.someget.admin.common.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Controller
@RequestMapping("/admin/system/menu")
public class MenuController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @GetMapping("list")
    public String list(Model model) {
        return "admin/system/menu/test";
    }

    @RequiresPermissions("sys:menu:list")
    @PostMapping("tree")
    @ResponseBody
    public RestResponse tree() {
        List<ZtreeVO> ztreeVOs = menuService.showTreeMenus();
        LOGGER.info(JSONObject.toJSONString(ztreeVOs));
        return RestResponse.success().setData(ztreeVOs);
    }

    @RequiresPermissions("sys:menu:list")
    @PostMapping("treelist")
    @ResponseBody
    public RestResponse treelist() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("parentId", 0);
        map.put("isShow", false);
        return RestResponse.success().setData(menuService.selectAllMenus(map));
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "parentId", required = false) Long parentId, Model model) {
        if (parentId != null) {
            Menu menu = menuService.getById(parentId);
            model.addAttribute("parentMenu", menu);
        }
        return "admin/system/menu/add";
    }

    @RequiresPermissions("sys:menu:add")
    @PostMapping("add")
    @ResponseBody
    public RestResponse add(Menu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            return RestResponse.failure("菜单名称不能为空");
        }
        if (menuService.getCountByName(menu.getName()) > 0) {
            return RestResponse.failure("菜单名称已存在");
        }
        if (StringUtils.isNotBlank(menu.getPermission())) {
            if (menuService.getCountByPermission(menu.getPermission()) > 0) {
                return RestResponse.failure("权限标识已经存在");
            }
        }
        if (menu.getParentId() == null) {
            menu.setLevel(1);

            Integer count = Convert.toInt(menuService.getMap(new QueryWrapper<Menu>()
                    .select("max(sort) max").eq("parent_id", 0)).getOrDefault("max", null));
            int sort = 0;
            if (count != null && count != 0) {
                sort =  count + 10;
            }
            menu.setSort(sort);
        } else {
            Menu parentMenu = menuService.getById(menu.getParentId());
            if (parentMenu == null) {
                return RestResponse.failure("父菜单不存在");
            }
            menu.setParentIds(parentMenu.getParentIds());
            menu.setLevel(parentMenu.getLevel() + 1);

            Integer count = Convert.toInt(menuService.getMap(new QueryWrapper<Menu>()
                    .select("max(sort) max").eq("parent_id", 0)).getOrDefault("max", null));
            int sort = 0;
            if (count != null && count != 0) {
                sort =  count + 10;
            }
            menu.setSort(sort);
        }
        menu.setIcon(Optional.ofNullable(menu.getIcon()).orElse(""));
        menuService.saveOrUpdateMenu(menu);
        menu.setParentIds(StringUtils.isBlank(menu.getParentIds()) ? menu.getId() + "," : menu.getParentIds() + menu.getId() + ",");
        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        Menu menu = menuService.getById(id);
        model.addAttribute("menu", menu);
        return "admin/system/menu/edit";
    }

    @RequiresPermissions("sys:menu:edit")
    @PostMapping("edit")
    @ResponseBody
    public RestResponse edit(Menu menu) {
        if (menu.getId() == null) {
            return RestResponse.failure("菜单ID不能为空");
        }
        if (StringUtils.isBlank(menu.getName())) {
            return RestResponse.failure("菜单名称不能为空");
        }
        Menu oldMenu = menuService.getById(menu.getId());
        if (!oldMenu.getName().equals(menu.getName())) {
            if (menuService.getCountByName(menu.getName()) > 0) {
                return RestResponse.failure("菜单名称已存在");
            }
        }
        if (StringUtils.isNotBlank(menu.getPermission())) {
            if (!oldMenu.getPermission().equals(menu.getPermission())) {
                if (menuService.getCountByPermission(menu.getPermission()) > 0) {
                    return RestResponse.failure("权限标识已经存在");
                }
            }
        }
        if (menu.getSort() == null) {
            return RestResponse.failure("排序值不能为空");
        }
        menu.setIcon(Optional.ofNullable(menu.getIcon()).orElse(""));
        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();
    }

    @RequiresPermissions("sys:menu:delete")
    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            return RestResponse.failure("菜单ID不能为空");
        }
        Menu menu = menuService.getById(id);
        menu.setDelFlag(true);
        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();
    }

    @PostMapping("isShow")
    @ResponseBody
    public RestResponse isShow(@RequestParam(value = "id", required = false) Long id,
                               @RequestParam(value = "isShow", required = false) String isShow) {
        if (id == null) {
            return RestResponse.failure("菜单ID不能为空");
        }
        if (isShow == null) {
            return RestResponse.failure("设置参数不能为空");
        } else {
            if (!"true".equals(isShow) && !"false".equals(isShow)) {
                return RestResponse.failure("设置参数不正确");
            }
        }
        Boolean showFalg = Boolean.valueOf(isShow);
        Menu menu = menuService.getById(id);
        menu.setIsShow(showFalg);
        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();

    }

}
