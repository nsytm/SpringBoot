package com.example.oss.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.example.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * @author tangmu
 * @date 2024/12/26
 * @description 功能描述
 */
@Slf4j
@RestController
@RequestMapping("/api/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 列举所有存储空间
     */
    @PostMapping("/listBuckets")
    public List<Bucket> listBuckets() {
        return ossService.listBuckets();
    }

    /**
     * 列举存储空间下的文件
     */
    @GetMapping("/listObjects")
    public List<OSSObjectSummary> listObjects(@RequestParam("bucketName") String bucketName) {
        // return ossService.listObjects(bucketName);
        return null;
    }

    /**
     * 生成一个唯一的 Bucket 名称
     */
    public static String generateUniqueBucketName(String prefix) {
        // 获取当前时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 生成一个 0 到 9999 之间的随机数
        Random random = new Random();
        int randomNum = random.nextInt(10000);
        // 连接以形成一个唯一的 Bucket 名称
        return prefix + "-" + timestamp + "-" + randomNum;
    }

    /**
     * 阿里云对象存储 OSS API 示例
     */

    public static void main(String[] args) throws com.aliyuncs.exceptions.ClientException {
        // 设置 OSS Endpoint 和 Bucket 名称
        String endpoint = "endpoint";
        // String bucketName = generateUniqueBucketName("demo");
        String bucketName = "bucketName";
        log.info("bucketName: {}", bucketName);

        String accessKeyId = "ak";
        String secretAccessKey = "sk";

        // 替换为您的 Bucket 区域
        String region = "cn-hangzhou";
        // 创建 OSSClient 实例
        // EnvironmentVariableCredentialsProvider credentialsProvider =
        //         CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // OSS ossClient = OSSClientBuilder.create()
        //         .endpoint(endpoint)
        //         .credentialsProvider(credentialsProvider)
        //         .region(region)
        //         .build();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);
        try {
            List<Bucket> buckets = ossClient.listBuckets();
            log.info("buckets: {}", buckets.toString());


            // 1. 创建存储空间（Bucket）
            ossClient.createBucket(bucketName);
            System.out.println("1. Bucket " + bucketName + " 创建成功。");

            // 2. 上传文件
            String objectName = "exampledir/exampleobject.txt";
            String content = "Hello OSS";
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
            System.out.println("2. 文件 " + objectName + " 上传成功。");
            // 3. 下载文件
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            InputStream contentStream = ossObject.getObjectContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(contentStream));
            String line;
            System.out.println("3. 下载的文件内容：");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            contentStream.close();
            // 4. 列出文件
            System.out.println("4. 列出 Bucket 中的文件：");
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + " (大小 = " + objectSummary.getSize() + ")");
            }
            // 5. 删除文件
            ossClient.deleteObject(bucketName, objectName);
            System.out.println("5. 文件 " + objectName + " 删除成功。");
            // 6. 删除存储空间（Bucket）
            ossClient.deleteBucket(bucketName);
            System.out.println("6. Bucket " + bucketName + " 删除成功。");
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException | IOException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }


}
