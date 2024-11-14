package com.example.base.list;

import com.example.vo.LogVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 天纵神威
 * @date 2024/11/6
 * @description 根据多个字段去重，并保持元素顺序不变
 */
@Slf4j
public class ListDistinct {

    public static void main(String[] args) {

        List<LogVO> logVOList = new ArrayList<>();
        logVOList.add(new LogVO().setLogId(1L).setTitle("新增用户").setErrorMsg("新增失败"));
        logVOList.add(new LogVO().setLogId(2L).setTitle("删除用户").setErrorMsg("删除失败"));
        logVOList.add(new LogVO().setLogId(3L).setTitle("新增用户").setErrorMsg("新增失败"));

        // 根据所有元素去重
        logVOList.stream().distinct().forEach(logVO -> {
            log.info("{} {} {}", logVO.getLogId(), logVO.getTitle(), logVO.getErrorMsg());
        });

        log.info("----------------------------------------------------------------------");

        // 根据指定元素去重
        List<LogVO> distinctList = logVOList.stream().collect(Collectors.toMap(
                        logVO -> Arrays.asList(logVO.getTitle(), logVO.getErrorMsg()),
                        logVO -> logVO,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new))
                .values().stream().toList();
        distinctList.forEach(logVO -> {
            log.info("{} {} {}", logVO.getLogId(), logVO.getTitle(), logVO.getErrorMsg());
        });

    }

}