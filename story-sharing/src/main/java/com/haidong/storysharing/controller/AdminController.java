package com.haidong.storysharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.VO.UserInfoVo;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dto.StoryConciseDTO;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.entry.UserAuthor;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.service.CommentService;
import com.haidong.storysharing.service.StoryService;
import com.haidong.storysharing.service.UserAuthorInfoService;
import com.haidong.storysharing.service.UserAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/13/18:09
 * @Description:
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    UserAuthorInfoService userAuthorInfoService;

    @Autowired
    UserAuthorService userAuthorService;

    @Autowired
    StoryService storyService;

    @Autowired
    CommentService commentService;

    /**
     * 获取用户信息
     * @param startIndex
     * @param length
     * @param response
     * @throws Exception
     */
    @GetMapping("/getAllUser/{startIndex}/{length}")
    public void getAllUser(@PathVariable Integer startIndex,
                               @PathVariable Integer length,
                               HttpServletResponse response
    )throws Exception{
        List<UserAuthor> userAuthors = userAuthorService.selectAll((startIndex-1) * length, length);
        int count = userAuthorService.countUser();
        Map<String,Integer> map = new HashMap<>();
        map.put("countUser",count);
        Result<List<UserAuthor>> result = new Result<>();
        result.setMap(map);
        result.setData(userAuthors);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    /**
     * 更改用户 禁言
     * @param id
     * @param isDisable
     * @param response
     * @throws Exception
     */
    @GetMapping("/userDisable/{id}/{isDisable}")
    public void userDisable(@PathVariable Integer id,@PathVariable Integer isDisable,HttpServletResponse response)throws Exception{
        int i = userAuthorInfoService.updateIsDisable(id, isDisable);
        Result<String> result = new Result<>();
        result.setData("修改成功");
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }


    /**
     * 删除用户信息
     * @param uid
     */
    @GetMapping("/deleteInfo/{uid}")
    public void deleteUserInfo(@PathVariable("uid") Integer uid) {
        userAuthorInfoService.deleteUserInfo(uid);
        userAuthorService.deleteUser(null,uid);
        commentService.deleteCommentsByUserId(uid);
        log.error("删除用户信息" + uid);
    }

    /**
     * 删除用户  删除用户的同时将用户的信息表进行删除
     * @param id
     */
    @GetMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Integer id){
        UserAuthor userAuthor = userAuthorService.selectByPrimaryKey(id);
        userAuthorService.deleteUser(id,null);
        userAuthorInfoService.deleteUserInfo(userAuthor.getUserInfoId());
        commentService.deleteCommentsByUserId(userAuthor.getUserInfoId());
        log.error("删除用户成功!");
    }



    /**
     * 返回用户信息 以及数据库中的数据量
     * @param startIndex
     * @param length
     * @param response
     * @throws Exception
     */
    @GetMapping("/getAllUserInfo/{startIndex}/{length}")
    public void getAllUserInfo(@PathVariable Integer startIndex,
                               @PathVariable Integer length,
                               HttpServletResponse response
    )throws Exception{
        List<UserAuthorInfo> userAuthorInfos = userAuthorInfoService.selectAll((startIndex - 1) * length, length);
        int count = userAuthorInfoService.countUserInfo();
        Map<String,Integer> map = new HashMap<>();
        map.put("countUserInfo",count);
        Result<List<UserAuthorInfo>> result = new Result<>();
        result.setData(userAuthorInfos);
        result.setMap(map);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    @GetMapping("/getAllStory")
    public void getAllStory(HttpServletResponse response) throws Exception{
        List<Story> stories = storyService.selectAllStory();
        Result<List<Story>> result = new Result<>();
        result.setData(stories);
        System.out.println(stories);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }


}
