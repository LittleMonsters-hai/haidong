package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.VO.UserInfoVo;
import com.haidong.storysharing.dao.UserAuthorInfoMapper;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.service.UserAuthorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/25/2:14
 * @Description:
 */
@Service
public class UserAuthorInfoServiceImpl implements UserAuthorInfoService {
    @Autowired(required = false)
    UserAuthorInfoMapper userAuthorInfoMapper;
    @Override
    public int insertOne(UserAuthorInfo userAuthorInfo) {
        userAuthorInfo.setCreateTime(LocalDateTime.now());
        userAuthorInfo.setUpdateTime(LocalDateTime.now());
        int count = userAuthorInfoMapper.insertOne(userAuthorInfo);
        return count;
    }

    @Override
    public UserAuthorInfo selectByPrimaryKey(Integer id) {
        return userAuthorInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 这里用户表中的用户名就是用户信息表中的Email
     * @param email
     * @return id
     */
    @Override
    public int getPrimaryKeyByEmail(String email) {
        int id = userAuthorInfoMapper.getPrimaryKeyByEmail(email);
        return id;
    }

    @Override
    public int modifyHeadPicture(String path, String email) {
        return userAuthorInfoMapper.modifyHeadPicture(path, email);
    }

    @Override
    public int updateUserInfo(UserInfoVo userInfoVo) {
        return userAuthorInfoMapper.updateUserInfo(userInfoVo);
    }

    @Override
    public int updateByPrimaryKeySelective(UserAuthorInfo userAuthorInfo) {
        return userAuthorInfoMapper.updateByPrimaryKeySelective(userAuthorInfo);
    }

    @Override
    public List<UserAuthorInfo> selectAll(Integer startIndex, Integer length) {
        return userAuthorInfoMapper.selectAll(startIndex, length);
    }

    @Override
    public int countUserInfo() {
        return userAuthorInfoMapper.countUserInfo();
    }

    @Override
    public int updateIsDisable(Integer id, Integer isDisable) {
        return userAuthorInfoMapper.updateIsDisable(id, isDisable);
    }

    @Override
    public int deleteUserInfo(Integer uid) {
        return userAuthorInfoMapper.deleteUserInfo(uid);
    }

    @Override
    public String selectIntro(Integer id) {
        return userAuthorInfoMapper.selectIntro(id);
    }
}
