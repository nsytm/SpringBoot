package com.pgl.common;

import lombok.Data;

@Data
public class User {

    /**
     * 主键ID
     */
    private String UUID;

    /**
     * 用户所在组名称
     */
    private String userGroupName;

    /**
     * 用户所属分公司
     */
    private String branchCode;

    /**
     * 用户工号
     */
    private String userCode;

}
