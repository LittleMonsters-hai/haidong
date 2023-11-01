package com.haidong.storysharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/17/19:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryTagDTO {
    private Integer storyId;

    private Integer tagId;
}
