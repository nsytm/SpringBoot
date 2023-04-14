package com.pgl.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * @Author pgl
 * @ClassName ThirdPartyDataUtils
 * @Date:2023/4/14
 */
@Slf4j
public final class ThirdPartyDataUtils {

    private ThirdPartyDataUtils() {
    }

    public static <T> T parseXml(InputStream stream, Class<T> cla) throws Exception {
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
                log.error("解析 xml 发生 jaxb 异常!", e);
            }
        }
        return null;
    }

}
