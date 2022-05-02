package com.someget.admin.common.exception;

import com.someget.admin.common.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 捕获全局异常
 *
 * @author zyf
 * @date 2022-04-16 17:03
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceBoost {

    @ExceptionHandler(IllegalArgumentException.class)
    public RestResponse processIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        log.warn("参数校验未通过，请求URL:[{}], 错误信息:{}", request.getRequestURL().toString(), e.getMessage());
        return RestResponse.failure(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RestResponse processValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMessage = "";
        if (CollectionUtils.isNotEmpty(errors)) {
            errorMessage = errors.get(0).getDefaultMessage();
        }
        String url = request.getRequestURL().toString();
        log.warn("参数非法，请求URL:[{}], 错误信息:{}", url, e.getMessage());
        return RestResponse.failure(errorMessage);
    }

    @ExceptionHandler({Exception.class})
    public RestResponse processException(HttpServletRequest request, Exception e) {
        String url = request.getRequestURL().toString();
        log.error("service层未捕获异常，请求URL:[{}] msg:{}", url,  e.getMessage());
        return RestResponse.failure("敖~, 服务器发生异常");
    }

    @ExceptionHandler({UnauthorizedException.class})
    public RestResponse unauthorizedException(HttpServletRequest request, UnauthorizedException e) {
        String url = request.getRequestURL().toString();
        log.error("用户发生越权，请求URL:[{}] msg:{}", url,  e.getMessage());
        return RestResponse.failure("咦~, 好像没有该权限哦~");
    }

}
