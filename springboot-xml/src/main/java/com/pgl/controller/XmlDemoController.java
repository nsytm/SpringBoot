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
 * @ClassName XmlDemoController
 * @Description Java使用JAXB操作XML
 * @Date:2023/4/14
 */
@Slf4j
@RestController
public class XmlDemoController {

    /**
     * 读取指定路径的XML文件, 解析入库
     */
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
            // 文件名称 升序排序
            fileList.sort(Comparator.comparing(File::getName));
            // Collections.sort(fileList, Comparator.comparing(File::getName));
            InputStream inputStream = null;
            XmlMessage xmlMessage = null;
            for (File file : fileList) {
                String fileName = file.getName();
                log.info("XmlDemoController.parseXmlDemo() 读取文件: {}", fileName);
                try {
                    log.info("file.toPath(): {}", file.toPath());
                    inputStream = Files.newInputStream(file.toPath());
                    xmlMessage = XmlUtils.parseXml(inputStream, XmlMessage.class);
                    log.info("XmlDemoController.parseXmlDemo() 解析文件: {}, 文件内容是否为空: {}", fileName, xmlMessage == null ? "空" : "非空");
                } catch (Exception e) {
                    log.error("XmlDemoController.parseXmlDemo() 解析文件异常, fileName: " + fileName, e);
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
                    log.error("XmlDemoController.parseXmlDemo() 文件数据处理异常, fileName: " + fileName, e);
                }
            }
        }
        return "success";
    }


    /**
     * JAXB 序列化和反序列化
     */
    public static void main(String[] args) {

        Root root = new Root();
        Root.BaseData baseData = new Root.BaseData();

        Root.BaseData.SecurityInfo securityInfoA = new Root.BaseData.SecurityInfo();
        securityInfoA.setSystemId("Q159");
        securityInfoA.setSystemName("大神之命");

        Root.BaseData.SecurityInfo securityInfoB = new Root.BaseData.SecurityInfo();
        securityInfoB.setSystemId("W160");
        securityInfoB.setSystemName("我即唯一");

        List<Root.BaseData.SecurityInfo> securityInfoList = new ArrayList<>();
        securityInfoList.add(securityInfoA);
        securityInfoList.add(securityInfoB);

        baseData.setUserName("张三");
        baseData.setUserCode("hangman_001");
        baseData.setSecurityInfos(securityInfoList);

        root.setBaseData(baseData);

        /*
         不参与 JAXB 序列化和反序列化的一些情况:
         1.瞬态字段和静态字段不参与 JAXB 的序列化和反序列化
         2.静态字段不参与 JAXB 的序列化和反序列化
         3.@XmlTransient 注解不参与 JAXB 的序列化和反序列化
         */
        root.setTransientString("transientString_001");
        root.setIsTransient("@XmlTransient 注解");

        // Root对象转XML
        String strXml = XmlUtils.objectToXml(root);
        System.out.println("=== Root 对象转 XML:\n" + strXml);


        // XML字符串转实体类
        strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ROOT>\n" +
                "    <BASE_DATA USER_NAME=\"张三\" USER_CODE=\"hangman_001\">\n" +
                "        <SECURITY_INFOS>\n" +
                "            <SECURITY_INFO>\n" +
                "                <SYSTEM_ID>Q159</SYSTEM_ID>\n" +
                "                <SYSTEM_NAME>大神之命</SYSTEM_NAME>\n" +
                "            </SECURITY_INFO>\n" +
                "            <SECURITY_INFO>\n" +
                "                <SYSTEM_ID>W160</SYSTEM_ID>\n" +
                "                <SYSTEM_NAME>我即唯一</SYSTEM_NAME>\n" +
                "            </SECURITY_INFO>\n" +
                "        </SECURITY_INFOS>\n" +
                "    </BASE_DATA>\n" +
                "    <transientString>transientString_001</transientString>\n" +
                "</ROOT>";
        Root deBug = XmlUtils.xmlStrToObject(strXml, Root.class);
        System.out.println("=== XML 字符串转 Root 对象:\n" + deBug);

    }

}
