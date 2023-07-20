package com.example.all.demo;

import com.example.all.entity.txt.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author pgl
 * @description 描述
 * @date 2023/7/7
 */
@Slf4j
public class TxtFile {

// 由上游系统向下游系统提供数据, 供数规范由下游系统提供。
//
// 供数规范:
//     1.上游系统将数据以TXT文件发送到指定目录, 并在该目录最后生成相应的信号文件 (OK文件), 标识数据可以被下游系统接收使用;
//     2.目录固定格式由下游系统提供, 如 \demo\file\txt\YYYYMMDD\001
//     3.文件名格式, 如 DEMO_TABLE_NAME_20230710_001.txt
//     4.文件内容格式, 如 3 || 9999999 || DEMO || 20230710001 || 字段值 || 字段值 ||\n
//     5.信号文件名格式, 如 DEMO_20230710_001.ok
//     6.信号文件内容格式, 如 DEMO_TABLE_NAME_20230710_001.txt || 文件记录数 || 文件大小 ||\n
//
// 注意点:
//     1.供数文件权限 664
//     2.批次 001 - 999
//     3.TXT 文件字符集 UTF-8
//     4.上线第一次全量推送, 后续增量推送
//     5.重送文件
//
// 问题:
//     1.推送 频率(批次)、数据范围 --- 业务
//     2.数据日期 t+1
//     3.文件内容 增量类型 1新增 2删除 3修改(默认)
//     4.日期型字段 yyyy-mm-dd hh24:mi:ss
//     5.重送规则 推送记录表 (主键、批次号、批次文件名称、推送状态、推送方式、推送路径、推送失败原因、推送条件、文件状态
//     创建时间、更新时间、创建者、更新者、删除状态)

//     推送状态: 1-成功 2-失败
//     推送方式: 1-正常推送 2-重复推送
//     文件状态: 1-正常 2-已删除

    public static void main(String[] args) {
        // String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh24:mi:ss"));
        // System.out.println("yyyy-MM-dd hh24:mi:ss === " + format);
        // java.lang.IllegalArgumentException: Unknown pattern letter: i

        // doGetTwo();
        // doGetOne();
    }

    /**
     * 批处理入口
     */
    public static void doGetOne() {
        // 查询推送记录表, 查询条件为当天时间, 查询到 batchNo+1, 查询不到 NowDate+001
        String batchNo = "20230711004";

        // 查询人员数据
        List<User> users = listUsers();
        // 查询机构数据

        exportData(users, batchNo);

        log.info("正常推送成功了!");
    }

    /**
     * web页面入口
     */
    public static void doGetTwo() {
        // 查询推送记录表, 取批次号和查询条件
        String batchNo = "20230711003";

        // 查询人员数据
        List<User> users = listUsers();
        // 查询机构数据

        exportData(users, batchNo);

        log.info("重复推送成功了!");
    }

    public static void exportData(List<User> users, String batchNo) {
        // 推送路径
        String filePath = "E:\\001\\demo\\txt file\\";
        String systemCode = "DEMO";

        // String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nowDate = batchNo.substring(0, 8);
        // String batchNumber = "003";
        String batchNumber = batchNo.substring(8);
        String basicPath = filePath + nowDate + File.separator + batchNumber;
        // 创建目录
        new File(basicPath).mkdirs();
        try {
            // 生成人员表TXT文件
            String usersTxtFileName = basicPath + File.separator + systemCode + "_TABLE_NAME_" + nowDate + "_" + batchNumber + ".txt";
            writeToFile(usersTxtFileName, formatUsers(users, systemCode, nowDate, batchNumber));

            // 生成OK文件
            String okFileName = basicPath + File.separator + systemCode + "_" + nowDate + "_" + batchNumber + ".ok";
            writeToFile(okFileName, generateOkFileContent(usersTxtFileName, null));

        } catch (Exception e) {
            log.error("生成TXT或者OK文件发生异常!", e);
            throw new RuntimeException("生成TXT或者OK文件发生异常!");
        }

    }

    private static List<User> listUsers() {
        User userA = new User(new Random().nextLong(), "张三", "31001", LocalDateTime.now());
        User userB = new User(new Random().nextLong(), "张四", "31001", LocalDateTime.now());
        User userC = new User();
        userC.setId(new Random().nextLong()).setName("张五");
        User userD = new User();
        userD.setId(new Random().nextLong()).setBranchCode("31002");
        List<User> users = new ArrayList<>();
        users.add(userA);
        users.add(userB);
        users.add(userC);
        users.add(userD);
        return users;
    }

    /**
     * 人员数据格式化
     */
    private static String formatUsers(List<User> users, String systemCode, String nowDate, String batchNumber) {
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            String prefix;
            if (null != user.getBranchCode()) {
                // TODO: 2023/7/11 增量、全量、追加  3
                prefix = "3 || " + user.getBranchCode() + " || " + systemCode + " || " + nowDate + batchNumber + " || ";
            } else {
                prefix = "3 || " + " || " + systemCode + " || " + nowDate + batchNumber + " || ";
            }
            sb.append(prefix);
            sb.append(appendStr(user.getId()))
                    .append(appendStr(user.getName()))
                    .append(appendStr(user.getCreateTime()))
                    .append(appendStr(user.getBranchCode()));
            // if (null != user.getBranchCode()) {
            //     sb.append(user.getBranchCode()).append(" ||\n");
            // } else {
            sb.append("\n");
            // }
        }
        return sb.toString();
    }

    /**
     * 生成TXT文件
     */
    private static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(Paths.get(fileName)), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            log.error("生成文件发生IO异常!", e);
            throw new RuntimeException("生成文件发生IO异常!");
        }
    }

    private static String appendStr(Object obj) {
        if (null != obj) {
            return " " + obj + " ||";
        } else {
            return "  ||";
        }
    }

    /**
     * 信号文件内容
     */
    private static String generateOkFileContent(String usersTxtFileName, String organizationsTxtFileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(usersTxtFileName.substring(usersTxtFileName.lastIndexOf(File.separator) + 1))
                .append(" || ")
                .append(getRecordCount(usersTxtFileName))
                .append(" || ")
                .append(getFileSize(usersTxtFileName))
                .append(" ||\n");
        return sb.toString();
    }

    /**
     * 文件大小
     */
    private static long getFileSize(String pathName) {
        File file = new File(pathName);
        return file.length();
    }

    /**
     * 文件记录数
     */
    private static long getRecordCount(String pathName) {
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(pathName))) {
            lineNumberReader.skip(Long.MAX_VALUE);
            return lineNumberReader.getLineNumber();
        } catch (IOException e) {
            log.error("获取文件记录数发生异常!", e);
            throw new RuntimeException("获取文件记录数发生异常!");
        }
    }

}
