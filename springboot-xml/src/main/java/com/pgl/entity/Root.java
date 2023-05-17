package com.pgl.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @Author pgl
 * @ClassName Root
 * @Description
 * @Date:2023/4/26
 */
@Data
@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {

    @XmlElement(name = "BASE_DATA")
    private BaseData baseData;

    /**
     * 瞬态字段不参与 JAXB 的序列化和反序列化
     */
    private transient String transientString;

    /**
     * 静态字段不参与 JAXB 的序列化和反序列化
     */
    private static String staticString;

    /**
     * @XmlTransient 注解不参与 JAXB 的序列化和反序列化
     */
    @XmlTransient
    private String isTransient;


    @Data
    @XmlRootElement(name = "BASE_DATA")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BaseData {

        @XmlAttribute(name = "USER_NAME")
        private String userName;

        @XmlAttribute(name = "USER_CODE")
        private String userCode;

        @XmlElement(name = "SECURITY_INFO")
        @XmlElementWrapper(name = "SECURITY_INFOS")
        private List<SecurityInfo> securityInfos;


        @Data
        @XmlRootElement(name = "SECURITY_INFO")
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class SecurityInfo {

            @XmlElement(name = "SYSTEM_ID")
            private String systemId;

            @XmlElement(name = "SYSTEM_NAME")
            private String systemName;

        }

    }

}
