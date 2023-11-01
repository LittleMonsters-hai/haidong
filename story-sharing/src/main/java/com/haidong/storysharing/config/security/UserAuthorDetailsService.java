package com.haidong.storysharing.config.security;

import com.haidong.storysharing.dto.UserAuthorDTO;
import com.haidong.storysharing.entry.Role;
import com.haidong.storysharing.entry.UserAuthor;
import com.haidong.storysharing.service.RoleService;
import com.haidong.storysharing.service.UserAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/24/0:39
 * @Description:
 */
@Service("userDetailsService")
public class UserAuthorDetailsService implements UserDetailsService {
    @Autowired
    private UserAuthorService userAuthorService;

    @Autowired
    private RoleService roleService;

    /*@Autowired
    RedisTemplate redisTemplate;*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthor userAuthor = userAuthorService.selectOneByUsername(username);
        //当数据库中不存在用户输入的用户名时
        if (userAuthor == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return new UserAuthorDTO(userAuthor.getId(),userAuthor.getUserInfoId(),userAuthor.getUsername(),
                userAuthor.getPassword(),userAuthor.getLoginType(),roleService.getUserRolesByUid(userAuthor.getId()),userAuthor.getIpAddress(),
                userAuthor.getRegisterTime(),userAuthor.getUpdateTime(),userAuthor.getLastLoginTime());
    }
}
