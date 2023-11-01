package com.haidong.storysharing.config.security.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.UserAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *  用户注销时进行的相关操作
 * @Author: HaiDong
 * @Date: 2023/03/13/16:57
 * @Description:
 */
@Component
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private UserAuthorService userAuthorService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Result result = new Result();
        if (authentication != null){
            log.info("用户验证的信息:{}", authentication.getPrincipal());
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setMessage(StatusCodeEnum.SUCCESS.getDesc());
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            log.info("用户注销成功，返回数据为:{}" , s);
            UserAuthorDTO user = (UserAuthorDTO)authentication.getPrincipal();
            userAuthorService.updateLoginTime(LocalDateTime.now(),user.getId());
        }else {
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage(StatusCodeEnum.FAIL.getDesc());
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            log.error("用户注销失败:{}", s);
        }


    }
}
