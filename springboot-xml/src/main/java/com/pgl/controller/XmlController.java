package com.pgl.controller;

import com.pgl.entity.Root;
import com.pgl.entity.XmlMessage;
import com.pgl.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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
        String filePath = "E:\\001\\demo\\xml file";
        // 文件备份路径
        String bakFilePath = "E:\\001\\demo\\bak xml file";

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
            XmlMessage xmlMessage = null;
            for (File file : fileList) {
                String fileName = file.getName();
                log.info("XmlController.parseXmlDemo() 读取文件: {}", fileName);
                try {
                    log.info("file.toPath(): {}", file.toPath());
                    inputStream = Files.newInputStream(file.toPath());
                    xmlMessage = XmlUtils.parseXml(inputStream, XmlMessage.class);
                    log.info("XmlController.parseXmlDemo() 解析文件: {}, 文件内容是否为空: {}", fileName, xmlMessage == null ? "空" : "非空");
                } catch (Exception e) {
                    log.error("XmlController.parseXmlDemo() 解析文件异常, fileName: " + fileName, e);
                } finally {
                    IOUtils.closeQuietly(inputStream);
                    try {
                        log.info("bakFilePath: {}", bakFilePath + File.separator + fileName);
                        boolean bool = file.renameTo(new File(bakFilePath + File.separator + fileName));
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


    public static void main(String[] args) {

        Root root = new Root();
        Root.BaseData baseData = new Root.BaseData();
        List<Root.MetaData> metaData = new ArrayList<>();
        Root.MetaData metaDataA = new Root.MetaData();
        Root.MetaData metaDataB = new Root.MetaData();
        Root.MetaData metaDataC = new Root.MetaData();

        Root.BaseData.SecurityInfo securityInfo = new Root.BaseData.SecurityInfo();
        securityInfo.setSystemId("123456789");
        securityInfo.setSystemName("大神");

        baseData.setUserName("张三");
        baseData.setUserCode("zhangsan");
        baseData.setSecurityInfo(securityInfo);

        metaDataA.setDemoId("A01");
        metaDataB.setDemoId("B01");
        metaDataC.setDemoId("C01");
        metaData.add(metaDataA);
        metaData.add(metaDataB);
        metaData.add(metaDataC);

        root.setBaseData(baseData);
        root.setMetaData(metaData);

        String strXml = XmlUtils.objToXml(root);
        System.out.println("Root 对象转 XML: " + strXml);


    }

}
