package com.example.oss.service;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;

import java.io.InputStream;
import java.util.List;

/**
 * @author 天纵神威
 * @date 2025/1/2
 * @description OSS API
 */
public interface OssService {

    /**
     * '
     * 列举所有存储空间
     */
    List<Bucket> listBuckets();

    /**
     * 下载文件
     */
    OSSObject downloadObject(String key);

    /**
     * 上传文件
     */
    void uploadObject(String key, InputStream inputStream);

    /**
     * 删除文件或目录
     */
    void deleteObject(String key);

    /**
     * 0SS生成文件签名URL。可以使用Get方法访问URL下载文件
     */
    String getObjectUrl(String key);

}
