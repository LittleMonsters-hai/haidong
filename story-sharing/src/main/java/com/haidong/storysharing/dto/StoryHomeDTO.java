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
 * @Date: 2023/04/03/20:38
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryHomeDTO {
    /**
     * 故事id
     */
    private Integer id;

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
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 故事创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 标签列表
     */
    private List<TagDTO> tagDTOList;
}
