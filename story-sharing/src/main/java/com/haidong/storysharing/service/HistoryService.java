package com.haidong.storysharing.service;

import com.haidong.storysharing.entry.History;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/16/22:48
 * @Description:
 */
public interface HistoryService {

    /**
     * 添加历史记录
     * @param history
     * @return
     */
    int insertSelective(History history);

    /**
     * 根据用户id删除用户的浏览记录
     * @param id
     * @return
     */
    int deleteByUserId(Integer id);
}
