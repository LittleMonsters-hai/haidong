package com.haidong.storysharing.dao;

import com.haidong.storysharing.dto.CategoryDTO;
import com.haidong.storysharing.entry.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    /**
     * 查询分类和对应文章数量
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTO();

    /**
     * 根据分类名查询ID
     * @return
     */
    Integer getIdByName(@Param("name") String name);

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
