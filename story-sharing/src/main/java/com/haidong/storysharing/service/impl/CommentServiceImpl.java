package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.VO.CommentVO;
import com.haidong.storysharing.VO.ConditionVO;
import com.haidong.storysharing.dao.CommentMapper;
import com.haidong.storysharing.dto.CommentDTO;
import com.haidong.storysharing.dto.ReplyCountDTO;
import com.haidong.storysharing.dto.ReplyDTO;
import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.entry.Comment;
import com.haidong.storysharing.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/12/22:51
 * @Description:
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired(required = false)
    CommentMapper commentMapper;

    @Override
    public List<CommentDTO> listComments(int startIndex, int length, CommentVO commentVO) {
        int count = commentMapper.selectCount(commentVO);
        if (count == 0){
            return null;
        }
        //查询故事类的父级评论
        System.out.println("当前的current值为：" + commentVO.getCurrent());
        List<CommentDTO> commentDTOS = commentMapper.listComments((commentVO.getCurrent() - 1) * 2, 2, commentVO);
        if (CollectionUtils.isEmpty(commentDTOS)){
            log.info("查询到的数据为空");
            return null;
        }
        // 提取评论id集合
        List<Integer> commentIdList = commentDTOS.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        log.info(commentIdList.toString());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentMapper.listReplies(commentIdList);
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        System.out.println(replyMap.keySet());
        List<ReplyCountDTO> replyCountDTOS = commentMapper.listReplyCountByCommentId(commentIdList);
        System.out.println("replyCountDTOS == " + replyCountDTOS.size());
        Map<Integer, Integer> replyCountMap = commentMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOS.forEach(item -> {
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return commentDTOS;
    }

    @Override
    public int selectCount(CommentVO commentVO) {
        return commentMapper.selectCount(commentVO);
    }

    @Override
    public List<ReplyDTO> listReplies(List<Integer> commentIdList) {
        return commentMapper.listReplies(commentIdList);
    }

    @Override
    public List<ReplyCountDTO> listReplyCountByCommentId(List<Integer> commentIdList) {
        return null;
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        Comment comment = Comment.builder()
                .userId(commentVO.getUserId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(1)
                .createTime(LocalDateTime.now())
                .isDelete(0)
                .build();
        System.out.println(comment);
        commentMapper.saveComment(comment);
    }

    @Override
    public int deleteComment(Integer cid) {
        return commentMapper.deleteComment(cid);
    }

    @Override
    public int deleteReplay(Integer rid) {
        return commentMapper.deleteReplay(rid);
    }

    @Override
    public int deleteCommentsByUserId(Integer uid) {
        return commentMapper.deleteCommentsByUserId(uid);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer current, Integer size, Integer commentId) {
        return commentMapper.listRepliesByCommentId(current, size, commentId);
    }
}
