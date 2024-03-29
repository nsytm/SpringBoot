package com.pgl.service;

import com.pgl.mock.service.RoleService;
import com.pgl.mock.service.impl.UserServiceImpl;
import com.pgl.mock.pojo.UserPojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * @author pgl
 * @description 描述
 * @date 2024/3/19
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Spy
    @InjectMocks
    private UserServiceImpl userServiceSpy;

    @Mock
    private RoleService roleService;

    @Test
    public void findNameByIdTest() {
        UserPojo userPojo = new UserPojo();
        userPojo.setId(155L);
        userPojo.setName("name1111");
        // 模拟调用并指定返回值（两种方法区别：Mockito.doReturn()不会调用真实方法，Mockito.when()会调用真实方法）
        Mockito.doReturn(userPojo).when(userServiceSpy).findUserById(any(Long.class));
        // Mockito.when(userServiceSpy.findUserById(155L)).thenReturn(userPojo);

        Mockito.doReturn("角色名称").when(roleService).findRoleName();

        // 调用findNameById()进行测试
        String result = userServiceSpy.findNameById(15L);
        System.out.println("result: " + result);

    }

}

