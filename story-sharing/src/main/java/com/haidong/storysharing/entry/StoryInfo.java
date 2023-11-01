package com.haidong.storysharing.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryInfo {
    private Long id;

    private Long storyId;

    private Long likeCount;

    private Long viewsCount;
}
