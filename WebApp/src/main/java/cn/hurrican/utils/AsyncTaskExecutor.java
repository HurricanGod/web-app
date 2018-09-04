package cn.hurrican.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/6/11
 * @Modified 12:12
 */
@Component("asyncExecutor")
public class AsyncTaskExecutor {

    private static ThreadPoolExecutor executor;

    static class AsyncThreadFactory implements ThreadFactory{
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;

        public String getNamePrefix() {
            return namePrefix;
        }

        public void setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        public AsyncThreadFactory(){
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "async-executor-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public AsyncThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        /**
         * Constructs a new {@code Thread}.  Implementations may also initialize
         * priority, name, daemon status, {@code ThreadGroup}, etc.
         *
         * @param r a runnable to be executed by new thread instance
         * @return constructed thread, or {@code null} if the request to
         * create a thread is rejected
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()){
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY){
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    static {
        executor = new ThreadPoolExecutor(10, 20, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(100),
                new AsyncThreadFactory("common_async_task"));
        executor.allowCoreThreadTimeOut(true);
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookTask(executor)));
    }

    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
