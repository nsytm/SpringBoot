package com.example.valid;

import com.example.common.CommonConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pgl
 * @description 批次号校验  格式: YYYYMMDDXXX (XXX: 001-999)
 * @date 2023/7/25
 */
public class BatchNumberValidator {

    public static boolean isValidBatchNumber(String batchNumber) {
        if (batchNumber == null || batchNumber.length() != 11) {
            return false;
        }

        String datePart = batchNumber.substring(0, 8);
        String batchPart = batchNumber.substring(8);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(CommonConstants.DATE_PATTERN);
        try {
            // 日期字符串转日期
            LocalDate.parse(datePart, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        // 正则匹配后三位
        Pattern pattern = Pattern.compile(CommonConstants.BATCH_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(batchPart);
        if (!matcher.matches()) {
            return false;
        }
        int batchNumberValue = Integer.parseInt(batchPart);
        if (batchNumberValue < 1) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String batchNumber1 = "20230725001";
        String batchNumber2 = "20230725000"; // 批次号小于001
        String batchNumber3 = "20230725999";
        String batchNumber4 = "2023072501";  // 批次号不足三位

        System.out.println(isValidBatchNumber(batchNumber1)); // true
        System.out.println(isValidBatchNumber(batchNumber2)); // false
        System.out.println(isValidBatchNumber(batchNumber3)); // true
        System.out.println(isValidBatchNumber(batchNumber4)); // false
    }
}