package com.haidong.storysharing.config.security.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.enums.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/13/16:43
 * @Description:
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result<Object> result = new Result<>();
        result.setCode(StatusCodeEnum.FAIL.getCode());
        result.setMessage("没有访问权限");
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().write(s);
        log.info("用户没有访问权限，返回数据：{}",s);
    }
}
