package com.haidong.storysharing.dao;

import com.haidong.storysharing.VO.PasswordVO;
import com.haidong.storysharing.entry.UserAuthor;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAuthorMapper {

    int insertOne(UserAuthor userAuthor);

    UserAuthor selectUserAuthorByPwd(String username, String password);

    int countOfUsername(String username);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    UserAuthor selectOneByUsername(String username);

    int deleteByPrimaryKey(Long id);

    int insert(UserAuthor record);

    UserAuthor selectByPrimaryKey(Integer id);

    /**
     * 更新用户最后修改时间
     * @param lastLoginTime
     * @param userId
     * @return
     */
    int updateLoginTime(@Param("lastLoginTime") LocalDateTime lastLoginTime, @Param("userId") Integer userId);

    /**
     * 查询所用用户表信息
     * @param startIndex
     * @param length
     * @return
     */
    List<UserAuthor> selectAll( @Param("startIndex") Integer startIndex, @Param("length") Integer length);

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
