package com.haidong.storysharing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/13/18:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthorAdminDTO {
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
     * 用户登录 ip地址
     */
    private String ipAddress;

    /**
     *用户注册时间
     * */
    private LocalDateTime registerTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

    /***
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    private String nickname;

    private String headPicture;

    private String introduce;

    private String webSite;

    private Integer isDisable;

    private LocalDateTime createTime;

    private LocalDateTime infoUpdateTime;
}
