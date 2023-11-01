package com.haidong.storysharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.VO.CommentVO;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dto.CommentDTO;
import com.haidong.storysharing.dto.ReplyDTO;
import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.entry.Comment;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.CommentService;
import com.haidong.storysharing.service.UserAuthorInfoService;
import com.haidong.storysharing.service.UserAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/12/22:48
 * @Description:
 */
@RestController
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserAuthorService userAuthorService;

    @Autowired
    private UserAuthorInfoService userAuthorInfoService;

    /**
     * 查询评论
     * @param commentVO
     * @param response
     * @throws Exception
     */
    @GetMapping("/comments")
    public void listComments(
            CommentVO commentVO,
            HttpServletResponse response
    ) throws Exception{
        System.out.println(commentVO);
        List<CommentDTO> commentDTOS = commentService.listComments((commentVO.getCurrent() - 1) * 2, 5, commentVO);
        Result<List<CommentDTO>> result = new Result<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("count", commentService.selectCount(commentVO));
        result.setMap(map);
        result.setData(commentDTOS);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    /**
     * 添加评论
     * @param commentVO
     */
    @PostMapping("/comments")
    public Result<String> saveComment(@RequestBody CommentVO commentVO){
        Result<String> result = new Result<>();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!ObjectUtils.isEmpty(user)){
            Integer userInfoId = user.getUserInfoId();
            UserAuthorInfo userAuthorInfo = userAuthorInfoService.selectByPrimaryKey(userInfoId);
            Integer isDisable = userAuthorInfo.getIsDisable();
            //当用户没有禁言时  保存评论
            if (isDisable == 0){
                commentService.saveComment(commentVO);
                result.setMessage("评论成功。");
                result.setCode(StatusCodeEnum.SUCCESS.getCode());
            }else {
                result.setMessage("评论失败！你已被禁言，请联系管理员。");
                result.setCode(StatusCodeEnum.FAIL.getCode());
            }
        }
        return result;
    }

    /**
     * 删除评论以及评论下的回复
     * @param commentVO
     * @return
     */
    @PostMapping("/deleteComment")
    public Result<String> deleteComment(@RequestBody CommentVO commentVO){
        log.error("----------------->{}",commentVO);
        //根据id删除评论
        int i = commentService.deleteComment(commentVO.getCid());
        Result<String> result = new Result<>();
        result.setMessage("删除评论成功");
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        return result;
    }

    /**
     * 删除回复
     * @param commentVO
     * @return
     */
    @PostMapping("/deleteReplay")
    public Result<String> deleteReplay(@RequestBody CommentVO commentVO){
        log.error("----------------->{}",commentVO);
        //根据id删除评论
        int i = commentService.deleteComment(commentVO.getRid());
        Result<String> result = new Result<>();
        result.setMessage("删除回复成功");
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        return result;
    }

    /**
     * 查询评论下的回复
     * @param commentId
     */
    @GetMapping("/comments/{commentId}/replies")
    public void listRepliesByCommentId(@PathVariable("commentId") Integer commentId ,
                                       CommentVO commentVO,
                                       HttpServletResponse response) throws Exception{
        System.out.println(commentVO);
        List<ReplyDTO> replyDTOS = commentService.listRepliesByCommentId((commentVO.getCurrent() - 1) * 5, commentVO.getSize(), commentId);
        Result<List<ReplyDTO>> result = new Result<>();
        result.setData(replyDTOS);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }
}
