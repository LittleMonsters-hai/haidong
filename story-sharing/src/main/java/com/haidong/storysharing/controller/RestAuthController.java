package com.haidong.storysharing.controller;

import com.haidong.storysharing.common.Result;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/06/10:22
 * @Description:
 */
@RestController
@RequestMapping("/oauth")
public class RestAuthController {
    @RequestMapping("/getAuthUrl")
    public Result<String> getAuthUrl(){
        Result<String> result = new Result<>();
        AuthRequest authRequest = getAuthRequest();
        //生成第三方平台的授权链接
        String authorize = authRequest.authorize(AuthStateUtils.createState());
        result.setData(authorize);
        return result;
    }

    /**
     * 创建Request
     * @return
     */
    private AuthRequest getAuthRequest(){
        return new AuthQqRequest(AuthConfig.builder()
                .clientId("")
                .clientSecret("")
                //网站回调域
                .redirectUri("")
                .build());
    }
}
