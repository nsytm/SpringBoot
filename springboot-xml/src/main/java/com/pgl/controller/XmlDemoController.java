package com.pgl.controller;

import com.pgl.entity.Root;
import com.pgl.entity.XmlMessage;
import com.pgl.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * @author pgl
 * @description Java使用JAXB操作XML
 * @date 2023/6/28
 */
@Slf4j
@RestController
public class XmlDemoController {

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


    /**
     * 解析指定路径下的XML文件 (允许文件夹下有多个XML文件, 按文件名称正序排序)
     * <p>
     * 案例一: 目录下只有XML文件
     * 代码逻辑: 先解析并备份文件, 然后再入库
     */
    public static String parseXmlOne() {
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
     * 解析指定路径下的XML文件 (允许文件夹下有多个XML文件, 按文件名称正序排序)
     * <p>
     * 案例二: 目录下有XML文件和OK文件
     * 代码逻辑: 先根据OK文件拿到XML文件, 然后解析入库 (发生异常就不备份), 最后再备份
     */
    public static void parseXmlTwo() {
        // XML文件路径
        String sourceFilePath = "E:\\001\\demo\\xml file";
        // XML文件备份路径
        String bakFilePath = "E:\\001\\demo\\bak xml file";
        // 文件名称规则
        String fileNamePrefix = "tm_";
        // OK文件扩展名
        String okFileExtension = ".ok";
        // XML文件扩展名
        String xmlFileExtension = ".xml";

        // 获取XML文件
        File allFile = new File(sourceFilePath);
        File[] fileArr = allFile.listFiles();
        if (null == fileArr) {
            return;
        }
        // 按文件名称正序排序
        Arrays.sort(fileArr, Comparator.comparing(File::getName));
        XmlMessage xmlMessage;
        // 处理XML文件
        for (File file : fileArr) {
            String fileName = file.getName();
            log.info("正在处理的文件名称: {}", fileName);
            // 文件名称规则校验
            if (fileName.startsWith(fileNamePrefix)) {
                // 只有OK文件存在, 才能继续处理XML文件
                if (fileName.endsWith(okFileExtension)) {
                    // 根据OK文件获取XML文件
                    String xmlFileName = fileName.substring(0, fileName.indexOf("."));
                    String xmlFilePathName = sourceFilePath + File.separator + xmlFileName + xmlFileExtension;
                    File xmlFile = new File(xmlFilePathName);
                    if (xmlFile.exists()) {
                        // 如果发生异常, 则不挪动文件到备份目录
                        try (InputStream inputStream = Files.newInputStream(xmlFile.toPath())) {
                            // 解析XML
                            xmlMessage = XmlUtils.parseXml(inputStream, XmlMessage.class);
                            // JsonUtils.toJson(), Json字符串
                            log.info("XML文件解析后内容, xmlMessage: {}", xmlMessage.toString());

                            // 入库逻辑
                            // 。。。

                        } catch (Exception e) {
                            log.error("fileName: {}, XML文件解析异常!", fileName, e);
                            continue;
                        }
                        // 文件备份 (OK文件和XML文件)
                        Arrays.stream(fileArr).filter(fe -> fe.getName().startsWith(xmlFileName)).forEach(
                                sourceFile -> {
                                    String bakFilePathName = bakFilePath + File.separator + sourceFile.getName();
                                    if (!sourceFile.renameTo(new File(bakFilePathName))) {
                                        log.error("fileName: {}, 文件备份失败!", sourceFile.getName());
                                    }
                                }
                        );
                    } else {
                        log.info("fileName: {}, XML文件不存在!", xmlFilePathName);
                    }
                }
            } else {
                log.info("fileName: {}, 文件名称不符合规则!", fileName);
            }
        }
    }

}
