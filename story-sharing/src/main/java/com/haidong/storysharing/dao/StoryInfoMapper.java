package com.haidong.storysharing.dao;

import com.haidong.storysharing.dto.StoryDTO;
import com.haidong.storysharing.dto.StoryInfoDTO;
import com.haidong.storysharing.entry.StoryInfo;

public interface StoryInfoMapper {
    StoryInfoDTO getStoryInfo(Integer storyId);
}
