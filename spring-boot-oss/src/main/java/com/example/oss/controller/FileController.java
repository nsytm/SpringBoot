package com.example.oss.controller;

import com.aliyun.oss.model.OSSObject;
import com.example.oss.service.OssService;
import com.example.oss.util.FileUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author 天纵神威
 * @date 2025/1/2
 * @description 阿里云对象存储OSS操作示例
 */
@Slf4j
@RestController
@RequestMapping("/api/oss/file")
public class FileController {

    @Autowired
    private OssService ossService;

    /**
     * 文件下载
     */
    @PostMapping("/download")
    public void download(String filePath, HttpServletResponse response) {
        // 根据文件路径下载
        try (OSSObject object = ossService.downloadObject(filePath);
             InputStream inputStream = new BufferedInputStream(object.getObjectContent());
             OutputStream outputStream = response.getOutputStream()) {
            // 文件名称
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

            // 设置响应头
            response.reset();
            response.setContentType(object.getObjectMetadata().getContentType());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

            byte[] buffer = new byte[1024];
            int length;
            // 从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，然后写入响应流，直至读到末尾返回-1结束
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("文件下载失败: {}", filePath, e);
            throw new RuntimeException("文件下载失败!");
        }
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public void upload(MultipartFile multipartFile) {
        FileUtils.checkFile(multipartFile);
        String fileName = "oss/" + multipartFile.getOriginalFilename();
        try {
            ossService.uploadObject(fileName, multipartFile.getInputStream());
        } catch (Exception e) {
            // log.error("upload file fail", e);
            throw new RuntimeException("文件上传失败!");
        }
    }

    /**
     * 文件删除
     */
    @PostMapping("/delete")
    public void delete(String filePath) {
        // filePath = oss/111.txt
        // 如果 oss/ 目录下仅存在 oss/111.txt 这一个文件，在删除 oss/111.txt 后，oss/ 目录也会被移除
        try {
            ossService.deleteObject(filePath);
        } catch (Exception e) {
            throw new RuntimeException("文件删除失败!");
        }
    }

    /**
     * 生成文件签名URL
     */
    @PostMapping("/getObjectUrl")
    public String getObjectUrl(String filePath) {
        try {
            return ossService.getObjectUrl(filePath);
        } catch (Exception e) {
            throw new RuntimeException("获取去文件签名URL失败!");
        }
    }

}
