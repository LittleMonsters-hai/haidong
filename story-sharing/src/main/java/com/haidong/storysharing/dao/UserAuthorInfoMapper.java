package com.haidong.storysharing.dao;

import com.haidong.storysharing.VO.UserInfoVo;
import com.haidong.storysharing.entry.UserAuthorInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAuthorInfoMapper {

    int insertOne(UserAuthorInfo userAuthorInfo);

    int deleteByPrimaryKey(Long id);

    int insert(UserAuthorInfo record);

    /**
     * 这里用户表中的用户名就是用户信息表中的Email
     * @param email
     * @return
     */
    int getPrimaryKeyByEmail(String email);

    /**
     * 根据用户表中的用户信息id进行分页查询
     * @param startIndex
     * @param length
     * @return
     */
    List<UserAuthorInfo> selectAll(@Param("startIndex") Integer startIndex, @Param("length") Integer length);

    /**
     * 统计数据库中的数据量
     * @return
     */
    int countUserInfo();

    /**
     * 根据用户信息 id 更改用户的
     * @param id
     * @return
     */
    int modifyDisable(Integer id);

    int insertSelective(UserAuthorInfo record);

    UserAuthorInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuthorInfo userAuthorInfo);

    int updateByPrimaryKey(UserAuthorInfo record);

    int modifyHeadPicture(String path, String email);

    int updateUserInfo(UserInfoVo userInfoVo);

    /**
     * 禁言
     * @param id
     * @param isDisable
     * @return
     */
    int updateIsDisable(@Param("id") Integer id,@Param("isDisable") Integer isDisable);

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
