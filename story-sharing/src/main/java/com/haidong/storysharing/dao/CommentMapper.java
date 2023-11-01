package com.haidong.storysharing.dao;

import com.haidong.storysharing.VO.CommentVO;
import com.haidong.storysharing.VO.ConditionVO;
import com.haidong.storysharing.dto.CommentDTO;
import com.haidong.storysharing.dto.ReplyCountDTO;
import com.haidong.storysharing.dto.ReplyDTO;
import com.haidong.storysharing.entry.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    List<CommentDTO> listComments(int startIndex, int length, CommentVO commentVO);

    int selectCount(CommentVO commentVO);

    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 添加评论
     *
     * @param comment 评论对象
     */
    void saveComment(Comment comment);

    /**
     * 在删除用户时，将用户的评论以及回复全部删除
     * @param uid
     * @return
     */
    int deleteCommentsByUserId(Integer uid);

    /**
     * 根据评论id删除评论
     * @param cid
     * @return
     */
    int deleteComment(Integer cid);

    /**
     * 根据回复id删除回复
     * @param rid
     * @return
     */
    int deleteReplay(Integer rid);

    /**
     * 查看当条评论下的回复
     *
     * @param commentId 评论id
     * @param current   当前页码
     * @param size      大小
     * @return 回复集合
     */
    List<ReplyDTO> listRepliesByCommentId(@Param("current") Integer current, @Param("size") Integer size, @Param("commentId") Integer commentId);

}
