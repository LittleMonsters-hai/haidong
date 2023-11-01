package com.haidong.storysharing.dto;

import com.haidong.storysharing.entry.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/14/16:35
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryPublishDTO {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 故事id
     */
    private Integer id;

    /**
     * 故事作者
     */
    private Integer userId;

    /**
     * 故事类别
     */
    private Integer categoryId;

    /**
     * 故事插图
     */
    private String storyIllustration;

    /**
     * 故事标题
     */
    private String storyTitle;

    /**
     * 故事内容
     */
    private String storyContent;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 故事当前 状态 公开 私密 评论可见
     */
    private Integer status;

    /**
     * 故事链接
     */
    private String originalUrl;

    /**
     * 故事创建时间
     */
    private LocalDateTime createTime;


    /**
     * 故事修改时间
     */
    private LocalDateTime modifyTime;

    private List<String> tagNameList;
}
