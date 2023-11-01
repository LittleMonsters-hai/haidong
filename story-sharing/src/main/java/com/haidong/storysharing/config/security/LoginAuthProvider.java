package com.haidong.storysharing.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/09/15:07
 * @Description:
 */
@Component
public class LoginAuthProvider implements AuthenticationProvider {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户名和密码
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetail = userDetailsService.loadUserByUsername(username);
        if (!userDetail.isEnabled()){
            throw new DisabledException("该账号已禁用，请联系管理员");
        }else if (!userDetail.isAccountNonExpired()){
            throw new AccountExpiredException("该账号已过期,请联系管理员");
        }else if(!userDetail.isAccountNonLocked()){
            throw new LockedException("该账号已被锁定，请联系管理员");
        }else if(!userDetail.isCredentialsNonExpired()){
            throw new CredentialsExpiredException("该账号的登录凭证已过期，请重新登录");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(password,userDetail.getPassword())){
            throw  new BadCredentialsException("密码错误请重新输入");
        }
        return new UsernamePasswordAuthenticationToken(userDetail,password,userDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
