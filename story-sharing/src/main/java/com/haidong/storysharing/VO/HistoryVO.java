package com.haidong.storysharing.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/16/22:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryVO {
    private Integer userId;

    private Integer storyId;
}
