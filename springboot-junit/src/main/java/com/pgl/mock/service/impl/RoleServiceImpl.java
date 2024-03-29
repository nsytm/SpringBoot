package com.pgl.mock.service.impl;

import com.pgl.mock.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author pgl
 * @description 描述
 * @date 2024/3/19
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public String findRoleName() {
        return "角色名称";
    }

}
