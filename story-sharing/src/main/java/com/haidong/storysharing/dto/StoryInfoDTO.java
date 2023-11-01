package com.haidong.storysharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/10/15:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryInfoDTO {
    /**
     * 点赞
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewsCount;
}
