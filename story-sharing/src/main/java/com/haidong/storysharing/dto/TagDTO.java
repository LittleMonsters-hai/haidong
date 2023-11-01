package com.haidong.storysharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/03/20:51
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签总数量
     */

    private Integer storyCount;
}
