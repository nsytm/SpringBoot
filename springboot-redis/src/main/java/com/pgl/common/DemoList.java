package com.pgl.common;

import java.util.ArrayList;
import java.util.List;

public class DemoList {
    public static void main(String[] args) {

        UserGroup.Users userA = new UserGroup.Users("101", "001");
        UserGroup.Users userB = new UserGroup.Users("102", "002");
        UserGroup.Users userC = new UserGroup.Users("103", "003");

        List<UserGroup.Users> userList = new ArrayList<>();
        userList.add(userA);
        userList.add(userB);
        userList.add(userC);

        UserGroup userGroupA = new UserGroup("UU01", "A", userList);
        UserGroup userGroupB = new UserGroup("UU02", "B", userList);

        List<UserGroup> userGroupList = new ArrayList<>();
        userGroupList.add(userGroupA);
        userGroupList.add(userGroupB);

        List<User> finalUsers = new ArrayList<>();
        User user;
        for (UserGroup userGroup : userGroupList) {
            user = new User();
            user.setUUID(userGroup.getUUID());
            user.setUserGroupName(userGroup.getName());
            for (UserGroup.Users users : userGroup.getUserList()) {
                user.setBranchCode(users.getBranchCode());
                user.setUserCode(users.getUserCode());
                finalUsers.add(user);
            }
        }

        System.out.println(finalUsers);


    }


}
