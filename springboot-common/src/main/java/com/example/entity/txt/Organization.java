package com.example.entity.txt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pgl
 * @description 机构表实体类
 * @date 2023/7/10
 */
// @TableName("organization") // 指定表名
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    /**
     * 主键
     */
    private Long id;

    /**
     * 机构名称
     */
    private String name;

}
