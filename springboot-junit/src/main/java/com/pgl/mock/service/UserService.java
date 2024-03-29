package com.pgl.mock.service;

import com.pgl.mock.pojo.UserPojo;

/**
 * @author pgl
 * @description 描述
 * @date 2024/3/19
 */
public interface UserService {

    UserPojo findUserById(Long id);

    String findNameById(Long id);

}
