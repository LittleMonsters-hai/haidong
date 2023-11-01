package com.haidong.storysharing.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/12/23:17
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    /**
     * 回复用户id
     */
    private Integer replyUserId;

    /**
     * 评论主题id
     */
    private Integer topicId;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String commentContent;

    /**
     * 父评论id
     */
    private Integer parentId;

    /**
     * 类型
     */
    @NotNull(message = "评论类型不能为空")
    private Integer type;

    /**
     * 用户信息id
     */
    private Integer userId;

    /**
     * 大小
     */
    private Integer size;

    /**
     * 评论id
     */
    private Integer cid;

    /**
     * 回复id
     */
    private Integer rid;

}
