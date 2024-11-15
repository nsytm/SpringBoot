package com.example.marscode;

import java.math.BigDecimal;

/**
 * @author 天纵神威
 * @date 2024/11/15
 * @description 数字字符串格式化
 */
public class NumberStringFormat {

    public static String solution(String s) {
        // 使用 BigDecimal 自动去除无用的零，并以十进制数形式返回
        String plainString = new BigDecimal(s).toPlainString();

        // 分割整数和小数部分
        String[] parts = plainString.split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? "." + parts[1] : "";

        // 添加千位分隔符
        StringBuilder sb = new StringBuilder();
        int length = integerPart.length();
        for (int i = 0; i < length; i++) {
            if (i > 0 && (length - i) % 3 == 0) {
                sb.append(",");
            }
            sb.append(integerPart.charAt(i));
        }

        return sb.append(decimalPart).toString();
    }

    public static void main(String[] args) {
        System.out.println("1,294,512.12412".equals(solution("1294512.12412")));
        System.out.println("123,456,789.99".equals(solution("0000123456789.99")));
        System.out.println("987,654,321".equals(solution("987654321")));
    }

}