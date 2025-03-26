package com.hexin.demo.executor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hexin
 * @date 2024/09/13 14:19
 * @description
 **/
@Setter
@Getter
@Slf4j
public class ExecutorConfiguration {
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
    private final TimeUnit keepAliveTimeUnit;
    private final int queueCapacity;
    private final RejectedExecutionHandler rejectedExecutionHandler;
    private final String threadNamePrefix;
    private final boolean preStartCoreThreads;
    private final ThreadFactory threadFactory;
    private final boolean allowCoreThreadTimeOut;
    private final int maxQueueCapacity;

    private ExecutorConfiguration(Builder builder) {
        this.corePoolSize = builder.corePoolSize;
        this.maximumPoolSize = builder.maximumPoolSize;
        this.keepAliveTime = builder.keepAliveTime;
        this.keepAliveTimeUnit = builder.keepAliveTimeUnit;
        this.queueCapacity = builder.queueCapacity;
        this.rejectedExecutionHandler = builder.rejectedExecutionHandler;
        this.threadNamePrefix = builder.threadNamePrefix;
        this.preStartCoreThreads = builder.preStartCoreThreads;
        this.threadFactory = builder.threadFactory != null ? builder.threadFactory : new NamedThreadFactory(threadNamePrefix);
        this.allowCoreThreadTimeOut = builder.allowCoreThreadTimeOut;
        this.maxQueueCapacity = builder.maxQueueCapacity;
    }

    public static class Builder {
        private int corePoolSize = AVAILABLE_PROCESSORS;
        private int maximumPoolSize = AVAILABLE_PROCESSORS * 2;
        private long keepAliveTime = 60L;
        private TimeUnit keepAliveTimeUnit = TimeUnit.SECONDS;
        private int queueCapacity = 500;
        private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        private final String threadNamePrefix = "TracerExecutor-Thread-";
        private boolean preStartCoreThreads = false;
        private ThreadFactory threadFactory = null;
        private boolean allowCoreThreadTimeOut = false;
        private int maxQueueCapacity = 5000;

        public Builder corePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        public Builder maximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        public Builder keepAliveTime(long keepAliveTime, TimeUnit unit) {
            this.keepAliveTime = keepAliveTime;
            this.keepAliveTimeUnit = unit;
            return this;
        }

        public Builder queueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public Builder rejectedExecutionHandler(RejectedExecutionHandler handler) {
            this.rejectedExecutionHandler = handler;
            return this;
        }

        public Builder threadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        public ExecutorConfiguration build() {
            return new ExecutorConfiguration(this);
        }

        public Builder preStartCoreThreads(boolean preStartCoreThreads) {
            this.preStartCoreThreads = preStartCoreThreads;
            return this;
        }

        public Builder allowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
            this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
            return this;
        }

        public Builder maxQueueCapacity(int maxQueueCapacity) {
            this.maxQueueCapacity = maxQueueCapacity;
            return this;
        }
    }

    public static class ExecutorHolder {
        private final ExecutorService executor;
        private final ResizableLinkedBlockingQueue<Runnable> queue;

        ExecutorHolder(ExecutorConfiguration config) {
            this.queue = new ResizableLinkedBlockingQueue<>(config.queueCapacity);
            ThreadPoolExecutor threadPoolExecutor = new MonitorableThreadPoolExecutor(
                    config.threadNamePrefix,
                    config.corePoolSize,
                    config.maximumPoolSize,
                    config.keepAliveTime,
                    config.keepAliveTimeUnit,
                    config.maxQueueCapacity,
                    queue,
                    config.threadFactory,
                    config.rejectedExecutionHandler
            );
            threadPoolExecutor.allowCoreThreadTimeOut(config.allowCoreThreadTimeOut);
            if (config.preStartCoreThreads) {
                threadPoolExecutor.prestartAllCoreThreads();
            }
            this.executor = threadPoolExecutor;
        }

        ExecutorService getExecutor() {
            return executor;
        }

        void shutdown() {
            TracerExecutor.shutdownExecutors(TracerExecutor.DEFAULT_SHUTDOWN_TIMEOUT, TracerExecutor.DEFAULT_SHUTDOWN_TIMEOUT_UNIT);
        }
    }


    private static class NamedThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}


