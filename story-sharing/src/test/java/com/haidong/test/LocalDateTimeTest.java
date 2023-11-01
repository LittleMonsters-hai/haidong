package com.haidong.test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import com.haidong.storysharing.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/10/14:21
 * @Description:
 */
public class LocalDateTimeTest {

    @Test
    public void test1(){

        /*System.out.println(LocalDateTime.now());
        DateUtils dateUtils = new DateUtils();
        String s = dateUtils.formatDateTime(LocalDateTime.now());
        System.out.println(s);

        String s1 = DateUtils.formatDate(LocalDateTime.now());
        System.out.println(s1);

        String s2 = DateUtils.formatTime(LocalDateTime.now());
        System.out.println(s2);

        String hashpw = BCrypt.hashpw("123456", BCrypt.gensalt());
        String s3 = BCrypt.hashpw("123456", BCrypt.gensalt());
        boolean checkpw = BCrypt.checkpw(hashpw, s3);
        System.out.println(hashpw);
        System.out.println(s3);
        System.out.println(checkpw);
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        String encode = bpe.encode("123456");
        System.out.println("encode ==" + encode);
        System.out.println(bpe.matches("123456",encode));*/
    }

    @Test
    public void test2(){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tG8aKrfisGuq8s8K2z7";
        String accessKeySecret = "fBuNk3iYEe9tcZDtVjU4BT0hmOaiDw";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "haidong-story";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "图片.jpg";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath= "D:\\图片\\hai.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = new FileInputStream(filePath);
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            System.out.println(result.getResponse().getStatusCode());
        }catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        System.out.println("上传成功");
    }
}
