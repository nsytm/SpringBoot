package com.example.all.entity.txt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author pgl
 * @description 人员表实体类
 * @date 2023/7/10
 */
// @TableName("user") // 指定表名
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 主键
     */
    private Long id;

    /**
     * 人员姓名
     */
    private String name;

    /**
     * 人员所属分公司代码
     */
    private String branchCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
