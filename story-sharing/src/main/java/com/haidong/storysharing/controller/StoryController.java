package com.haidong.storysharing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.VO.ConditionVO;
import com.haidong.storysharing.constant.StoryConst;
import com.haidong.storysharing.dto.*;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.entry.Page;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.entry.StoryInfo;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.*;
import com.haidong.storysharing.utils.BeanCopyUtils;
import com.haidong.storysharing.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/10/0:03
 * @Description:
 */
@RestController
@Slf4j
public class StoryController {
    @Autowired
    StoryService storyService;

    @Autowired
    StoryInfoService storyInfoService;

    @Autowired
    private UserAuthorInfoService userAuthorInfoService;

    @Autowired
    private TagService tagService;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    /**
     * 首页故事展示信息
     * @param startIndex
     * @param response
     * @param request
     * @param length
     * @throws InterruptedException
     * @throws IOException
     */
    @GetMapping("/stories/{startIndex}/{length}")
    public void listStories(@PathVariable Integer startIndex,
                                                  HttpServletResponse response,
                                                  HttpServletRequest request,
                                                  @PathVariable Integer length) throws InterruptedException, IOException {
        Result<List<StoryHomeDTO>> result = new Result<>();
        List<StoryHomeDTO> listStories = storyService.listStories((startIndex - 1) * 2, length);
        result.setData(listStories);
        Thread.sleep(800);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().print(s);
        log.warn("startIndex----------------->" + startIndex);
        log.info("用户验证成功，返回数据为:{}" , s);
    }

    /**
     * 查询当前用户发布的故事
     * @param startIndex
     * @param response
     * @param request
     * @param length
     * @throws InterruptedException
     * @throws IOException
     */
    @GetMapping("/stories/user/{startIndex}/{length}")
    public void listStoriesByUser(@PathVariable Integer startIndex,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            @PathVariable Integer length) throws InterruptedException, IOException {
        Result<List<StoryHomeDTO>> result = new Result<>();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(startIndex + "===================>");
        List<StoryHomeDTO> listStories = storyService.listStoriesByUser((startIndex - 1) * 2, length, user.getUserInfoId());
        result.setData(listStories);
        Thread.sleep(800);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().print(s);
        log.warn("查询用户发布故事信息" + listStories);
    }


    /**
     * 按条件进行查询 向用户展示故事简洁的信息
     * @return
     */
    @GetMapping("/stories/condition")
    public void listStoriesByCondition(
            ConditionVO condition,
            HttpServletResponse response
            ) throws IOException {
        List<StoryConciseDTO> storyConciseDTOS = storyService.listStoriesByCondition((condition.getCurrent()-1)*2, condition.getLength(), condition);
        Result<List<StoryConciseDTO>> result = new Result<>();
        result.setData(storyConciseDTOS);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
        log.info("查询的标签数据，返回数据为:{}" , s);
    }

    /**
     * 根据id查询故事信息
     * @param storyId
     * @param response
     * @throws Exception
     */
    @GetMapping("/stories/{storyId}")
    public void getStoryById(
            @PathVariable Integer storyId,
            HttpServletResponse response) throws Exception{
        System.out.println("查询的故事ID：" + storyId);
        StoryDTO story = storyService.getStoryById(storyId);
        System.out.println("查询的故事" + story);
        System.out.println("查询出来的故事信息");
        UserAuthorInfo userAuthorInfo = userAuthorInfoService.selectByPrimaryKey(story.getUserId());
        Result<StoryDTO> result = new Result<>();
        Map<String, UserAuthorInfo> map = new HashMap<>();
        map.put("userAuthorInfo", userAuthorInfo);
        result.setData(story);
        result.setMap(map);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
        log.info("查询的标签数据，返回数据为:{}" , s);
    }

    /**
     * 根据id查询故事信息
     * @param storyId
     * @param response
     * @throws Exception
     */
    @GetMapping("/storiesEdit/{storyId}")
    public void getStoryByIdEdit(
            @PathVariable Integer storyId,
            HttpServletResponse response) throws Exception{
        System.out.println("查询的故事ID：" + storyId);
        StoryDTO story = storyService.getStoryByIdEdit(storyId);
        System.out.println("查询的故事" + story);
        System.out.println("查询出来的故事信息");
        UserAuthorInfo userAuthorInfo = userAuthorInfoService.selectByPrimaryKey(story.getUserId());
        Result<StoryDTO> result = new Result<>();
        Map<String, UserAuthorInfo> map = new HashMap<>();
        map.put("userAuthorInfo", userAuthorInfo);
        result.setData(story);
        result.setMap(map);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
        log.info("查询的标签数据，返回数据为:{}" , s);
    }


    @GetMapping("/stories")
    public Result<List<StoryHomeDTO>> listStories2(){
        Result<List<StoryHomeDTO>> result = new Result<>();

        Integer limitCurrent = PageUtils.getLimitCurrent();
        System.out.println(limitCurrent);
        System.out.println(PageUtils.getCurrent());
        Integer current = PageUtils.getCurrent();

        List<StoryHomeDTO> listStories = storyService.listStories(limitCurrent, PageUtils.getLength());

        if (Objects.isNull(listStories)){
            return result;
        }
        current++;
        PageUtils.getPage().setCurrent(current);
        System.out.println(PageUtils.getCurrent());

        result.setData(listStories);
        return result;
    }

    /**
     * 获取用户发布故事的总数
     * @param userId
     */
    @GetMapping("/getStoryCount")
    public Integer getStoryCount(Integer userId){
        System.out.println(userId);
        int storyCount = storyService.getStoryCount(userId);
        Result<Integer> result = new Result<>();
        result.setData(storyCount);
        System.out.println(storyCount);
        return storyCount;
    }

    @GetMapping("/story")
    public Result<List<Story>> getStory(){
        Result<List<Story>> result = new Result<>();
        result.setData(storyService.selectAllStory());
        if (result.getData() != null){
            log.info("查询所有Story数据:{}",result.getData());
            result.setMessage("查询成功");
        }else {
            log.info("查询失败!");
            result.setMessage("查询失败！");
        }
        return result;
    }

    @GetMapping("/getStoryOne/{id}")
    public Story getOne(@PathVariable int id){
        Story story = storyService.selectOneById(id);
        return story;
    }

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    @PostMapping("/saveStory")
    public void saveStory(
            @RequestBody StoryPublishDTO story,
            HttpServletResponse response
    ){
        storyService.saveStory(story);
        System.out.println(story);
        //获取刚刚保存故事的ID 进行标签的插入
        int storyId = storyService.getStoryIdByUserId(story.getUserId());
        List<String> tagNameList = story.getTagNameList();
        for (int i = 0; i < tagNameList.size(); i++) {
            Integer tagId = tagService.getTagId(tagNameList.get(i));
            StoryTagDTO storyTagDTO = new StoryTagDTO();
            storyTagDTO.setStoryId(storyId);
            storyTagDTO.setTagId(tagId);
            tagService.insertTagIdByStory(storyTagDTO);
        }
        //获取标签Id
//        tagNameList=[儿童, 趣事, 其他]
    }

    /**
     * 用户更改自己故事信息
     * @param story
     * @param response
     */
    @PostMapping("/editStory")
    public void editStory(
            @RequestBody StoryPublishDTO story,
            HttpServletResponse response
    ){
        storyService.editStory(story);
        //获取刚刚保存故事的ID 进行标签的插入
        int storyId = story.getId();
        List<String> tagNameList = story.getTagNameList();
        System.out.println(story);
        int i1 = tagService.deleteTag(storyId);
        System.out.println("删除标签" + i1);
        if (!ObjectUtils.isEmpty(tagNameList)){
            for (int i = 0; i < tagNameList.size(); i++) {
                Integer tagId = tagService.getTagId(tagNameList.get(i));
                StoryTagDTO storyTagDTO = new StoryTagDTO();
                storyTagDTO.setStoryId(storyId);
                storyTagDTO.setTagId(tagId);
                tagService.insertTagIdByStory(storyTagDTO);
            }
        }
        System.out.println("============>" + story);
    }

    @GetMapping("/getHistory/{startIndex}/{length}")
    public void getHistoryStoriesList(
            @PathVariable Integer startIndex,
            HttpServletResponse response,
            HttpServletRequest request,
            @PathVariable Integer length
    )throws Exception{
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(startIndex);
        System.out.println(user.getUserInfoId());
        Result<List<StoryHistoryDTO>> result = new Result<>();
        List<StoryHistoryDTO> story = storyService.historyListStoriesByUser((startIndex - 1) * 2, length, user.getUserInfoId());
        result.setData(story);
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    /**
     * 删除故事
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/delete/story/{id}")
    public void deleteStoryById(@PathVariable Integer id){
//        String key = StoryConst.STORY_VIEWS_COUNT + id;
        storyService.deleteStory(id);
//        redisTemplate.opsForSet().remove(key);
        log.error("删除用户故事成功！！！");
        System.out.println(id);
    }

    @GetMapping("/story/search")
    public Result<List<StorySearchDTO>> listArticlesBySearch(ConditionVO condition){
        if (condition.getKeywords().equals("") || condition.getKeywords() == null){
            return null;
        }
        System.out.println(condition);
        Result<List<StorySearchDTO>> result = new Result<>();
        List<StorySearchDTO> storySearchDTOS = storyService.listArticlesBySearch(condition.getKeywords());
        System.out.println(storySearchDTOS);
        result.setData(storySearchDTOS);
        return result;
    }

    /**
     * 故事点赞
     * @param id
     * @return
     */
    @GetMapping("/story/{id}/like")
    public Result<String> saveStoryLike(@PathVariable Integer id){
        Result<String> result = new Result<>();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String key = StoryConst.STORY_LIKE_COUNT + id;
        Long object = redisTemplate.opsForSet().add(key, user.getUserInfoId());
        if (object == 1){
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setMessage("点赞成功");
        }else {
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage("你已重复点赞");
        }
        log.info("查询结果：{}",object);
        return result;
    }

    /**
     * 故事浏览
     * @param id
     * @return
     */
    @GetMapping("/story/{id}/view")
    public Result<String> saveStoryView(@PathVariable Integer id){
        Result<String> result = new Result<>();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String key = StoryConst.STORY_VIEWS_COUNT + id;
        Long object = redisTemplate.opsForSet().add(key, user.getUserInfoId());
        if (object == 1){
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setMessage("故事浏览加一");
        }else {
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage("该用户已经浏览过该故事");
        }
        log.info("查询结果：{}",object);
        return result;
    }

    /**
     * 取消故事点赞
     * @param id
     * @return
     */
    @GetMapping("/story/{id}/unlike")
    public Result<String> saveStoryUnlike(@PathVariable Integer id){
        Result<String> result = new Result<>();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String key = StoryConst.STORY_LIKE_COUNT + id;
        System.out.println(key);
        Long object = redisTemplate.opsForSet().remove(key, user.getUserInfoId());
        if (object == 1){
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setMessage("取消成功");
        }else {
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage("你已重复取消点赞");
        }
        log.info("查询结果：{}",object);
        return result;
    }

    /**
     * 根据 故事ID 查看故事点赞总数和是否点赞
     * @param id
     * @return
     */
    @GetMapping("/getStoryLikes")
    public Result<Object> getStoryLikes(Integer id){
        Map map = new HashMap();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Object> result = new Result<>();
        String key = StoryConst.STORY_LIKE_COUNT + id;
        Long size = redisTemplate.opsForSet().size(key);
        boolean bo = redisTemplate.opsForSet().isMember(key, user.getUserInfoId());
        map.put("size", size);
        map.put("isLike",bo);
        result.setMap(map);
        return result;
    }

    /**
     * 查看点赞明细
     * @param id
     * @return
     */
    @GetMapping("/likeDetail")
    public Result<Object> likeDetail(Integer id){
        Result<Object> result = new Result<>();
        Set set = null;
        String key = StoryConst.STORY_LIKE_COUNT + id;
        set = redisTemplate.opsForSet().members(key);
        log.info("查询结果:{}",set);
        result.setData(set);
        return result;
    }

    /**
     * 根据 故事ID 查看故事浏览量
     * @param id
     * @return
     */
    @GetMapping("/getViewLikes")
    public Result<Object> getViewLikes(Integer id){
        Map map = new HashMap();
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Object> result = new Result<>();
        String key = StoryConst.STORY_VIEWS_COUNT + id;
        Long view = redisTemplate.opsForSet().size(key);
        boolean bo = redisTemplate.opsForSet().isMember(key, user.getUserInfoId());
        map.put("size", view);
        map.put("isLike",bo);
        result.setMap(map);
        return result;
    }

    @GetMapping("/hostStoryList")
    public Result<List<StorySearchDTO>> hostStoryList() {
        Result<List<StorySearchDTO>> result = new Result<>();
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
        //根据浏览量查询热门故事
        List<StorySearchDTO> storySearchDTOS = storyService.hostStoryList(hostStoryID);
        List<StorySearchDTO> resultHostStory = new ArrayList<>();
        for (int i = 0; i < hostStoryID.size(); i++) {
            for (int i1 = 0; i1 < storySearchDTOS.size(); i1++) {
                if (hostStoryID.get(i).equals(storySearchDTOS.get(i1).getId())){
                    resultHostStory.add(storySearchDTOS.get(i1));
                }
            }
        }
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        result.setData(resultHostStory);
        log.info("查询热门故事");
        return result;
    }
}
