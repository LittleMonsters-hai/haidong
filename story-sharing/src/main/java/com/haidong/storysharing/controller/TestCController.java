package com.haidong.storysharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/24/21:55
 * @Description:
 */
@Controller
public class TestCController {
    @GetMapping("/login")
    public String login(){
        return "redirect:/login.xml";
    }
}
