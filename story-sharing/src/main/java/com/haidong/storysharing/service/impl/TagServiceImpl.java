package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.dao.TagMapper;
import com.haidong.storysharing.dto.StoryTagDTO;
import com.haidong.storysharing.entry.Tag;
import com.haidong.storysharing.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/14/12:11
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired(required = false)
    TagMapper tagMapper;
    @Override
    public List<Tag> selectList() {
        return tagMapper.selectList();
    }

    @Override
    public Integer tagCount() {
        return tagMapper.tagCount();
    }

    @Override
    public List<Integer> getTagsId(List<Integer> tagsNameList) {
        return tagMapper.getTagsId(tagsNameList);
    }

    @Override
    public Integer getTagId(String tagName) {
        return tagMapper.getTagId(tagName);
    }

    @Override
    public int insertTagIdByStory(StoryTagDTO storyTagDTO) {
        return tagMapper.insertTagIdByStory(storyTagDTO);
    }

    @Override
    public int insertOne(Tag tag) {
        tag.setCreateTime(LocalDateTime.now());
        tag.setUpdateTime(LocalDateTime.now());
        return tagMapper.insertOne(tag);
    }

    @Override
    public int deleteOne(Integer id) {
        return tagMapper.deleteOne(id);
    }

    @Override
    public int deleteTag(Integer sid) {
        return tagMapper.deleteTag(sid);
    }
}
