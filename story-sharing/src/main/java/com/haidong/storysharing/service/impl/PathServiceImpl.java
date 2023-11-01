package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.dao.PathMapper;
import com.haidong.storysharing.entry.Path;
import com.haidong.storysharing.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/12/18:48
 * @Description:
 */
@Service
public class PathServiceImpl implements PathService {
    @Autowired(required = false)
    private PathMapper pathMapper;
    @Override
    public List<Path> getAllPath() {
        List<Path> allPath = pathMapper.getAllPath();
        return allPath;
    }
}
