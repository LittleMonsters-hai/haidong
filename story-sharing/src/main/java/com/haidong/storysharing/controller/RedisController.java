package com.haidong.storysharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.constant.StoryConst;
import com.haidong.storysharing.dto.StorySearchDTO;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.entry.UserAuthor;
import com.haidong.storysharing.service.StoryService;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/26/11:27
 * @Description:
 */
@RestController
public class RedisController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StoryService storyService;


    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/redisTest")
    public UserAuthor testRedis(){
        List<Story> stories = storyService.selectAllStory();
        //保存所有的故事ID
        List<Integer> list = new ArrayList<>();
        List<Long> storiesId = new ArrayList<>();
        Set set = null;
        Map<Integer,Long> map = new HashMap<>();
        List<Integer> hostStoryID = new ArrayList<>();
        //获取所有故事的ID
        for (int i = 0; i < stories.size(); i++) {
            list.add(stories.get(i).getId());
        }
        //获取所有redis中的浏览量
        for (int i = 0; i < list.size(); i++) {
            String key = StoryConst.STORY_VIEWS_COUNT + list.get(i);
            Long size = redisTemplate.opsForSet().size(key);
            if (size>0){
                map.put(list.get(i),size);
                storiesId.add(size);
            }
        }
        System.out.println(map);

        List<Map.Entry<Integer,Long>> list2 = new ArrayList<Map.Entry<Integer,Long>>(map.entrySet());
        Collections.sort(list2,new Comparator<Map.Entry<Integer,Long>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<Integer,Long> o1,
                               Map.Entry<Integer,Long> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for(Map.Entry<Integer,Long> mapping:list2){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }
        //将已经排号序的故事Id保存五个
        for (int i = list2.size(); i > 0; i--) {
            System.out.println(list2.get(i-1).getKey() + "////" + list2.get(i-1).getValue());
            if (hostStoryID.size()<5){
                hostStoryID.add(list2.get(i-1).getKey());
            }
        }
        List<StorySearchDTO> storySearchDTOS = storyService.hostStoryList(hostStoryID);
        System.out.println(storySearchDTOS.size());
        return null;
    }

    @GetMapping("/test4")
    public void test4(){
        System.out.println("==========");
    }
}
