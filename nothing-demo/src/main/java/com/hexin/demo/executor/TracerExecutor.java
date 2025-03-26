package com.hexin.demo.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author hexin
 * @date 2024/09/13 14:50
 * @description
 **/
@Slf4j
public class TracerExecutor {

    private static final ExecutorConfiguration DEFAULT_CONFIG = new ExecutorConfiguration.Builder().build();
    private static volatile ExecutorConfiguration.ExecutorHolder DEFAULT_EXECUTOR_HOLDER = new ExecutorConfiguration.ExecutorHolder(DEFAULT_CONFIG);
    private static final TracerProvider DEFAULT_TRACER_PROVIDER = TracerProvider.defaultProvider();
    private static final ExecutorService DEFAULT_EXECUTOR = getDefaultExecutor();

    private static final ScheduledExecutorService TIMEOUT_SCHEDULER = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "TracerExecutor-Timeout-Thread");
        t.setDaemon(true);
        return t;
    });

    public static final long DEFAULT_SHUTDOWN_TIMEOUT = 30;
    public static final TimeUnit DEFAULT_SHUTDOWN_TIMEOUT_UNIT = TimeUnit.SECONDS;
    private static final Set<ExecutorService> CUSTOM_EXECUTORS = Collections.newSetFromMap(new ConcurrentHashMap<>());


    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down TracerExecutor executors...");
            Set<ExecutorService> executorsToShutdown = new HashSet<>(CUSTOM_EXECUTORS);
            executorsToShutdown.add(DEFAULT_EXECUTOR_HOLDER.getExecutor());
            executorsToShutdown.add(TIMEOUT_SCHEDULER);
            shutdownExecutors(DEFAULT_SHUTDOWN_TIMEOUT, DEFAULT_SHUTDOWN_TIMEOUT_UNIT, executorsToShutdown.toArray(new ExecutorService[0]));
        }));
    }

    private TracerExecutor() {
    }

    public static class Builder {
        private ExecutorService executor = DEFAULT_EXECUTOR;
        private TracerProvider tracerProvider = DEFAULT_TRACER_PROVIDER;
        private Long defaultTimeout;
        private TimeUnit defaultTimeoutUnit;

        public Builder withExecutor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public Builder withTracerProvider(TracerProvider tracerProvider) {
            this.tracerProvider = tracerProvider;
            return this;
        }

        public Builder withDefaultTimeout(long timeout, TimeUnit unit) {
            this.defaultTimeout = timeout;
            this.defaultTimeoutUnit = unit;
            return this;
        }

        public TracerExecutor build() {
            return new TracerExecutor();
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return supplyAsync(supplier, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, ExecutorService executor) {
        return supplyAsync(supplier, executor, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, ExecutorService executor, TracerProvider tracerProvider) {
        return CompletableFuture.supplyAsync(tracerProvider.wrap(supplier), executor);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, long timeout, TimeUnit unit) {
        return supplyAsync(supplier, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, ExecutorService executor, long timeout, TimeUnit unit) {
        return supplyAsync(supplier, executor, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, ExecutorService executor, TracerProvider tracerProvider, Long timeout, TimeUnit unit) {
        CompletableFuture<T> future = supplyAsync(supplier, executor, tracerProvider);
        return timeout != null ? withTimeout(future, timeout, unit) : future;
    }


    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return runAsync(runnable, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, ExecutorService executor) {
        return runAsync(runnable, executor, DEFAULT_TRACER_PROVIDER);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, ExecutorService executor, TracerProvider tracerProvider) {
        return CompletableFuture.runAsync(tracerProvider.wrap(runnable), executor);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, long timeout, TimeUnit unit) {
        return runAsync(runnable, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, ExecutorService executor, long timeout, TimeUnit unit) {
        return runAsync(runnable, executor, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, ExecutorService executor, TracerProvider tracerProvider, long timeout, TimeUnit unit) {
        return withTimeout(runAsync(runnable, executor, tracerProvider), timeout, unit);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return submit(task, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> Future<T> submit(Callable<T> task, ExecutorService executor) {
        return submit(task, executor, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> Future<T> submit(Callable<T> task, ExecutorService executor, TracerProvider tracerProvider) {
        return executor.submit(tracerProvider.wrap(task));
    }

    public static <T> Future<T> submit(Callable<T> task, long timeout, TimeUnit unit) {
        return submit(task, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static <T> Future<T> submit(Callable<T> task, ExecutorService executor, long timeout, TimeUnit unit) {
        return submit(task, executor, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static <T> Future<T> submit(Callable<T> task, ExecutorService executor, TracerProvider tracerProvider, Long timeout, TimeUnit unit) {
        Future<T> future = submit(task, executor, tracerProvider);
        return timeout != null ? new TimedFuture<>(future, timeout, unit) : future;
    }

    public static Future<?> submit(Runnable task) {
        return submit(task, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER);
    }

    public static Future<?> submit(Runnable task, ExecutorService executor) {
        return submit(task, executor, DEFAULT_TRACER_PROVIDER);
    }

    public static Future<?> submit(Runnable task, ExecutorService executor, TracerProvider tracerProvider) {
        return executor.submit(tracerProvider.wrap(task));
    }

    public static Future<?> submit(Runnable task, long timeout, TimeUnit unit) {
        return submit(task, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static Future<?> submit(Runnable task, ExecutorService executor, long timeout, TimeUnit unit) {
        return submit(task, executor, DEFAULT_TRACER_PROVIDER, timeout, unit);
    }

    public static Future<?> submit(Runnable task, ExecutorService executor, TracerProvider tracerProvider, Long timeout, TimeUnit unit) {
        Future<?> future = submit(task, executor, tracerProvider);
        return timeout != null ? new TimedFuture<>(future, timeout, unit) : future;
    }

    public static CompletableFuture<Void> allOf(CompletableFuture<?>... futures) {
        return CompletableFuture.allOf(futures);
    }

    public static CompletableFuture<Void> allOf(long timeout, TimeUnit unit, CompletableFuture<?>... futures) {
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        return withTimeout(allOf, timeout, unit);
    }

    public static <T> CompletableFuture<List<T>> allOfWithResults(CompletableFuture<T>... futures) {
        return allOf(futures).thenApply(v ->
                Arrays.stream(futures)
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    public static <T> CompletableFuture<List<T>> allOfWithResults(long timeout, TimeUnit unit, CompletableFuture<T>... futures) {
        CompletableFuture<List<T>> result = allOfWithResults(futures);
        return withTimeout(result, timeout, unit);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return invokeAll(tasks, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, ExecutorService executor) throws InterruptedException {
        return invokeAll(tasks, executor, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, ExecutorService executor, TracerProvider tracerProvider) throws InterruptedException {
        return executor.invokeAll(tasks.stream().map(tracerProvider::wrap).collect(Collectors.toList()));
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return invokeAny(tasks, DEFAULT_EXECUTOR, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks, ExecutorService executor) throws InterruptedException, ExecutionException {
        return invokeAny(tasks, executor, DEFAULT_TRACER_PROVIDER);
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks, ExecutorService executor, TracerProvider tracerProvider) throws InterruptedException, ExecutionException {
        return executor.invokeAny(tasks.stream().map(tracerProvider::wrap).collect(Collectors.toList()));
    }

    private static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, long timeout, TimeUnit unit) {
        CompletableFuture<T> timeoutFuture = new CompletableFuture<>();
        TIMEOUT_SCHEDULER.schedule(() -> {
            if (!future.isDone()) {
                timeoutFuture.completeExceptionally(new TimeoutException("Operation timed out"));
                future.cancel(true);
            }
        }, timeout, unit);

        return future.applyToEither(timeoutFuture, Function.identity());
    }

    public static void registerCustomExecutor(ExecutorService executor) {
        if (executor != null && executor != DEFAULT_EXECUTOR && executor != TIMEOUT_SCHEDULER) {
            CUSTOM_EXECUTORS.add(executor);
        }
    }

    public static void unregisterCustomExecutor(ExecutorService executor) {
        CUSTOM_EXECUTORS.remove(executor);
    }

    public static void shutdownCustomExecutor(ExecutorService executor) {
        shutdownCustomExecutor(executor, DEFAULT_SHUTDOWN_TIMEOUT, DEFAULT_SHUTDOWN_TIMEOUT_UNIT);
    }

    public static void shutdownCustomExecutor(ExecutorService executor, long timeout, TimeUnit unit) {
        if (executor != null && executor != DEFAULT_EXECUTOR) {
            shutdownExecutors(timeout, unit, executor);
        }
    }

    public static ExecutorService getDefaultExecutor() {
        return DEFAULT_EXECUTOR_HOLDER.getExecutor();
    }


    public static synchronized void setDefaultExecutorParameters(ExecutorConfiguration config) {
        ExecutorConfiguration.ExecutorHolder oldHolder = DEFAULT_EXECUTOR_HOLDER;
        DEFAULT_EXECUTOR_HOLDER = new ExecutorConfiguration.ExecutorHolder(config);
        oldHolder.shutdown();
        log.info("Default executor has been updated with new parameters.");
    }

    public interface TracerProvider {
        <T> Supplier<T> wrap(Supplier<T> supplier);

        Runnable wrap(Runnable runnable);

        <T> Callable<T> wrap(Callable<T> callable);

        static TracerProvider defaultProvider() {
            return new DefaultTracerProvider();
        }
    }

    private static class DefaultTracerProvider implements TracerProvider {
        @Override
        public <T> Supplier<T> wrap(Supplier<T> supplier) {
            return () -> {
                TracerCallable<T> tracerCallable = new TracerCallable<T>() {
                    @Override
                    public T doCall() throws Exception {
                        return supplier.get();
                    }
                };
                try {
                    return tracerCallable.call();
                } catch (Exception e) {
                    throw new RuntimeException("Error executing traced supplier", e);
                }
            };
        }

        @Override
        public Runnable wrap(Runnable runnable) {
            return new TracerRunnable() {
                @Override
                public void doRun() {
                    runnable.run();
                }
            };
        }

        @Override
        public <T> Callable<T> wrap(Callable<T> callable) {
            return new TracerCallable<T>() {
                @Override
                public T doCall() throws Exception {
                    return callable.call();
                }
            };
        }
    }

    private static class TimedFuture<T> implements Future<T> {
        private final Future<T> delegate;
        private final long timeout;
        private final TimeUnit unit;

        TimedFuture(Future<T> delegate, long timeout, TimeUnit unit) {
            this.delegate = delegate;
            this.timeout = timeout;
            this.unit = unit;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return delegate.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return delegate.isCancelled();
        }

        @Override
        public boolean isDone() {
            return delegate.isDone();
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            try {
                return get(timeout, unit);
            } catch (TimeoutException e) {
                throw new ExecutionException("Task timed out", e);
            }
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return delegate.get(timeout, unit);
        }
    }

    public static void shutdownExecutors(long timeout, TimeUnit unit, ExecutorService... executors) {
        for (ExecutorService executor : executors) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(timeout, unit)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(timeout, unit)) {
                        log.error("ExecutorService did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            } finally {
                if (executor instanceof MonitorableThreadPoolExecutor) {
                    ((MonitorableThreadPoolExecutor) executor).printFinalStats();
                }
            }
        }
    }
}