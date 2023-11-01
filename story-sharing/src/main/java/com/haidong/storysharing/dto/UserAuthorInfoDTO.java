package com.haidong.storysharing.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.haidong.storysharing.entry.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/11/8:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthorInfoDTO {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户信息ID
     */
    private Integer userInfoId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headPicture;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 用户网址
     */
    private String webSite;

    /**
     * 登录方式
     */
    private Integer loginType;

    /**
     * 用户的角色
     */
    private List<Role> roles;

    /**
     * 用户登录 ip地址
     */
    private String ipAddress;

    /**
     *用户注册时间
     * */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registerTime;

    /**
     * 最后更新时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /***
     * 最后登录时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;
}
