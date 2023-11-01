package com.haidong.storysharing.config.security.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.dto.UserAuthorInfoDTO;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.UserAuthorInfoService;
import com.haidong.storysharing.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *  用户登录成功认证时的处理
 * @Author: HaiDong
 * @Date: 2023/03/12/23:52
 * @Description:
 */
@Component
@Slf4j
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserAuthorInfoService userAuthorInfoService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Result<UserAuthorInfoDTO> result = new Result();
        UserAuthorDTO userAuthorDTO = (UserAuthorDTO) authentication.getPrincipal();
        UserAuthorInfoDTO userAuthorInfo = BeanCopyUtils.copyObject(userAuthorDTO, UserAuthorInfoDTO.class);
        //根据用户中的用户信息id查找用户的基本信息并返回到前端
        UserAuthorInfo info = userAuthorInfoService.selectByPrimaryKey(userAuthorDTO.getUserInfoId());
        if (!ObjectUtils.isEmpty(info)){
            userAuthorInfo.setEmail(info.getEmail());
            userAuthorInfo.setHeadPicture(info.getHeadPicture());
            userAuthorInfo.setNickname(info.getNickname());
            userAuthorInfo.setWebSite(info.getWebSite());
            userAuthorInfo.setIntroduce(info.getIntroduce());
        }
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        result.setData(userAuthorInfo);
        result.setMessage(StatusCodeEnum.SUCCESS.getDesc());
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().print(s);
        log.info("用户验证成功，返回数据为:{}" , s);
    }
}
