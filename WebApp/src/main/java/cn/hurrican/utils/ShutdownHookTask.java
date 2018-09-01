package cn.hurrican.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/6/11
 * @Modified 12:30
 */
public class ShutdownHookTask implements Runnable {

    private static Logger logger = LogManager.getLogger(ShutdownHookTask.class);

    private ThreadPoolExecutor executor;

    public ShutdownHookTask(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public ShutdownHookTask() {
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (executor != null) {
            try {
                System.out.println("执行关闭线程池操作！");
                logger.info("shutdown");
                executor.shutdownNow();
            } catch (Exception e) {
                System.out.println("关闭线程池操作出现异常");
                e.printStackTrace();
                logger.error("ShutdownHookTask线程执行出现异常：\n{}", e);
            }
        }
    }
}
