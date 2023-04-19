package com.pgl.common;

import lombok.Data;

import java.util.List;

@Data
public class UserGroup {

    public UserGroup(String UUID, String name, List<Users> userList) {
        this.UUID = UUID;
        this.name = name;
        this.userList = userList;
    }

    /**
     * 用户组唯一编码
     */
    private String UUID;

    /**
     * 用户组名称
     */
    private String name;

    /**
     * 用户列表
     */
    private List<Users> userList;

    @Data
    public static class Users {

        public Users(String branchCode, String userCode) {
            this.branchCode = branchCode;
            this.userCode = userCode;
        }

        /**
         * 用户所属分公司
         */
        private String branchCode;

        /**
         * 用户工号
         */
        private String userCode;

    }

}
