package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.dao.StoryInfoMapper;
import com.haidong.storysharing.dto.StoryDTO;
import com.haidong.storysharing.dto.StoryInfoDTO;
import com.haidong.storysharing.entry.StoryInfo;
import com.haidong.storysharing.service.StoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/10/15:33
 * @Description:
 */
@Service
public class StoryInfoServiceImpl implements StoryInfoService {

    @Autowired(required = false)
    StoryInfoMapper storyInfoMapper;

    @Override
    public StoryInfoDTO getStoryInfo(Integer storyId) {
        return storyInfoMapper.getStoryInfo(storyId);
    }
}
