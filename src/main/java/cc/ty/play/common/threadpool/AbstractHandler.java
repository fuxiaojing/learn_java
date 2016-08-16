package cc.ty.play.common.threadpool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * date: 2016/7/18 15:07.
 *
 * @author taoyang (ty.ice@me.com)
 */
public abstract class AbstractHandler implements Handler{

    private static final long DEFAULT_KEEP_ALIVE_TIME = 60;
    private PoolExecutor executor;
    private String processorName = this.getClass().getSimpleName();

    public AbstractHandler(int workSize, int queueSize) {
        this(workSize, queueSize, null);
    }

    public AbstractHandler(int workSize, int queueSize, RejectedExecutionHandler handler) {
        this.executor = new PoolExecutor(this.getClass(), workSize, workSize, DEFAULT_KEEP_ALIVE_TIME, queueSize);
        if(handler != null) {
            executor.setRejectHandler(handler);
        }
    }

    public int getQueueSize() {
        return executor.getQueueSize();
    }

    public void stop() {
        this.executor.stop();
    }

    public void addTask(Task task) {
        try {
            this.executor.execute(new Worker(task));
        } catch (RejectedExecutionException e) {
            reject(task);
        }
    }

    protected class Worker implements Runnable {
        private Task task;

        public Worker(Task task) {
            this.task = task;
        }

        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            if (processorName != null) {
                setName(currentThread, processorName);
            }
            try {
                process(task);
            } catch (Exception e) {
                //TODO logger
            }
        }

        public Task getTask() {
            return task;
        }

        private void setName(Thread thread, String name) {
            try {
                thread.setName(name);
            } catch (SecurityException se) {
                //TODO logger
            }
        }
    }
}
