package com.haidong.storysharing.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/16/10:31
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordVO {
    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 用户ID
     */

    private Integer Id;
}
