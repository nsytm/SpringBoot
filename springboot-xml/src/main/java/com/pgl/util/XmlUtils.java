package com.pgl.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Author pgl
 * @ClassName ThirdPartyDataUtils
 * @Date:2023/4/14
 */
@Slf4j
public final class XmlUtils {

    private XmlUtils() {
    }

    /**
     * XML转实体类
     *
     * @param stream XML文件流
     * @param cla    实体类
     */
    public static <T> T parseXml(InputStream stream, Class<T> cla) {
        if (stream != null) {
            try {
                // 关闭外部实体和 DTO 的解析, 防止 xml 实体注入
                XMLInputFactory factory = XMLInputFactory.newFactory();
                factory.setProperty(XMLInputFactory.IS_COALESCING, false);
                factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
                XMLStreamReader reader = factory.createXMLStreamReader(new BufferedInputStream(stream));
                JAXBContext context = JAXBContext.newInstance(cla);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                return (T) unmarshaller.unmarshal(reader);
            } catch (JAXBException e) {
                log.error("解析 XML 发生 JAXB 异常!", e);
                throw new RuntimeException("解析 XML 发生 JAXB 异常!", e);
            } catch (Exception e) {
                log.error("解析 XML 发生异常!", e);
                throw new RuntimeException("解析 XML 发生异常!", e);
            }
        }
        return null;
    }

    /**
     * XML字符串转实体类
     *
     * @param xmlStr XML字符串
     * @param cla    实体类
     */
    public static <T> T xmlStrToObject(String xmlStr, Class<T> cla) {
        if (StringUtils.isNotBlank(xmlStr)) {
            try {
                InputStream inputStream = IOUtils.toInputStream(xmlStr, StandardCharsets.UTF_8);
                return parseXml(inputStream, cla);
            } catch (Exception e) {
                log.error("对象转 XML 发生 JAXB 异常!", e);
                throw new RuntimeException("对象转 XML 发生 JAXB 异常!", e);
            }
        }
        return null;
    }

    /**
     * 对象转XML
     *
     * @param obj 目标对象
     * @return 返回String格式的XML报文
     */
    public static <T> String objectToXml(T obj) {
        StringWriter sw = new StringWriter();
        try {
            // 通过传入的类,创建该类的转换上下文
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            // 创建实例
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式,true会格式化输出,false会全部压缩到一起 (本地DUG使用TRUE)
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 是否打印xml的说明头 <?xml version="1.0" encoding="UTF-8" standalone="yes">
            // 设置为true表示不打印,设置为false表示打印,默认打印
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            log.error("对象转 XML 发生 JAXB 异常!", e);
            throw new RuntimeException("对象转 XML 发生 JAXB 异常!", e);
        } finally {
            try {
                sw.close();
            } catch (IOException e) {
                log.error("对象转 XML 发生 IO 异常!", e);
            }
        }
        return sw.toString();
    }

}
