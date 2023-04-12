package com.pgl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author pgl
 * @ClassName AsyncController
 * @Description 异步任务实现 Demo
 * @date:2023/4/7
 */
@Slf4j
@RestController
public class AsyncController {

    @Autowired
    private ExecutorService myExecutor;


    @GetMapping("/async/thread")
    public String asyncTaskThread() {

        // 传统的显式的 new Thread 实现异步任务
        Thread thread = new Thread(() -> {
            // 模拟任务执行时间
            sleep(30 * 1000, TimeUnit.MILLISECONDS);

            // 异步任务逻辑
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
                sleep(2, TimeUnit.SECONDS);

                // 异步任务逻辑
                log.info("async task " + taskId + " is running");
            });
        }
        executor.shutdown();

        return "success";
    }


    /**
     * 使用 CompletableFuture 和 Executor 实现异步任务, 有返回值
     *
     * @return String
     */
    @GetMapping("/async/executors/result")
    public String asyncTaskExecutorsResult() {

        // 创建一个自定义的线程池, 包含两个线程... 这里有问题, 每次请求都会创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // 使用 supplyAsync() 方法创建一个异步任务, 并指定自定义的线程池
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("=== async task start");

            // 异步任务逻辑
            log.info("async task " + Thread.currentThread().getName() + " is running");
            // 模拟任务执行时间
            sleep(15, TimeUnit.SECONDS);

            log.info("=== async task end");
            return "true";
        }, executor);
        // 使用 thenAccept() 方法处理异步任务的执行结果, 注意: thenAccept() 方法是异步的
        future.thenAccept(result -> {
            // 异步任务执行完后的回调, 获取结果
            log.info("callback result: " + result);
            // 在这里可以根据异步任务的结果进行后续操作
        });
        // 关闭线程池
        executor.shutdown();

        return "success";
    }

    // TODO: 2023/4/7 CompletableFuture相关文档
    // https://blog.csdn.net/Lost_in_the_woods/article/details/125876816


    private void sleep(long timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("error", e);
        }
    }

}
