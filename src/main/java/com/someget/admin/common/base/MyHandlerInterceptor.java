package com.someget.admin.common.base;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.someget.admin.common.sys.dal.entity.User;
import com.someget.admin.common.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Slf4j
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if (userService == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            userService = (UserService) factory.getBean("userService");
        }
        User user = userService.findUserById(MySysUser.id());
        log.info("{}-当前请求路径: {}, 登陆者是:{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), user == null ? "未登录" : user.getNickName());
        httpServletRequest.setAttribute("startTime", System.currentTimeMillis());
        if(user != null){
        	httpServletRequest.setAttribute("currentUser",user);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        Long start = Convert.toLong(httpServletRequest.getAttribute("startTime"));
        log.info("{}-接口{}, 入参[{}], 耗时{}ms", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
                JSON.toJSON(httpServletRequest.getParameterMap()), System.currentTimeMillis() - start);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
