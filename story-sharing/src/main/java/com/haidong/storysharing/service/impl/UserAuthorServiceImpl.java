package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.VO.PasswordVO;
import com.haidong.storysharing.dao.UserAuthorInfoMapper;
import com.haidong.storysharing.dao.UserAuthorMapper;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.entry.UserAuthor;
import com.haidong.storysharing.service.UserAuthorInfoService;
import com.haidong.storysharing.service.UserAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/23/2:09
 * @Description:
 */
@Service
public class UserAuthorServiceImpl implements UserAuthorService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired(required = false)
    UserAuthorMapper userAuthorMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    UserAuthorInfoMapper userAuthorInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public void test(){
    }

    /**
     * 新增用户
     * @param userAuthor
     * @return
     */
    @Override
    public int insertOne(UserAuthor userAuthor) {
        //用户注册时间
        userAuthor.setRegisterTime(LocalDateTime.now());
        //用户更新时间
        userAuthor.setUpdateTime(LocalDateTime.now());
        //用户密码加密
        userAuthor.setPassword(passwordEncoder.encode(userAuthor.getPassword()));
        int count = userAuthorMapper.insertOne(userAuthor);
        return count;
    }

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    @Override
    public UserAuthor selectByPrimaryKey(Integer id) {
        UserAuthor userAuthor = userAuthorMapper.selectByPrimaryKey(id);
        redisTemplate.opsForValue().set("user",userAuthor);
        return userAuthor;
    }

    @Override
    public UserAuthor selectUserAuthorByPwd(String username, String password) {
        UserAuthor userAuthor = userAuthorMapper.selectUserAuthorByPwd(username, password);
        return userAuthor;
    }

    @Override
    public int countOfUsername(String username) {
        int count = userAuthorMapper.countOfUsername(username);
        return count;
    }

    @Override
    public UserAuthor selectOneByUsername(String username) {
        UserAuthor userAuthor = userAuthorMapper.selectOneByUsername(username);
        return userAuthor;
    }

    @Override
    public int updateLoginTime(LocalDateTime lastLoginTime, Integer userId) {
        return userAuthorMapper.updateLoginTime(lastLoginTime, userId);
    }

    @Override
    public List<UserAuthor> selectAll(Integer startIndex, Integer length) {
        return userAuthorMapper.selectAll(startIndex, length);
    }

    @Override
    public int countUser() {
        return userAuthorMapper.countUser();
    }

    @Override
    public int deleteUser(Integer id, Integer uid) {
        return userAuthorMapper.deleteUser(id,uid);
    }

    /**
     * 更改用户密码
     * @param passwordVO
     * @return
     */
    @Override
    public int modifyPassword(PasswordVO passwordVO) {
        passwordVO.setNewPassword(passwordEncoder.encode(passwordVO.getNewPassword()));
        System.out.println(passwordVO);
        return userAuthorMapper.modifyPassword(passwordVO);
    }

    @Override
    public int modifyUsername(String username, Integer id) {
        return userAuthorMapper.modifyUsername(username, id);
    }
}
