package com.haidong.storysharing.controller;

import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.UserAuthorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/25/2:25
 * @Description:
 */
@Slf4j
@RequestMapping("/userInfo")
@RestController
public class UserAuthorInfoController {
    @Autowired
    UserAuthorInfoService userAuthorInfoService;

    @PostMapping("/insertOne")
    public Result<Integer> insertOne(@RequestBody UserAuthorInfo userAuthorInfo){
        Result<Integer> result = new Result<>();
        int count = userAuthorInfoService.insertOne(userAuthorInfo);
        if (count > 0){
            result.setData(count);
            result.setMessage(StatusCodeEnum.SUCCESS.getDesc());
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            log.info("用户信息注册成功！");
        }else {
            result.setMessage(StatusCodeEnum.FAIL.getDesc());
            result.setCode(StatusCodeEnum.FAIL.getCode());
            log.info("用户信息注册失败！");
        }
        return result;
    }

}
