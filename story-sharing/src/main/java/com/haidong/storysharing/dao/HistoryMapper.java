package com.haidong.storysharing.dao;

import com.haidong.storysharing.entry.History;

import java.util.List;

public interface HistoryMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 根据用户id删除用户的浏览记录
     * @param id
     * @return
     */
    int deleteByUserId(Integer id);

    int insert(History record);

    /**
     * 添加历史记录
     * @param history
     * @return
     */
    int insertSelective(History history);

    History selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);
}
