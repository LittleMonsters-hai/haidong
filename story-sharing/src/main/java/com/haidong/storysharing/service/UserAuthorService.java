package com.haidong.storysharing.service;

import com.haidong.storysharing.VO.PasswordVO;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.entry.UserAuthor;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/23/2:09
 * @Description:
 */
public interface UserAuthorService {
    int insertOne(UserAuthor userAuthor);

    UserAuthor selectByPrimaryKey(Integer id);

    UserAuthor selectUserAuthorByPwd(String username, String password);

    int countOfUsername(String username);

    UserAuthor selectOneByUsername(String username);

    int updateLoginTime(LocalDateTime lastLoginTime, Integer userId);

    List<UserAuthor> selectAll(Integer startIndex, Integer length);

    /**
     * 获取数据库中用户的数量
     * @return
     */
    int countUser();

    /**
     * 删除用户
     * @return
     */
    int deleteUser(@Param("id") Integer id, @Param("uid") Integer uid);

    /**
     * 更新密码
     * @param passwordVO
     * @return
     */
    int modifyPassword(PasswordVO passwordVO);

    /**
     * 更新用户密码
     * @param username
     * @param id
     * @return
     */
    int modifyUsername(@Param("username") String username, @Param("id") Integer id);


}
