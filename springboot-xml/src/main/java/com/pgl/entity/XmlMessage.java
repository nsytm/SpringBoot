package com.pgl.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author pgl
 * @ClassName XmlMessageMO
 * @Description
 * @Date:2023/4/14
 */
@Data
@XmlRootElement(name = "MESSAGE")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMessage {

    @XmlElement(name = "HEAD")
    private String head;

    @XmlElement(name = "BODY")
    private Body body;

    @Data
    @XmlRootElement(name = "BODY")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {

        @XmlElement(name = "PrivilegeGroups")
        private String privilegeGroups;

//        @Data
//        @XmlRootElement(name = "PrivilegeGroups")
//        @XmlAccessorType(XmlAccessType.FIELD)
//        public static class PrivilegeGroups {
//
//            @XmlElement(name = "PrivilegeGroup")
//            private PrivilegeGroup privilegeGroup;
//
//            @Data
//            @XmlRootElement(name = "PrivilegeGroup")
//            @XmlAccessorType(XmlAccessType.FIELD)
//            public static class PrivilegeGroup {
//
//            }
//
//        }

    }

}
