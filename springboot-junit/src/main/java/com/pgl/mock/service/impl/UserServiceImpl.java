package com.pgl.mock.service.impl;

import com.pgl.mock.service.RoleService;
import com.pgl.mock.service.UserService;
import com.pgl.mock.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pgl
 * @description 描述
 * @date 2024/3/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Override
    public UserPojo findUserById(Long id) {
        System.out.println("findUserById()方法被调用了！");
        UserPojo userPojo = new UserPojo();
        userPojo.setId(155L);
        userPojo.setName("name");
        return userPojo;
    }

    @Override
    public String findNameById(Long id) {
        UserPojo userById = findUserById(id);
        String roleName = roleService.findRoleName();
        return "user name: " + userById.getName() + ", role name: " + roleName;
    }

}
