package com.haidong.storysharing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/17/15:55
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorySearchDTO {

    /**
     * 故事id
     */
    @Id
    private Integer id;

    /**
     * 故事标题
     */
    private String storyTitle;

    /**
     * 故事内容
     */
    private String storyContent;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 文章状态
     */
    private Integer status;
}
