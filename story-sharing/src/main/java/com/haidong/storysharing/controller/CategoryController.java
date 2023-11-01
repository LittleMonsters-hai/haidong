package com.haidong.storysharing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dto.CategoryDTO;
import com.haidong.storysharing.entry.Category;
import com.haidong.storysharing.entry.Tag;
import com.haidong.storysharing.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/14/10:50
 * @Description:
 */
@RestController
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     *查找分类
     */
    @GetMapping("/categories")
    public void listCategories(HttpServletResponse response) throws IOException {
        List<CategoryDTO> categoryDTOS = categoryService.listCategoryDTO();
        System.out.println(categoryDTOS);
        Result<List<CategoryDTO>> result = new Result<>();
        result.setData(categoryDTOS);

        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    @GetMapping("/getAllCategory")
    public void getAllCategory(HttpServletResponse response) throws Exception{
        List<Category> allCategory = categoryService.getAllCategory();
        Result<List<Category>> result = new Result<>();
        result.setData(allCategory);

        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    @PostMapping("/addCategory")
    public void addTag(@RequestBody Category category){
        System.out.println("##################################" + category);
        categoryService.addCategory(category);
        log.info("增加成功");
    }

    @GetMapping("/admin/deleteCategory/{id}")
    public void deleteTag(@PathVariable Integer id){
        categoryService.deleteOne(id);
        log.info("删除标签成功");
    }
}
