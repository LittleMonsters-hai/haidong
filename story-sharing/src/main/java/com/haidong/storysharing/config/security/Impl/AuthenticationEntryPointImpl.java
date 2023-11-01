package com.haidong.storysharing.config.security.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.enums.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *  用户没有登陆时的处理  没有认证
 * @Author: HaiDong
 * @Date: 2023/03/13/16:15
 * @Description:
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result<Object> result = new Result<>();
        result.setCode(StatusCodeEnum.NO_LOGIN.getCode());
        result.setMessage(StatusCodeEnum.NO_LOGIN.getDesc());
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().write(s);
        log.info("用户没有认证返回的数据{}",s);
    }
}
