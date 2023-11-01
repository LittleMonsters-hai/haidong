package com.haidong.storysharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.VO.UserAuthorVO;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.common.UserConstant;
import com.haidong.storysharing.entry.UserAuthor;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.RoleService;
import com.haidong.storysharing.service.UserAuthorInfoService;
import com.haidong.storysharing.service.UserAuthorService;
import com.haidong.storysharing.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/11/18:56
 * @Description:
 */
@RestController
@Slf4j
public class RegisterController {
    @Autowired
    UserAuthorService userAuthorService;

    @Autowired
    UserAuthorInfoService userAuthorInfoService;

    @Autowired
    RoleService roleService;

    @Autowired(required = false)
    StringRedisTemplate redisTemplate;

    @PostMapping("/register")
    @Transactional(rollbackFor = {Exception.class})
    public void register(
            @Valid @RequestBody UserAuthorVO userAuthorVO,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        Result<Integer> result = new Result<>();
        String userCode = userAuthorVO.getCode();
        String code = (String) request.getServletContext().getAttribute("code");
//        String code = redisTemplate.opsForValue().get("code");
        System.out.println("userCode=" + userAuthorVO.getCode());
        System.out.println("SystemCode=" + code);
        if ((!userAuthorVO.getCode().equals(code)) || (userCode==null) || (userCode.equals(""))){
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage("验证码不正确");
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            return;
        }
        //当前注册用户的用户名在数据库中不存在
        int count = userAuthorService.countOfUsername(userAuthorVO.getUsername());
        if (count == 0 && userAuthorVO.getCode().equals(code)){
            redisTemplate.delete("code");
            UserAuthorInfo userAuthorInfo = new UserAuthorInfo();
            //默认用户为非禁言状态
            userAuthorInfo.setIsDisable(0);
            userAuthorInfo.setEmail(userAuthorVO.getUsername());
            userAuthorInfo.setNickname(UserConstant.DEFAULT_NICKNAME);
            userAuthorInfo.setIntroduce(UserConstant.DEFAULT_INTRODUCE);
            userAuthorInfo.setHeadPicture(UserConstant.DEFAULT_HEAD_PICTURE);
            //插入用户信息
            userAuthorInfoService.insertOne(userAuthorInfo);
            //获取刚刚插入后的用户信息ID
            int userInfoId = userAuthorInfoService.getPrimaryKeyByEmail(userAuthorInfo.getEmail());
            UserAuthor userAuthor = BeanCopyUtils.copyObject(userAuthorInfo, UserAuthor.class);
            userAuthor.setUsername(userAuthorInfo.getEmail());
            userAuthor.setPassword(userAuthorVO.getPassword());
            userAuthor.setUserInfoId(userInfoId);
            //新增用户
            int count2 = userAuthorService.insertOne(userAuthor);

            //查询用户ID
            UserAuthor author = userAuthorService.selectOneByUsername(userAuthorInfo.getEmail());
            //插入用户 角色 默认的为普通用户
            roleService.insertOne(author.getId(),2);
            result.setMessage(StatusCodeEnum.SUCCESS.getDesc());
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setData(count2);
            log.info("用户注册成功！");
            //删除验证码
            request.getServletContext().removeAttribute("code");
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
        }else {
            result.setMessage("账户已存在");
            result.setCode(StatusCodeEnum.FAIL.getCode());
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            log.info("用户注册失败！");
        }
    }
}
