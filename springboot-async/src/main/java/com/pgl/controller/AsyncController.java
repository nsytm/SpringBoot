package com.pgl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pgl
 * @ClassName AsyncController
 * @Description 异步任务实现 Demo
 * @date:2023/4/7
 */
@Slf4j
@RestController
public class AsyncController {


    @GetMapping("/async/thread")
    public String asyncTaskThread() {

        // 传统的显式的 new Thread 实现异步任务
        Thread thread = new Thread(() -> {
            // 模拟任务执行时间
            try {
                Thread.sleep(30 * 1000L);
            } catch (InterruptedException e) {
                log.error("error", e);
            }

            for (int i = 0; i < 10; i++) {
                log.info(Thread.currentThread().getName() + "【使用匿名内部类的方式创建多线程】" + i);
            }
        });
        thread.start();

        return "success";
    }

    @GetMapping("/async/executors")
    public String asyncTaskExecutors() {

        // 使用线程池实现异步任务, 没有返回值
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executor.execute(() -> {
                // 模拟任务执行时间
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    log.error("error", e);
                }

                // 异步任务逻辑
                log.info("async task " + taskId + " is running");
            });
        }
        executor.shutdown();

        return "success";
    }


    @GetMapping("/async/executors/result")
    public String asyncTaskExecutorsResult() {

        // 使用线程池实现异步任务, 有返回值
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟任务执行时间
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                log.error("error", e);
            }
            // 异步执行的任务
            return 15;
        }, executor);
        future.thenAccept(result -> {
            // 异步任务执行完后的回调
            log.info("result: " + result);
        });
        executor.shutdown();

        return "success";
    }

    // TODO: 2023/4/7 CompletableFuture相关文档
    // https://blog.csdn.net/Lost_in_the_woods/article/details/125876816


}
