package com.haidong.storysharing.service;

import com.haidong.storysharing.dto.StoryInfoDTO;
import com.haidong.storysharing.entry.StoryInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/10/15:32
 * @Description:
 */
public interface StoryInfoService {
    StoryInfoDTO getStoryInfo(Integer storyId);
}
