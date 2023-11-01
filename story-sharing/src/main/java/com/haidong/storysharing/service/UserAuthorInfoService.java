package com.haidong.storysharing.service;

import com.haidong.storysharing.VO.UserInfoVo;
import com.haidong.storysharing.entry.UserAuthorInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/25/2:13
 * @Description:
 */
public interface UserAuthorInfoService {
    int insertOne(UserAuthorInfo userAuthorInfo);

    UserAuthorInfo selectByPrimaryKey(Integer id);

    /**
     * 这里用户表中的用户名就是用户信息表中的Email
     * @param email
     * @return
     */
    int getPrimaryKeyByEmail(String email);

    int modifyHeadPicture(String path, String email);

    int updateUserInfo(UserInfoVo userInfoVo);

    int updateByPrimaryKeySelective(UserAuthorInfo userAuthorInfo);

    List<UserAuthorInfo> selectAll(@Param("startIndex") Integer startIndex, @Param("length") Integer length);

    /**
     * 统计数据库中的数据量
     * @return
     */
    int countUserInfo();

    /**
     * 禁言
     * @param id
     * @param isDisable
     * @return
     */
    int updateIsDisable(Integer id, Integer isDisable);

    /**
     * 根据用户信息id删除用户信息
     * @param uid
     * @return
     */
    int deleteUserInfo(@Param("uid") Integer uid);

    /**
     * 返回用户的简介
     * @param id
     * @return
     */
    String selectIntro(Integer id);

}
