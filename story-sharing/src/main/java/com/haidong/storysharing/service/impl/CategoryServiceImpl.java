package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.dao.CategoryMapper;
import com.haidong.storysharing.dto.CategoryDTO;
import com.haidong.storysharing.entry.Category;
import com.haidong.storysharing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/14/10:51
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> listCategoryDTO() {
        return categoryMapper.listCategoryDTO();
    }

    @Override
    public Integer getIdByName(String name) {
        return categoryMapper.getIdByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public int addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.addCategory(category);
    }

    @Override
    public int deleteOne(Integer id) {
        return categoryMapper.deleteOne(id);
    }
}
