package com.haidong.storysharing.config.security.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.enums.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 *
 * @Author: HaiDong
 * @Date: 2023/03/13/0:01
 * @Description:
 */
@Component
@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Result result = new Result();
        result.setCode(StatusCodeEnum.FAIL.getCode());
        result.setMessage("用户名或密码不正确！");
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().print(s);
        log.error("用户验证失败,返回数据:{}", s);
    }
}
