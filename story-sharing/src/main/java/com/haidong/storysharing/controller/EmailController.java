package com.haidong.storysharing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.common.Result;
import com.haidong.storysharing.entry.UserAuthorInfo;
import com.haidong.storysharing.enums.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/12/10:47
 * @Description:
 */
@RestController
@Slf4j
public class EmailController {
    @Resource
    private JavaMailSender javaMailSender;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    //读取yml文件中username的值并赋值给form
    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("/sendEmail/{username}")
    public void sendSimpleMail(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(username);
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件发送者
        message.setFrom(from);
        // 设置邮件接收者
        message.setTo(username);
        // 设置邮件的主题
        message.setSubject("登录验证码");
        // 设置邮件的正文
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(10);
            code.append(r);
        }
        redisTemplate.opsForValue().set("code",code.toString());
        //将验证码保存到session中
        request.getServletContext().setAttribute("code",code.toString());
        log.info("发送验证码成功:{}",code);
        String text = "您的验证码为：" + code + ",请勿泄露给他人。\r此邮件由系统发出，请勿直接回复。";
        message.setText(text);
        message.setSentDate(new Date());
        // 发送邮件
        try {
            javaMailSender.send(message);
            log.info("验证码发送成功");
        } catch (MailException e) {
            e.printStackTrace();
            Result<UserAuthorInfo> result = new Result<>();
            Map<String, String> map = new HashMap<>();
            result.setCode(StatusCodeEnum.FAIL.getCode());
            result.setMessage("验证码发送失败！请检查邮箱是否存在");
            result.setMap(map);
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().print(s);
            log.error("验证码发送失败！！！");
        }
    }
}
