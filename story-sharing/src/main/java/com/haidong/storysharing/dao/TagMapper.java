package com.haidong.storysharing.dao;

import com.haidong.storysharing.dto.StoryTagDTO;
import com.haidong.storysharing.dto.TagDTO;
import com.haidong.storysharing.entry.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    List<Tag> selectList();

    /**
     * 统计 标签数
     * @return
     */
    int tagCount();

    /**
     * 通过标签名查询 标签ID
     * @param tagsNameList
     * @return
     */
    List<Integer> getTagsId(@Param("tagsNameList") List<Integer> tagsNameList);

    /**
     * 获取ID
     * @return
     */
    Integer getTagId(@Param("tagName") String tagName);

    /**
     * 批量插入
     */
    int batchInsert();

    /**
     * 增加 tag 关联
     * @param storyTagDTO
     * @return
     */
    int insertTagIdByStory(StoryTagDTO storyTagDTO);

    /**
     * 新增标签
     * @param tag
     * @return
     */
    int insertOne(Tag tag);

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    int deleteOne(Integer id);

    /**
     * 根据故事id 删除标签
     * @param sid
     * @return
     */
    int deleteTag(Integer sid);
}
