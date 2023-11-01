package com.haidong.storysharing.service;

import com.haidong.storysharing.dto.CategoryDTO;
import com.haidong.storysharing.entry.Category;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/14/10:51
 * @Description:
 */
public interface CategoryService {
    /**
     * 查询分类和对应文章数量
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTO();

    /**
     * 根据分类名查询ID
     * @param name
     * @return
     */
    Integer getIdByName(String name);

    /**
     * 获取所有的分类
     * @return
     */
    List<Category> getAllCategory();

    /**
     * 添加分类
     * @return
     */
    int addCategory(Category category);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteOne(Integer id);
}
