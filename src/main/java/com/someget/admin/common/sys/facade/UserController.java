package com.someget.admin.common.sys.facade;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.someget.admin.common.base.BaseController;
import com.someget.admin.common.base.MySysUser;
import com.someget.admin.common.sys.dal.entity.Role;
import com.someget.admin.common.sys.dal.entity.User;
import com.someget.admin.common.sys.dal.entity.vo.ShowMenu;
import com.someget.admin.common.util.LayerData;
import com.someget.admin.common.util.RestResponse;
import com.someget.admin.common.util.SecurityConstants;
import com.someget.admin.common.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Controller
@RequestMapping("admin/system/user")
@Slf4j
public class UserController extends BaseController {

    @GetMapping("list")
    public String list() {
        return "admin/system/user/list";
    }

    @RequiresPermissions("sys:user:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<User> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                ServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<User> userLayerData = new LayerData<>();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!map.isEmpty()) {
            String keys = (String) map.get("key");
            if (StringUtils.isNotBlank(keys)) {
                userQueryWrapper.like("login_name", keys).or().like("phone", keys).or().like("email", keys);
            }
        }
        User currentUser = userService.getCurrentUser();
        userQueryWrapper.eq("service_id", currentUser.getServiceId());
        Page<User> userPage = userService.page(new Page<>(page, limit), userQueryWrapper);

        userLayerData.setCount(userPage.getTotal());
        userLayerData.setData(userPage.getRecords());
        return userLayerData;
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("roleList", roleService.selectAll());
        return "admin/system/user/add";
    }

    @RequiresPermissions("sys:user:add")
    @PostMapping("add")
    @ResponseBody
    public RestResponse add(@RequestBody User user) {
        if (StringUtils.isBlank(user.getPhone())) {
            return RestResponse.failure("员工手机不能为空");
        }
        if (user.getRoleLists() == null || user.getRoleLists().isEmpty()) {
            return RestResponse.failure("用户角色至少选择一个");
        }
        if (StringUtils.isNoneBlank(user.getPhone())) {
            if (userService.userCount(user.getPhone()) > 0) {
                return RestResponse.failure("该手机号已被绑定");
            }
        }
        if (userService.userCount(user.getLoginName()) > 0) {
            return RestResponse.failure("登录名称已经存在");
        }
        user.setEmail(String.format("%s@someget.cn", user.getLoginName()));
        user.setPassword(SecurityConstants.DEFAULT_PASSWORD);
        user.setServiceId(userService.getCurrentUser().getServiceId());
        userService.saveUser(user);
        if (user.getId() == null || user.getId() == 0) {
            return RestResponse.failure("保存用户信息出错");
        }
        return RestResponse.success("添加成功了哈, 初试密码是123456");
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        User user = userService.findUserById(id);
        Assert.notNull(user, "index");
        List<Long> roleIdList = Lists.newArrayList();
        Set<Role> roleSet = Optional.ofNullable(user.getRoleLists()).orElse(Collections.emptySet());
        if (!roleSet.isEmpty()) {
            for (Role r : roleSet) {
                roleIdList.add(r.getId());
            }
        }
        List<Role> roleList = roleService.selectAll();
        model.addAttribute("localuser", user);
        model.addAttribute("roleIds", roleIdList);
        model.addAttribute("roleList", roleList);
        return "admin/system/user/edit";
    }

    @RequiresPermissions("sys:user:edit")
    @PostMapping("edit")
    @ResponseBody
    public RestResponse edit(@RequestBody User user) {
        if (user.getId() == 0 || user.getId() == null) {
            return RestResponse.failure("用户ID不能为空");
        }
        if (StringUtils.isBlank(user.getPhone())) {
            return RestResponse.failure("员工手机不能为空");
        }
        if (user.getRoleLists() == null || user.getRoleLists().isEmpty()) {
            return RestResponse.failure("用户角色至少选择一个");
        }
        User oldUser = userService.findUserById(user.getId());

        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
        oldUser.setRoleLists(user.getRoleLists());
        oldUser.setNickName(user.getNickName());
        oldUser.setLoginName(user.getLoginName());
        userService.updateUser(oldUser);
        return RestResponse.success();
    }

    @RequiresPermissions("sys:user:delete")
    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (id == null || id == 0 || id == 1) {
            return RestResponse.failure("参数错误");
        }
        User user = userService.findUserById(id);
        if (user == null) {
            return RestResponse.failure("用户不存在");
        }
        if ("root".equals(user.getNickName())) {
            RestResponse.success("root用户你没有权限删除哦");
        }
        userService.removeById(user.getId());
        return RestResponse.success("删除成功");
    }

    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    public RestResponse deleteSome(@RequestBody List<User> users) {
        if (users == null || users.isEmpty()) {
            return RestResponse.failure("请选择需要删除的用户");
        }
        for (User u : users) {
            if (u.getId() == 1) {
                return RestResponse.failure("不能删除超级管理员");
            } else {
                userService.deleteUser(u);
            }
        }
        return RestResponse.success();
    }

    /***
     * 获得用户所拥有的菜单列表
     */
    @GetMapping("getUserMenu")
    @ResponseBody
    public List<ShowMenu> getUserMenu() {
        Long userId = MySysUser.id();
        return menuService.getShowMenuByUser(userId);
    }

    @GetMapping("userinfo")
    public String toEditMyInfo(Model model) {
        Long userId = MySysUser.id();
        User user = userService.findUserById(userId);
        model.addAttribute("userinfo", user);
        model.addAttribute("userRole", user.getRoleLists());
        return "admin/system/user/userInfo";
    }

    @PostMapping("saveUserinfo")
    @ResponseBody
    public RestResponse saveUserInfo(User user) {
        if (user.getId() == 0 || user.getId() == null) {
            return RestResponse.failure("用户ID不能为空");
        }
        if (StringUtils.isBlank(user.getPhone())) {
            return RestResponse.failure("员工手机不能为空");
        }
        User oldUser = userService.findUserById(user.getId());
        if (StringUtils.isNotBlank(user.getEmail())) {
            if (!user.getEmail().equals(oldUser.getEmail())) {
                if (userService.userCount(user.getEmail()) > 0) {
                    return RestResponse.failure("该邮箱已被使用");
                }
            }
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            if (!user.getPhone().equals(oldUser.getPhone())) {
                if (userService.userCount(user.getPhone()) > 0) {
                    return RestResponse.failure("该手机号已经被绑定");
                }
            }
        }
        user.setRoleLists(oldUser.getRoleLists());
        userService.updateUser(user);
        return RestResponse.success();
    }

    @GetMapping("changePassword")
    public String changePassword(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "admin/system/user/changePassword";
    }

    //    @RequiresPermissions("sys:user:changePassword")
    @PostMapping("changePassword")
    @ResponseBody
    public RestResponse changePassword(@RequestParam(value = "oldPwd", required = false) String oldPwd,
                                       @RequestParam(value = "newPwd", required = false) String newPwd,
                                       @RequestParam(value = "confirmPwd", required = false) String confirmPwd) {
        if (StringUtils.isBlank(oldPwd)) {
            return RestResponse.failure("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPwd)) {


            return RestResponse.failure("新密码不能为空");
        }
        if (StringUtils.isBlank(confirmPwd)) {
            return RestResponse.failure("确认密码不能为空");
        }
        if (!confirmPwd.equals(newPwd)) {
            return RestResponse.failure("确认密码与新密码不一致");
        }
        User user = userService.findUserById(MySysUser.id());

        //旧密码不能为空
        String pw = ToolUtil.entryptPassword(oldPwd, user.getSalt()).split(",")[0];
        if (!user.getPassword().equals(pw)) {
            return RestResponse.failure("旧密码错误");
        }
        user.setPassword(newPwd);
        ToolUtil.entryptPassword(user);
        userService.updateUser(user);
        return RestResponse.success();
    }


    /**
     * 用户注册页面
     *
     * @return
     */
    @GetMapping("register")
    public String register(Model model) {
        return "admin/system/user/register";
    }

    /**
     * 忘记密码页面
     *
     * @return
     */
    @GetMapping("forget")
    public String forget(Model model, String mobile) {
        model.addAttribute("mobile", mobile);
        return "admin/system/user/forget";
    }

    @GetMapping("change-password")
    @ResponseBody
    public Map<String, Object> changePassword(String loginName, String newPassword, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (!"0:0:0:0:0:0:0:1".equals(request.getRemoteHost())) {
            map.put("code", -1);
            map.put("msg", "保证安全只能内网操作");
            return map;
        }
        map.put("code", 0);
        map.put("msg", "修改成功");
        User user = userService.findUserByLoginName(loginName);
        if (user == null) {
            map.put("code", -1);
            map.put("msg", "该手机号未在系统内注册");
            return map;
        }
        user.setPassword(newPassword);
        ToolUtil.entryptPassword(user);
        userService.updateUser(user);
        return map;
    }


    @PostMapping("subForget")
    @ResponseBody
    public Map<String, Object> subForget(String phone, String vercode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "密码找回失败，功能暂未支持, 找一下你的超级管理员哈");
        try {
            User user = userService.findUserBymobile(phone);
            if (user == null) {
                map.put("code", -1);
                map.put("msg", "该手机号未在系统内注册");
                return map;
            }
            HttpSession session = request.getSession();
            if (session == null) {
                return RestResponse.failure("session超时");
            }
            String trueCode = (String) session.getAttribute(SecurityConstants.VALIDATE_CODE);
            if (StringUtils.isBlank(trueCode)) {
                return RestResponse.failure("验证码超时");
            }
            if (StringUtils.isBlank(vercode) || !trueCode.toLowerCase().equals(vercode.toLowerCase())) {
                map.put("code", -1);
                map.put("msg", "验证码错误");
                return map;
            }

            //生成6位随机数密码
            String newPwd = RandomUtil.randomString(6);
            user.setPassword(newPwd);
            ToolUtil.entryptPassword(user);

            //发送短信提醒用户
            Map<String, Object> parammap = new HashMap<>();
            parammap.put("passcode", newPwd);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("找回用户密码异常e:" + e.getMessage());
        }
        return map;


    }

}
