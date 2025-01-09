package com.example.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.example.oss.config.OssConfig;
import com.example.oss.service.OssService;
import com.example.oss.util.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author 天纵神威
 * @date 2024/12/27
 * @description OSS API
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 列举所有存储空间
     */
    @Override
    public List<Bucket> listBuckets() {
        try {
            return ossClient.listBuckets();
        } catch (OSSException | ClientException e) {
            log.error("list buckets fail", e);
            throw new RuntimeException("list buckets fail");
        }

        // 如果 OSS 客户端是通过 Spring 容器管理的（使用 @Autowired 注解注入），
        // 关闭 ossClient 相当于销毁了这个 Spring Bean，后续再使用会报错，
        // 例如 BeanCurrentlyInCreationException。
        /* finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }*/
    }

    /**
     * 列举存储空间下的文件。单次请求默认最多列举100个文件。
     */
    public List<OSSObjectSummary> listObjects(String bucketName) {
        try {
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            return objectListing.getObjectSummaries();
        } catch (OSSException | ClientException e) {
            log.error("list objects fail", e);
            throw new RuntimeException("list objects fail");
        }
    }

    /**
     * 下载文件
     * 注：使用 OSSObject.getObjectContent() 获取输入流，需要手动关闭，避免内存泄露
     *
     * @param key 对象键
     */
    @Override
    public OSSObject downloadObject(String key) {
        String formatKey = OssUtils.formatKey(key);
        try {
            OSSObject object = ossClient.getObject(ossConfig.getBucketName(), formatKey);
            if (object == null || object.getObjectContent() == null) {
                throw new RuntimeException("OSS下载文件失败: 文件不存在");
            }
            return object;
        } catch (OSSException | ClientException e) {
            log.error("OSS下载文件失败: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param key         对象键
     * @param inputStream 文件流
     */
    @Override
    public void uploadObject(String key, InputStream inputStream) {
        // key 相同，object 会被覆盖
        String formatKey = OssUtils.formatKey(key);
        try {
            ossClient.putObject(ossConfig.getBucketName(), formatKey, inputStream);
        } catch (OSSException | ClientException e) {
            // log.error("upload object fail", e);
            log.error("OSS上传文件失败: ", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // log.error("close input stream fail", e);
                log.error("OSS上传文件后释放资源失败: ", e);
            }
        }
    }

    /**
     * 上传文件
     *
     * @param key  对象键
     * @param file 文件
     */
    public void uploadObject(String key, File file) {
        String formatKey = OssUtils.formatKey(key);
        try {
            PutObjectResult putObjectResult = ossClient.putObject(ossConfig.getBucketName(), formatKey, file);
            log.info("upload object result: {}", putObjectResult);
        } catch (OSSException | ClientException e) {
            log.error("upload object fail", e);
            throw new RuntimeException("upload object fail");
        }
    }

    /**
     * 删除文件或目录
     *
     * @param key 对象键
     */
    @Override
    public void deleteObject(String key) {
        String formatKey = OssUtils.formatKey(key);
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(ossConfig.getBucketName(), formatKey);
        } catch (OSSException | ClientException e) {
            log.error("OSS删除文件失败: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 0SS生成文件签名URL。可以使用Get方法访问签名URL下载文件
     *
     * @param key 对象键
     */
    @Override
    public String getObjectUrl(String key) {
        String formatKey = OssUtils.formatKey(key);

        // 设置签名URL过期时间，单位为毫秒。12小时
        Date expiration = new Date(new Date().getTime() + 12 * 3600 * 1000L);
        try {
            // 生成以GET方法访问的签名URL。可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(ossConfig.getBucketName(), formatKey, expiration);
            return String.valueOf(url);
        } catch (OSSException | ClientException e) {
            log.error("OSS生成文件签名URL失败: ", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        // upload(new ByteArrayInputStream("hello".getBytes()));

    }


}
