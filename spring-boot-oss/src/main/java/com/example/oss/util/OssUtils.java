package com.example.oss.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 天纵神威
 * @date 2025/1/2
 * @description 功能描述
 */
@Slf4j
public class OssUtils {

    public static String formatKey(String key) {
        // key 不能为空
        if (StringUtils.isBlank(key)) {
            return key;
        }

        // 替换连续多个 '/' 为单个 '/'
        key = key.replaceAll("(/)+", "/");

        // key 不能以 ‘/’ 开头
        if (key.startsWith("/")) {
            key = key.substring(1);
        }

        log.info("OSS对象键: {}", key);
        return key;
    }

}
