package com.example.all.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author pgl
 * @description 参数校验示例
 * @date 2023/8/11
 */
@Data
public class ValidQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "参数不能为空")
    private String paramA;

    @Size(max = 10, message = "参数最大长度10个字符")
    private String paramB;

    @NotNull(message = "参数不能为空")
    private Long paramC;

    @NotEmpty(message = "参数不能为空")
    private String paramD;

    private String paramE;

}
