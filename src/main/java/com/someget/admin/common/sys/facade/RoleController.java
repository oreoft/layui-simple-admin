package com.someget.admin.common.sys.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.someget.admin.common.base.BaseController;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.Role;
import com.someget.admin.common.sys.dal.entity.User;
import com.someget.admin.common.util.LayerData;
import com.someget.admin.common.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 角色相关的重启之
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Slf4j
@Controller
@RequestMapping("admin/system/role")
public class RoleController extends BaseController {

    @GetMapping("list")
    public String list() {
        return "admin/system/role/list";
    }

    @RequiresPermissions("sys:role:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Role> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                ServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Role> roleLayerData = new LayerData<>();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("del_flag", false);
        if (!map.isEmpty()) {
            String keys = (String) map.get("key");
            if (StringUtils.isNotBlank(keys)) {
                roleQueryWrapper.like("name", keys);
            }
        }
        long start = System.currentTimeMillis();
        Page<Role> rolePage = roleService.page(new Page<>(page, limit), roleQueryWrapper);
        log.info("查询角色耗时:{}ms, 查询结果{}", System.currentTimeMillis() - start, rolePage.getRecords());
        roleLayerData.setCount(rolePage.getTotal());
        roleLayerData.setData(setUserToRole(rolePage.getRecords()));
        return roleLayerData;
    }

    private List<Role> setUserToRole(List<Role> roles) {
        List<Long> userIds = roles.stream()
                .flatMap(e -> Lists.newArrayList(e.getCreateId(), e.getUpdateId()).stream())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, User> userMap = userService.batchUserByPk(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        for (Role r : roles) {
            if (r.getCreateId() != null && r.getCreateId() != 0) {
                User u =  userMap.get(r.getCreateId());
                if (u != null && StringUtils.isBlank(u.getNickName())) {
                    u.setNickName(u.getLoginName());
                }
                r.setCreateUser(u);
            }
            if (r.getUpdateId() != null && r.getUpdateId() != 0) {
                User u =   userMap.get(r.getUpdateId());
                if (u != null && StringUtils.isBlank(u.getNickName())) {
                    u.setNickName(u.getLoginName());
                }
                r.setUpdateUser(u);
            }
        }
        return roles;
    }

    @GetMapping("add")
    public String add(Model model) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("parentId", 0);
        map.put("isShow", false);
        List<Menu> menuList = menuService.selectAllMenus(map);
        model.addAttribute("menuList", menuList);
        return "admin/system/role/add";
    }

    @RequiresPermissions("sys:role:add")
    @PostMapping("add")
    @ResponseBody
    public RestResponse add(@RequestBody Role role) {
        if (StringUtils.isBlank(role.getName())) {
            return RestResponse.failure("角色名称不能为空");
        }
        if (roleService.getRoleNameCount(role.getName()) > 0) {
            return RestResponse.failure("角色名称已存在");
        }
        roleService.saveRole(role);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        Role role = roleService.getRoleById(id);
        //StringBuilder menuIds = new StringBuilder();
        List<Long> menuIds = Lists.newArrayList();
        if (role != null) {
            Set<Menu> menuSet = role.getMenuSet();
            if (menuSet != null && menuSet.size() > 0) {
                for (Menu m : menuSet) {
                    //menuIds.append(m.getId().toString()).append(",");
                    menuIds.add(m.getId());
                }
            }
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("parentId", 0);
        map.put("isShow", false);
        List<Menu> menuList = menuService.selectAllMenus(map);
        model.addAttribute("role", role);
        model.addAttribute("menuList", menuList);
        model.addAttribute("menuIds", menuIds);
        return "admin/system/role/edit";
    }

    @RequiresPermissions("sys:role:edit")
    @PostMapping("edit")
    @ResponseBody
    public RestResponse edit(@RequestBody Role role) {
        if (role.getId() == null || role.getId() == 0) {
            return RestResponse.failure("角色ID不能为空");
        }
        if (StringUtils.isBlank(role.getName())) {
            return RestResponse.failure("角色名称不能为空");
        }
        Role oldRole = roleService.getRoleById(role.getId());
        if (!oldRole.getName().equals(role.getName())) {
            if (roleService.getRoleNameCount(role.getName()) > 0) {
                return RestResponse.failure("角色名称已存在");
            }
        }
        roleService.updateRole(role);
        return RestResponse.success();
    }

    @RequiresPermissions("sys:role:delete")
    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (id == null || id == 0) {
            return RestResponse.failure("角色ID不能为空");
        }
        Role role = roleService.getRoleById(id);
        roleService.deleteRole(role);
        return RestResponse.success();
    }

    @RequiresPermissions("sys:role:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    public RestResponse deleteSome(@RequestBody List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return RestResponse.failure("请选择需要删除的角色");
        }
        for (Role r : roles) {
            roleService.deleteRole(r);
        }
        return RestResponse.success();
    }


}
