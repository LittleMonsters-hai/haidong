package com.haidong.storysharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.VO.EmailVo;
import com.haidong.storysharing.VO.PasswordVO;
import com.haidong.storysharing.VO.UserAuthorVO;
import com.haidong.storysharing.VO.UserInfoVo;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.common.UserConstant;
import com.haidong.storysharing.dao.UserAuthorInfoMapper;
import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.entry.Story;
import com.haidong.storysharing.entry.UserAuthor;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import com.haidong.storysharing.service.*;
import com.haidong.storysharing.utils.BeanCopyUtils;
import com.haidong.storysharing.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/23/2:13
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserAuthorController {
    @Autowired
    UserAuthorService userAuthorService;

    @Autowired
    UserAuthorInfoService userAuthorInfoService;

    @Autowired
    CommentService  commentService;

    @Autowired
    RoleService roleService;

    @Autowired
    StoryService storyService;

    @Autowired
    StringRedisTemplate redisTemplate;



    /**
     * 用户注册
     * @param userAuthor
     * @return
     */
    @PostMapping("/insertOne")
    public Result<Integer> insertOne(@RequestBody UserAuthor userAuthor){
        Result<Integer> result = new Result<>();
        //当前注册用户的用户名在数据库中不存在
        int count = userAuthorService.countOfUsername(userAuthor.getUsername());
        if (count == 0){
            int count2 = userAuthorService.insertOne(userAuthor);
            result.setMessage(StatusCodeEnum.SUCCESS.getDesc());
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setData(count2);
            log.info("用户注册成功！");
        }else {
            result.setMessage(StatusCodeEnum.FAIL.getDesc());
            result.setCode(StatusCodeEnum.FAIL.getCode());
            log.info("用户注册失败！");
        }
        return result;
    }

    /**
     * 用户注册
     * @param userAuthorVO
     */
    @PostMapping("/register")
    public void register(UserAuthorVO userAuthorVO){
        Result<Integer> result = new Result<>();
        //当前注册用户的用户名在数据库中不存在
        int count = userAuthorService.countOfUsername(userAuthorVO.getUsername());
        UserAuthorInfo userAuthorInfo = new UserAuthorInfo();
        userAuthorInfo.setEmail(userAuthorVO.getUsername());
        userAuthorInfo.setNickname(UserConstant.DEFAULT_NICKNAME);
        if (count == 0){
            int count2 = userAuthorService.insertOne(null);
            result.setMessage(StatusCodeEnum.SUCCESS.getDesc());
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setData(count2);
            log.info("用户注册成功！");
        }else {
            result.setMessage(StatusCodeEnum.FAIL.getDesc());
            result.setCode(StatusCodeEnum.FAIL.getCode());
            log.info("用户注册失败！");
        }
        log.info("邮箱已存在！{}" ,count);
    }

    /**
     * 更改用户头像
     * @param path
     */
    @GetMapping("/modifyHeadPicture")
    public void modifyHeadPicture(String path){
        System.out.println(path);
        UserAuthorDTO userAuthorDTO = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAuthorInfoService.modifyHeadPicture(path, userAuthorDTO.getUsername());
    }

    @PostMapping("/modifyPassword")
    public Result<Object> modifyPassword(
            @RequestBody PasswordVO passwordVO,
            HttpServletResponse response
            ){

        System.out.println(passwordVO);
        //获取解密对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Result<Object> result = new Result<>();
        //获取用户登录后信息
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //当用户输入的旧密码不正确
        if(!passwordEncoder.matches(passwordVO.getOldPassword(),user.getPassword())){
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setData("旧密码不正确！");
            System.out.println("旧密码不正确！");
        }else {
            passwordVO.setId(user.getId());
            int count = userAuthorService.modifyPassword(passwordVO);
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            result.setData("更新成功！");
            System.out.println("更新成功！");
            log.info("更改密码成功！");
        }
        return result;
    }

    /**
     * 更改用户基本信息
     * @param
     */
    @PostMapping("/updateInfo")
    public void updateUserInfo(@RequestBody UserInfoVo userInfoVo, HttpServletResponse response) throws Exception{
        UserAuthorInfo userAuthorInfo = BeanCopyUtils.copyObject(userInfoVo, UserAuthorInfo.class);
        UserAuthorDTO userAuthorDTO = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAuthorInfo.setId(userAuthorDTO.getUserInfoId());
        log.info(userAuthorDTO.toString());
        //设置更新时间
        userAuthorInfo.setUpdateTime(LocalDateTime.now());
        userAuthorInfoService.updateByPrimaryKeySelective(userAuthorInfo);
        UserAuthorInfo authorInfo = userAuthorInfoService.selectByPrimaryKey(userAuthorDTO.getUserInfoId());
        Result<UserAuthorInfo> result = new Result<>();
        result.setData(authorInfo);
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        log.warn("查询到的数据为:{}", s);
        response.getWriter().print(s);
    }

    /**
     * 更改邮箱
     * @param emailVo
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping("/updateEmail")
    @Transactional(rollbackFor = {Exception.class})
    public void register(
            @Valid @RequestBody EmailVo emailVo,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        System.out.println(emailVo);
        Result<Integer> result = new Result<>();
        String userCode = emailVo.getCode();
        String code = (String) request.getServletContext().getAttribute("code");
        System.out.println("userCode=" + emailVo.getCode());
        System.out.println("SystemCode=" + code);
        if ((!emailVo.getCode().equals(code)) || (userCode==null) || (userCode.equals(""))){
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage("验证码不正确");
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            return;
        }
        //当前注册用户的用户名在数据库中不存在
        int count = userAuthorService.countOfUsername(emailVo.getUsername());
        if (count == 0 && emailVo.getCode().equals(code)){
            UserAuthorInfo userAuthorInfo = new UserAuthorInfo();
            //更改用户信息中的  邮箱信息
            userAuthorInfo.setEmail(emailVo.getUsername());
            //设置 id 属性
            userAuthorInfo.setId(emailVo.getId());
            int count2 = userAuthorInfoService.updateByPrimaryKeySelective(userAuthorInfo);
            int count3 = userAuthorService.modifyUsername(emailVo.getUsername(), emailVo.getId());
            result.setMessage("邮箱更改成功！");
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            log.info("用户邮箱更改成功！");
            //删除验证码
            request.getServletContext().removeAttribute("code");
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
        }else {
            result.setMessage("账户已存在");
            result.setCode(StatusCodeEnum.FAIL.getCode());
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            log.info("邮箱更改失败！");
        }
    }

    /**
     * 用户注销
     * @param response
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/deleteUser")
    public void deleteUser(
            HttpServletResponse response
    )throws Exception{
        UserAuthorDTO user = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userInfoId = user.getUserInfoId();
        Integer userId = user.getId();
        Result<Object> result = new Result<>();
        System.out.println(userId + "-------------" + userInfoId);
        int i = userAuthorService.deleteUser(userId, userInfoId);
        int i1 = userAuthorInfoService.deleteUserInfo(userInfoId);
        int i2 = commentService.deleteCommentsByUserId(userInfoId);
        int i3 = roleService.removeOneByUserId(userId);
        int i4 = storyService.deleteStoryListByUserId(userInfoId);
        if (i > 0 && i1 > 0){
            result.setMessage("注销成功");
            result.setCode(StatusCodeEnum.SUCCESS.getCode());
            log.error("注销成功");
        }else {
            result.setMessage("注销失败");
            result.setCode(StatusCodeEnum.FAIL.getCode());
            log.error("注销失败");
        }

        response.setContentType("application/json;charset=utf-8");
        String s = new ObjectMapper().writeValueAsString(result);
        response.getWriter().print(s);
    }

    @GetMapping("/userTest")
    public void userTest(HttpServletRequest request){
        log.info("获取用户ip地址");
        String ipAddress = null;
        log.info("x-forwarded-for的值为{}", request.getHeader("x-forwarded-for"));
        log.info("RemoteAddr:{}", request.getRemoteAddr());
        log.info("User-Agent:{}",request.getHeader("User-Agent"));
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        log.info(ipAddress);
        String ipSource = IpUtils.getIpSource(ipAddress);
        log.info(ipSource);
    }

    @PostMapping("/fileTest")
    public void saveFile(MultipartFile file){}
}
