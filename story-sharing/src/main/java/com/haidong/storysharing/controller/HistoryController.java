package com.haidong.storysharing.controller;

import com.haidong.storysharing.VO.HistoryVO;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.entry.History;
import com.haidong.storysharing.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/16/22:49
 * @Description:
 */
@RestController
@Slf4j
public class HistoryController {
    @Autowired
    HistoryService historyService;


    @PostMapping("/addHistory")
    public void addHistory(@RequestBody  HistoryVO historyVO){
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null){
            History history = new History();
            history.setStoryId(historyVO.getStoryId());
            history.setUserId(historyVO.getUserId());
            history.setCreateTime(LocalDateTime.now());
            history.setUpdateTime(LocalDateTime.now());
            historyService.insertSelective(history);
        }else {
            log.info("用户未登录！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/deleteHistoryByUserId")
    public Result<Object> deleteHistoryByUserId(HistoryVO historyVO){
        System.out.println(historyVO);
        int i = historyService.deleteByUserId(historyVO.getUserId());
        Result<Object> result = new Result<>();
        if (i>0){
            log.error("用户删除浏览记录");
            result.setMessage("已清除浏览记录");
        }else {
            result.setMessage("清除记录失败");
            log.error("清除记录失败");
        }
        return result;

    }
}
