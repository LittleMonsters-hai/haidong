package com.haidong.storysharing.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/19/23:49
 * @Description:
 */
public class AliyunOSSUtil {
    /**
     * byte数组格式上传文件
     * @param endpoint          OSS对外服务的访问域名
     * @param accessKeyId       accessKey账号
     * @param accessKeySecret   accessKey密码
     * @param bucketName        桶名字
     * @param objectName        完整文件名, 例如abc/efg/123.jpg
     * @param content           文件内容, byte数组格式
     */
    public static void uploadByByteArrayFile(String endpoint,
                                             String accessKeyId,
                                             String accessKeySecret,
                                             String bucketName,
                                             String objectName,
                                             byte[] content) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //创建上传请求对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content));

        // 上传
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 输入流格式上传文件
     * @param endpoint          OSS对外服务的访问域名
     * @param accessKeyId       accessKey账号
     * @param accessKeySecret   accessKey密码
     * @param bucketName        桶名字
     * @param objectName        完整文件名, 例如abc/efg/123.jpg
     * @param content           文件内容, 输入流格式
     * @Author 壹哥
     */
    public static void uploadByInputStreamFile(String endpoint,
                                               String accessKeyId,
                                               String accessKeySecret,
                                               String bucketName,
                                               String objectName,
                                               InputStream content) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传
        ossClient.putObject(bucketName, objectName, content);
        // 关闭OSSClient
        ossClient.shutdown();
    }

    /**
     * byte数组格式上传文件并返回上传后的URL地址
     * @param endpoint          OSS对外服务的访问域名
     * @param accessKeyId       accessKey账号
     * @param accessKeySecret   accessKey密码
     * @param bucketName        桶名字
     * @param objectName        完整文件名, 例如abc/efg/123.jpg
     * @param content           文件内容, byte数组格式
     * @Author 壹哥
     */
    public static String uploadImage(String endpoint,
                                     String accessKeyId,
                                     String accessKeySecret,
                                     String bucketName,
                                     String objectName,
                                     byte[] content)  throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpg");
        // 文件上传
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content), objectMetadata);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        //返回url地址
        String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
        //关闭OSSClient。
        ossClient.shutdown();
        return url;
    }

    /**
     * 下载文件到本地
     * @param endpoint          OSS对外服务的访问域名
     * @param accessKeyId       accessKey账号
     * @param accessKeySecret   accessKey密码
     * @param bucketName        桶名字
     * @param objectName        完整文件名, 例如abc/efg/123.jpg
     * @param localFile         下载到本地文件目录
     * @Author 壹哥
     */
    public static void downFile(String endpoint,
                                String accessKeyId,
                                String accessKeySecret,
                                String bucketName,
                                String objectName,
                                String localFile) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除文件
     * @param endpoint          OSS对外服务的访问域名
     * @param accessKeyId       accessKey账号
     * @param accessKeySecret   accessKey密码
     * @param bucketName        桶名字
     * @param objectName        完整文件名, 例如abc/efg/123.jpg
     * @Author 壹哥
     */
    public static void deleteFile(String endpoint,
                                  String accessKeyId,
                                  String accessKeySecret,
                                  String bucketName,
                                  String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
