package com.example.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author pgl
 * @description Stream流工具类
 * @date 2023/8/16
 */
@Slf4j
public class StreamUtils {

    public static String toString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == inputStream) {
            return stringBuilder.toString();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            int bytesRead;
            char[] charBuffer = new char[256];
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

}
