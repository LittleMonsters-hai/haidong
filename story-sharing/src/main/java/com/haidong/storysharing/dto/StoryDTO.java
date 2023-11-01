package com.haidong.storysharing.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/10/11:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryDTO {
    /**
     * 故事id
     */
    private Integer id;

    /**
     * 故事发布者ID
     */
    private Integer userId;

    /**
     * 故事类别id
     */
    private Integer categoryId;

    /**
     * 故事类别名
     */
    private String categoryName;

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
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewsCount;

    /**
     * 故事创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 故事修改时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifyTime;

    /**
     * 标签列表
     */
    private List<TagDTO> tagDTOList;
}
