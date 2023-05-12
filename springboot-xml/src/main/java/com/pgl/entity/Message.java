package com.pgl.entity;

/**
 * @Author pgl
 * @ClassName Message
 * @Description
 * @Date:2023/4/17
 */

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "MESSAGE")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    private Head HEAD;
    private Body BODY;

    // 省略 getter 和 setter 方法

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Head {
        // 省略字段定义
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {
        @XmlElement(name = "PrivilegeGroups")
        private PrivilegeGroups privilegeGroups;

        // 省略 getter 和 setter 方法

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class PrivilegeGroups {
            @XmlElement(name = "PrivilegeGroup")
            private List<PrivilegeGroup> privilegeGroupList;

            // 省略 getter 和 setter 方法

            @XmlAccessorType(XmlAccessType.FIELD)
            public static class PrivilegeGroup {
                /**
                 * 用户组名称, not null
                 */
                private String name;

                /**
                 * 唯一编码 (集约调度平台用户组数据的唯一编码UUID), not null
                 */
                private String uniqueCode;

                /**
                 * 任务类型代码, not null
                 */
                private String taskTypeCode;

                /**
                 * 所属机构代码 (分公司, 层级关系xxx.xxx.xxx.xxx), not null
                 */
                private String orgCode;

                /**
                 * 部门组代码
                 */
                private String deptGroupCode;

                /**
                 * 部门代码
                 */
                private String deptCode;

                /**
                 * 数据类型 (线上=online, 人伤=injurey, 营运服务平台=fwpt), not null
                 */
                private String dataType;

                /**
                 * 线上分队代码
                 */
                private String teamCode;

                /**
                 * 线上分队名称
                 */
                private String teamName;

                /**
                 * 互溢组唯一编码 (互溢组UUID)
                 */
                private String backupGroupUUID;

                /**
                 * 合议-工作组类型
                 */
                private String groupType;

                /**
                 * 基础量
                 */
                private Integer dailyCount;

                /**
                 * 是否普通岗位
                 */
                private String isJunior;

                /**
                 * 通赔任务等级
                 */
                private String claimRemoteLevel;

                /**
                 * 大案预报确认任务 (大案审核级别)
                 */
                private String criticalCaseAuditLevel;

                /**
                 * 大案组标志 (1:是 0:否)
                 */
                private String criticalcase;

                /**
                 * 管理片区代码
                 */
                private String regionInfo;

                /**
                 * 操作人/更新人
                 */
                private String operator;

                /**
                 * 更新时间
                 */
                private LocalDate updatetime;

                /**
                 * 功能区属性编码 (1 常规功能区, 2 通赔功能区, 3 大案功能区)
                 */
                private String groupAttribute;

                /**
                 * 删除标记 (1:是 0:否)
                 */
                private String deleteFlag;

                @XmlElement(name = "PrivilegeUserGroups")
                private PrivilegeUserGroups privilegeUserGroups;

                // 省略 getter 和 setter 方法

                @XmlAccessorType(XmlAccessType.FIELD)
                public static class PrivilegeUserGroups {
                    @XmlElement(name = "PrivilegeUserGroup")
                    private List<PrivilegeUserGroup> privilegeUserGroupList;

                    // 省略 getter 和 setter 方法

                    @XmlAccessorType(XmlAccessType.FIELD)
                    public static class PrivilegeUserGroup {
                        /**
                         * 分公司编码, not null
                         */
                        private String branchCode;

                        /**
                         * 用户编码, not null
                         */
                        private String userCode;

                        /**
                         * 是否组长 (1:是 0:否, 默认为0)
                         */
                        private String isleader;

                    }
                }
            }
        }
    }
}

