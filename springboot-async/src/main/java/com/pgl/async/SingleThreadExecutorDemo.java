package com.pgl.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pgl
 * @ClassName SingleThreadExecutorDemo
 * @Description
 * @date:2023/4/7
 */
@Slf4j
public class SingleThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executor.execute(() -> {

                if (taskId == 5) {
                    int[] a = new int[2];
                    int b = a[3];
                }
                log.info("Task " + taskId + " is running.");
            });
        }
        executor.shutdown();

    }
}
