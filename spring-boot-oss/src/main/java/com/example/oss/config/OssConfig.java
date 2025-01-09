package com.example.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 天纵神威
 * @date 2025/1/2
 * @description OSS配置类
 */
@Getter
@Setter
@Configuration
public class OssConfig {

    /**
     * 节点地址
     */
    private String endpoint = "endpoint";

    /**
     * 访问 key id
     */
    private String accessKeyId = "ak";

    /**
     * 访问 key 密钥
     */
    private String secretAccessKey = "sk";

    /**
     * 存储桶名称
     */
    // private String bucketName = "oss-cn-beijing";
    private String bucketName = "bucketName";

    /**
     * 最大连接数
     * 设置OSSClient允许打开的最大HTTP连接数，默认为1024个
     */
    private int maxConnections = 1024;

    // 。。。


    /**
     * 创建 OSSClient 实例
     */
    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

        // ClientBuilderConfiguration config = new ClientBuilderConfiguration();
        // config.setMaxConnections(maxConnections);
        // return new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey, config);
    }

}
