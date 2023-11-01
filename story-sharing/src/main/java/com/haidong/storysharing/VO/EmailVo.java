package com.haidong.storysharing.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/16/13:22
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVo {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 验证码
     */
    private String code;

    /**
     * 用户名  也就是邮箱
     */
    private String username;
}
