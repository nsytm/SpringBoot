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

/**
 * @author pgl
 * @description 描述
 * @date 2024/3/19
 */
@ExtendWith(MockitoExtension.class) // 启用Mockito支持，自动初始化Mockito的环境
public class UserServiceTest {

    @Spy // 创建真实的对象实例，并允许模拟该对象的一些行为（当你想要保留对象的一些实际行为，同时又想模拟它的某些方法时，@Spy就非常有用。）
    @InjectMocks // 创建被测试类的实例时，自动注入被@Mock和@Spy注解创建的模拟对象或间谍对象
    private UserServiceImpl userServiceSpy;

    @Mock // 创建模拟的对象实例
    private RoleService roleService;

    @Test
    public void findNameByIdTest() {
        UserPojo userPojo = new UserPojo();
        userPojo.setId(155L);
        userPojo.setName("name1111");
        // 模拟调用并指定返回值，两种方法区别：
        // Mockito.doReturn()不会调用实际方法，
        // Mockito.when()会调用实际方法
        Mockito.doReturn(userPojo).when(userServiceSpy).findUserById(any(Long.class));
        // Mockito.when(userServiceSpy.findUserById(155L)).thenReturn(userPojo);

        Mockito.doReturn("角色名称").when(roleService).findRoleName();

        // 调用findNameById()进行测试
        String result = userServiceSpy.findNameById(15L);
        System.out.println("result: " + result);

    }

}

