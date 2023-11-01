package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.VO.ConditionVO;
import com.haidong.storysharing.dao.CategoryMapper;
import com.haidong.storysharing.dao.StoryDao;
import com.haidong.storysharing.dto.*;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.service.CategoryService;
import com.haidong.storysharing.service.StoryService;
import com.haidong.storysharing.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/21/21:51
 * @Description:
 */
@Service
@Slf4j
public class StoryServiceImpl implements StoryService {
    @Autowired(required = false)
    StoryDao storyDao;

    @Autowired(required = false)
    private CategoryMapper categoryMapper;
    @Override
    public List<Story> selectAllStory() {
        List<Story> stories = storyDao.selectAllStory();
        if (stories != null) {
            log.info("查询成功。");
        }else {
            log.warn("查询失败！");
        }
        return stories;
    }

    @Override
    public List<StoryHomeDTO> listStories(int startIndex, int length) {
        List<StoryHomeDTO> listStories = storyDao.listStories(startIndex, length);
        return listStories;
    }

    @Override
    public Story selectOneById(int id) {
        Story story = storyDao.selectOneById(id);
        return story;
    }

    @Override
    public List<StoryConciseDTO> listStoriesByCondition(int startIndex, int length, ConditionVO condition) {
        List<StoryConciseDTO> storyConciseDTOS = storyDao.listStoriesByCondition(startIndex, length, condition);
        return storyConciseDTOS;
    }

    @Override
    public StoryDTO getStoryById(Integer storyId) {
        return storyDao.getStoryById(storyId);
    }

    @Override
    public StoryDTO getStoryByIdEdit(Integer storyId) {
        return storyDao.getStoryByIdEdit(storyId);
    }

    @Override
    public int getStoryCount(Integer userId) {
        return storyDao.getStoryCount(userId);
    }

    @Override
    public int saveStory(StoryPublishDTO storyPublishDTO) {
        Integer id = categoryMapper.getIdByName(storyPublishDTO.getCategoryName());
        storyPublishDTO.setCategoryId(id);
        storyPublishDTO.setIsDelete(0);
        storyPublishDTO.setCreateTime(LocalDateTime.now());
        storyPublishDTO.setModifyTime(LocalDateTime.now());
        Story story = BeanCopyUtils.copyObject(storyPublishDTO, Story.class);
        story.setIsTop(0);
        story.setOriginalUrl("www.baidu.com");
        int i = storyDao.saveStory(story);
        return i;
    }

    @Override
    public int deleteStoryListByUserId(Integer uid) {
        return storyDao.deleteStoryListByUserId(uid);
    }

    @Override
    public List<StoryHomeDTO> listStoriesByUser(int startIndex, int length, int uid) {
        return storyDao.listStoriesByUser(startIndex, length, uid);
    }

    @Override
    public int editStory(StoryPublishDTO storyPublishDTO) {
        Integer id = categoryMapper.getIdByName(storyPublishDTO.getCategoryName());
        storyPublishDTO.setCategoryId(id);
        storyPublishDTO.setIsDelete(0);
        storyPublishDTO.setCreateTime(LocalDateTime.now());
        storyPublishDTO.setModifyTime(LocalDateTime.now());
        Story story = BeanCopyUtils.copyObject(storyPublishDTO, Story.class);
        story.setIsTop(storyPublishDTO.getIsTop());
        story.setOriginalUrl("www.baidu.com");
        System.out.println("----------------->"+story);
        int i = storyDao.editStory(story);
        return i;
    }

    @Override
    public List<StoryHistoryDTO> historyListStoriesByUser(int startIndex, int length, Integer uid) {
        return storyDao.historyListStoriesByUser(startIndex, length, uid);
    }

    @Override
    public int deleteStory(Integer id) {
        return storyDao.deleteStory(id);
    }

    @Override
    public List<StorySearchDTO> listArticlesBySearch(String keywords) {
        return storyDao.listArticlesBySearch(keywords);
    }

    @Override
    public int getStoryIdByUserId(Integer uid) {
        return storyDao.getStoryIdByUserId(uid);
    }

    @Override
    public List<StorySearchDTO> hostStoryList(List<Integer> storyIdList) {
        return storyDao.hostStoryList(storyIdList);
    }
}
