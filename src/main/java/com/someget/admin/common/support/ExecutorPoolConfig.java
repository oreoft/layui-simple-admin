package com.someget.admin.common.support;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author zyf
 * @date 2022-04-04 20:04
 */
@Setter
@Component
@ConfigurationProperties(prefix = "executor.pool")
public class ExecutorPoolConfig {
    /**
     * 核心线程池大小
     */
    private int corePoolSize;

    /**
     * 线程池维护线程的最大数量
     */
    private int maxPoolSize;

    /**
     * 队列最大长度
     */
    private int queueCapacity;

    /**
     * 线程池维护空闲线程存在时间
     */
    private int keepAliveSeconds;

    /**
     * 拒绝策略
     */
    private final RejectedExecutionHandler rejectedExecutionHandler;

    /**
     * 线程池前缀
     */
    private final String threadNamePrefix;

    public ExecutorPoolConfig() {
        this.rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        this.threadNamePrefix = "common-executorPool";
    }

    @Bean
    public ThreadPoolExecutor executor() {
        return new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                ThreadFactoryBuilder.create().setNamePrefix(threadNamePrefix).build(),
                rejectedExecutionHandler
        );
    }
}
