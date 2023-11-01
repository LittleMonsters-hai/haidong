package com.haidong.storysharing.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/12/9:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private String nickname;

    private String introduce;

    private String webSite;

    private Integer isDisable;

    private Integer id;

    private Integer email;
}
