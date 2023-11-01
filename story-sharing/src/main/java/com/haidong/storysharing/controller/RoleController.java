package com.haidong.storysharing.controller;

import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.entry.Role;
import com.haidong.storysharing.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/12/2:36
 * @Description:
 */
@RestController()
@RequestMapping("/testRole")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("/test")
    public void test(){
        UserAuthorDTO userAuthorDTO = (UserAuthorDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(roleService.getUserRolesByUid(userAuthorDTO.getId()));
    }
}
