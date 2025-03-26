package com.hexin.demo.executor;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hexin
 * @date 2024/09/13 15:13
 * @description
 **/
@Getter
public class MonitorableThreadPoolExecutor extends ThreadPoolExecutor {
    private static final Logger log = LoggerFactory.getLogger(MonitorableThreadPoolExecutor.class);

    private final AtomicInteger totalTaskCount = new AtomicInteger(0);
    private final AtomicLong completedTaskCount = new AtomicLong(0);
    private final AtomicLong totalExecutionTime = new AtomicLong(0);
    private final AtomicLong maxExecutionTime = new AtomicLong(0);
    private final ReentrantLock statsLock = new ReentrantLock();
    private final long startTime;
    private final String threadName;
    private final ResizableLinkedBlockingQueue<Runnable> workQueue;
    private final int maxQueueCapacity;
    private final double upperThresholds = 0.75;
    private final double lowerThresholds = 0.25;

    public MonitorableThreadPoolExecutor(String threadName, int corePoolSize,
                                         int maximumPoolSize,
                                         long keepAliveTime,
                                         TimeUnit unit,
                                         int maxQueueCapacity,
                                         BlockingQueue<Runnable> workQueue,
                                         ThreadFactory threadFactory,
                                         RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadName = threadName;
        this.startTime = System.currentTimeMillis();
        this.workQueue = (ResizableLinkedBlockingQueue<Runnable>) getQueue();
        this.maxQueueCapacity = maxQueueCapacity;
    }

    private void autoResizeQueue() {
        if (workQueue instanceof ResizableLinkedBlockingQueue) {
            ResizableLinkedBlockingQueue<Runnable> resizableQueue = workQueue;
            int capacity = resizableQueue.getCapacity();
            double usageRatio = resizableQueue.getUsageRatio();
            if (usageRatio > upperThresholds) { // 当使用率超过75%时扩容
                int newCapacity = Math.min(capacity * 2, maxQueueCapacity);
                if (newCapacity > capacity) {
                    resizableQueue.setCapacity(newCapacity);
                    log.info("Queue capacity increased from {} to {}", capacity, newCapacity);
                }
            } else if (usageRatio < lowerThresholds && capacity > 100) { // 当使用率低于25%且容量大于100时缩容
                int newCapacity = Math.max(capacity / 2, 100);
                resizableQueue.setCapacity(newCapacity);
                log.info("Queue capacity decreased from {} to {}", capacity, newCapacity);
            }
        }
    }


    @Override
    public void execute(Runnable command) {
        totalTaskCount.incrementAndGet();
        super.execute(() -> executeWithStats(command));
        autoResizeQueue();
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        totalTaskCount.incrementAndGet();
        return super.submit(() -> submitWithStats(task));
    }

    @Override
    public Future<?> submit(Runnable task) {
        totalTaskCount.incrementAndGet();
        return super.submit(() -> executeWithStats(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        totalTaskCount.incrementAndGet();
        return super.submit(() -> {
            executeWithStats(task);
            return result;
        });
    }

    private void executeWithStats(Runnable task) {
        long startTime = System.nanoTime();
        try {
            task.run();
        } finally {
            updateExecutionStats(System.nanoTime() - startTime);
        }
    }

    private <T> T submitWithStats(Callable<T> task) throws Exception {
        long startTime = System.nanoTime();
        try {
            return task.call();
        } finally {
            updateExecutionStats(System.nanoTime() - startTime);
        }
    }

    private void updateExecutionStats(long executionTime) {
        totalExecutionTime.addAndGet(executionTime);
        updateMaxExecutionTime(executionTime);
        completedTaskCount.incrementAndGet();
    }

    private void updateMaxExecutionTime(long executionTime) {
        maxExecutionTime.updateAndGet(currentMax -> Math.max(currentMax, executionTime));
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        totalTaskCount.incrementAndGet();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long start = System.nanoTime();
            super.afterExecute(r, t);
            long end = System.nanoTime();
            long executionTime = end - start;

            totalExecutionTime.addAndGet(executionTime);
            updateMaxExecutionTime(executionTime);

            completedTaskCount.incrementAndGet();

            if (t != null) {
                log.error("Uncaught exception in task execution", t);
            }
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        printFinalStats();
    }

    public void printFinalStats() {
        ExecutorStats stats = getStats();
        long totalTime = System.currentTimeMillis() - startTime;
        double avgCompletionRate = stats.getCompletedTasks() / (totalTime / 1000.0);
        log.info("Final Executor Statistics for {} [max={}]: Runtime: {} ms, {}, Avg completion rate: {} tasks/s",
                threadName, getMaximumPoolSize(), totalTime, stats, String.format("%.2f", avgCompletionRate));
    }

    public ExecutorStats getStats() {
        statsLock.lock();
        try {
            return new ExecutorStats(
                    threadName,
                    getPoolSize(),
                    getActiveCount(),
                    completedTaskCount.get(),
                    totalTaskCount.get(),
                    totalExecutionTime.get(),
                    maxExecutionTime.get()
            );
        } finally {
            statsLock.unlock();
        }
    }

    @Getter
    public static class ExecutorStats {
        private final String threadName;
        private final int poolSize;
        private final int activeThreads;
        private final long completedTasks;
        private final int totalTasks;
        private final long totalExecutionTime;
        private final long maxExecutionTime;

        public ExecutorStats(String threadName, int poolSize, int activeThreads, long completedTasks, int totalTasks,
                             long totalExecutionTime, long maxExecutionTime) {
            this.threadName = threadName;
            this.poolSize = poolSize;
            this.activeThreads = activeThreads;
            this.completedTasks = completedTasks;
            this.totalTasks = totalTasks;
            this.totalExecutionTime = totalExecutionTime;
            this.maxExecutionTime = maxExecutionTime;
        }


        @Override
        public String toString() {
            return String.format("[%s] Pool: %d, Active: %d, Completed: %d, Total: %d, AvgTime: %.2fms, MaxTime: %.2fms",
                    threadName, poolSize, activeThreads, completedTasks, totalTasks,
                    completedTasks > 0 ? totalExecutionTime / (double) completedTasks / 1_000_000 : 0,
                    maxExecutionTime / 1_000_000.0);
        }
    }

    public long getCompletedTaskCount() {
        return completedTaskCount.get();
    }
}