package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author pgl
 * @description
 * @date 2023/12/20
 */
@Slf4j
@RestController
public class FileDownloadController {

    /**
     * 模板文件下载
     * <a href="http://localhost:8080/api/downloadExcel">...</a>
     */
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) {
        try {
            // 服务器上文件所在路径（本地调试时修改文件路径）
            String filePath = "E:\\IdeaProject\\MyProject\\template\\导入模板.xlsx";
            // 设置响应头
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("文件模板.xlsx", "UTF-8"));
            // 设置响应类型
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // 读取文件并写入输出流
            try (FileInputStream fileInputStream = new FileInputStream(filePath);
                 OutputStream outputStream = response.getOutputStream()) {
                IOUtils.copy(fileInputStream, outputStream);
            }
        } catch (FileNotFoundException e) {
            log.error("download excel error", e);
            throw new RuntimeException("文件不存在");
        } catch (IOException e) {
            log.error("download excel error", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

}
