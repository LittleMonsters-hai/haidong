package com.haidong.storysharing.dao;

import com.haidong.storysharing.VO.ConditionVO;
import com.haidong.storysharing.dto.*;
import com.haidong.storysharing.entry.Story;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/10/18:39
 * @Description:
 */
public interface StoryDao {
    List<Story> selectAllStory();

    /**
     * 获取首页的故事信息
     * @param startIndex
     * @param length
     * @return
     */
    List<StoryHomeDTO> listStories(int startIndex, int length);

    /**
     * 获取用户已经发布过的故事信息
     * @param startIndex
     * @param length
     * @param uid
     * @return
     */
    List<StoryHomeDTO> listStoriesByUser(int startIndex, int length, int uid);

    Story selectOneById(int id);

    /**
     * 获取故事的简洁信息
     * @param startIndex
     * @param length
     * @param condition
     * @return
     */
    List<StoryConciseDTO> listStoriesByCondition(int startIndex, int length, ConditionVO condition);

    StoryDTO getStoryById(Integer storyId);

    /**
     * 根据ID查询一个故事
     * @param storyId
     * @return
     */
    StoryDTO getStoryByIdEdit(Integer storyId);

    /**
     * 统计用户发布的故事数量
     * @param userId
     * @return
     */
    int getStoryCount(Integer userId);

    /**
     * 用户发布故事 保存
     * @param
     * @return
     */
    int saveStory(Story story);

    /**
     * 删除用户的所有以发布的故事
     * @param uid
     * @return
     */
    int deleteStoryListByUserId(@Param("uid") Integer uid);

    /**
     * 用户更新故事
     * @param story
     * @return
     */
    int editStory(Story story);

    /**
     * 获取用户已经发布过的故事信息
     * @param startIndex
     * @param length
     * @return
     */
    List<StoryHistoryDTO> historyListStoriesByUser(int startIndex, int length ,Integer uid);

    /**
     * 根据id删除故事信息
     * @param id
     * @return
     */
    int deleteStory(Integer id);

    /**
     * 通过故事标题中的关键字进行模糊查询
     * @param keywords
     * @return
     */
    List<StorySearchDTO> listArticlesBySearch(String keywords);

    /**
     * 通过用户id 查询用户刚刚保存的故事ID
     * @param uid
     * @return
     */
    int getStoryIdByUserId(@Param("uid") Integer uid);

    /**
     * 根据浏览量 查询热门故事
     * @param storyIdList
     * @return
     */
    List<StorySearchDTO> hostStoryList(@Param("storyIdList") List<Integer> storyIdList);
}
