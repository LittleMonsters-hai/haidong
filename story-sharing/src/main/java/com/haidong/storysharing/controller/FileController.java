package com.haidong.storysharing.controller;

import com.haidong.storysharing.utils.AliyunOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/19/23:54
 * @Description:
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    //OSS服务器访问域名
    @Value("http://oss-cn-beijing.aliyuncs.com")
    private String endpoint;

    //子账户名
    @Value("LTAI5tQE1t5DcE119v6rbXEq")
    private String accessKeyId;

    //子账户密码
    @Value("cPFf8kYz1Q3pyq5pHnZLgOLdCnWOhN")
    private String accessKeySecret;

    //桶名字
    @Value("haidong_story")
    private String bucketName;

    @GetMapping("/upload")
    public void upload(@RequestParam MultipartFile file) throws IOException {
        AliyunOSSUtil.uploadByInputStreamFile(endpoint,accessKeyId,accessKeySecret,bucketName,file.getOriginalFilename(),file.getInputStream());
        log.info("上传文件");
    }

}
