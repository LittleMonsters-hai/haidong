package com.haidong.storysharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.dao.TagMapper;
import com.haidong.storysharing.dto.TagDTO;
import com.haidong.storysharing.entry.Tag;
import com.haidong.storysharing.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/14/12:13
 * @Description:
 */
@RestController
@Slf4j
public class TagController {
    @Autowired
    TagService tagService;

    /**
     * 查询标签列表
     *
     * @return  标签列表
     */
    @GetMapping("/tags")
    public void listTags(HttpServletResponse response) throws Exception{
        List<Tag> tags = tagService.selectList();
        System.out.println(tags);
        Integer count = tagService.tagCount();
        System.out.println(count);

        Result<List<Tag>> result = new Result<>();
        result.setData(tags);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    @PostMapping("/addTag")
    public void addTag(@RequestBody Tag tag){
        System.out.println("##################################" + tag);
        tagService.insertOne(tag);
        log.info("增加成功");
    }

    @GetMapping("/admin/deleteTag/{id}")
    public void deleteTag(@PathVariable Integer id){
        tagService.deleteOne(id);
        log.info("删除标签成功");
    }
}
