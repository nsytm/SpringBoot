package com.pgl.controller;

import com.pgl.util.ThirdPartyDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * @Author pgl
 * @ClassName XmlController
 * @Description
 * @Date:2023/4/14
 */
@Slf4j
@RestController
public class XmlController {

    @GetMapping("/data/parseXmlDemo")
    public String parseXmlDemo() {

        // 文件路径
        String filePath = "";
        // 文件备份路径
        String bakFilePath = "";

        File originalFile = new File(filePath);
        File[] files = originalFile.listFiles();
        if (files != null && files.length > 0) {
            List<File> fileList = new ArrayList<>(Arrays.asList(files));
            // 正序排序
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            InputStream inputStream = null;
            String xmlMessage = null;
            for (File file : fileList) {
                String fileName = file.getName();
                log.info("XmlController.parseXmlDemo() 读取文件: {}", fileName);
                try {
                    inputStream = Files.newInputStream(file.toPath());
                    xmlMessage = ThirdPartyDataUtils.parseXml(inputStream, String.class);
                    log.info("XmlController.parseXmlDemo() 读取文件: {}, 文件内容是否为空: {}", fileName, xmlMessage == null ? "空" : "非空");
                } catch (Exception e) {
                    log.error("XmlController.parseXmlDemo() 解析文件异常, fileName: " + fileName, e);
                } finally {
                    IOUtils.closeQuietly(inputStream);
                    try {
                        boolean bool = file.renameTo(new File(bakFilePath));
                        if (!bool) {
                            log.error("fileName: " + fileName + ", 文件备份失败");
                        }
                    } catch (Exception e) {
                        log.error("fileName: " + fileName + ", 文件备份失败", e);
                    }
                }

                // xml数据入库
                try {
                    if (xmlMessage != null) {
                        // 文件数据处理逻辑
                    }
                } catch (Exception e) {
                    log.error("XmlController.parseXmlDemo() 文件数据处理异常, fileName: " + fileName, e);
                }
            }
        }
        return "success";
    }

}
