package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.dao.HistoryMapper;
import com.haidong.storysharing.entry.History;
import com.haidong.storysharing.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/16/22:48
 * @Description:
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired(required = false)
    HistoryMapper historyMapper;

    @Override
    public int insertSelective(History history) {
        return historyMapper.insertSelective(history);
    }

    @Override
    public int deleteByUserId(Integer id) {
        return historyMapper.deleteByUserId(id);
    }
}
