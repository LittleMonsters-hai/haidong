package com.haidong.storysharing.config.security.metadatasource;

import com.haidong.storysharing.entry.Path;
import com.haidong.storysharing.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/11/22:44
 * @Description:
 */
@Component
@Slf4j
public class CustomerSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    PathService pathService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        List<Path> allPath = pathService.getAllPath();
        for (Path path : allPath){
            log.info("path ====>" + path);
            log.info("allPath====>" + requestURI);
            log.info("比对是否正确" + antPathMatcher.match(path.getPath(),requestURI));
            if (antPathMatcher.match(path.getPath(),requestURI)){
                String[] roles = path.getRoles().stream().map(role -> role.getRoleName()).toArray(String[]::new);
                for (int i = 0; i < roles.length; i++) {
                    log.info(roles[i]);
                }
                return SecurityConfig.createList(roles);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
