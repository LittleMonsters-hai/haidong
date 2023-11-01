package com.haidong.storysharing.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/08/22:48
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionVO {
    /**
     * 从第几个开始查询
     */
    private Integer current;

    private Integer tagId;

    private Integer categoryId;

    private Integer length;

    /**
     * 搜索关键字
     */
    private String keywords;
}
