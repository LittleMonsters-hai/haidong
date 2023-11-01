package com.haidong.storysharing.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private Integer id;

    private Integer userId;

    private Integer topicId;

    private Integer replyUserId;

    private Integer parentId;

    private Integer type;

    private Integer isDelete;

    private Integer isReview;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String commentContent;

}
