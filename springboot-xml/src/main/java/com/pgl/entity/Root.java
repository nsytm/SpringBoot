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

    @XmlElement(name = "META_DATA")
    @XmlElementWrapper(name = "META_DATA")
    private List<MetaData> metaData;


    @Data
    @XmlRootElement(name = "BASE_DATA")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BaseData {

        @XmlElement(name = "USER_CODE")
        private String userCode;

        @XmlElement(name = "USER_NAME")
        private String userName;

        @XmlElement(name = "SECURITY_INFO")
        private SecurityInfo securityInfo;


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

    @Data
    @XmlRootElement(name = "META_DATA")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MetaData {

        @XmlElement(name = "DEMO_ID")
        private String demoId;

    }

}
