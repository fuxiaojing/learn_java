package cc.ty.play.common.threadpool;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * date: 2016/7/18 14:56.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class PoolExecutor implements Executor{

    private final PoolQueue queue;

    private final int capacity;

    public PoolExecutor(PoolQueue queue) {
        this.queue = queue;
        this.capacity = queue.remainingCapacity();
        Executors.newCachedThreadPool();
    }

    public PoolExecutor(Class<?> type, int corePoolSize, int maxinumPoolSizelong, long keepAliveTime, int queueSize) {
        this(new PoolQueue(type, corePoolSize, maxinumPoolSizelong, keepAliveTime, queueSize));
    }

    @Override
    public void execute(Runnable command) {
        queue.execute(command);
    }

    public void setRejectHandler(RejectedExecutionHandler handler) {
        this.queue.setRejectedExecutionHandler(handler);
    }

    public void stop() {
        queue.stop();
    }

    public int getQueueSize() {
        return queue.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public int remainingCapacity() {
        return queue.remainingCapacity();
    }

    static class PoolQueue extends ThreadPoolExecutor {
        private final BlockingQueue<Runnable> workQueue;

        private final AtomicBoolean stop = new AtomicBoolean(false);

        public PoolQueue(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, ThreadFactory factory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory);
            this.workQueue = workQueue;
        }

        public PoolQueue(Class<?> type, int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueSize) {
            this(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new Queue(queueSize), new PoolFactory(type));
        }

        public void join() {
            boolean dead = isTerminated();

            while (!dead) {
                try {
                    dead = awaitTermination(50, TimeUnit.MICROSECONDS);
                } catch (InterruptedException e) {
                    //logger
                    break;
                }
            }
        }

        public int size() {
            return workQueue.size();
        }

        public int remainingCapacity() {
            return workQueue.remainingCapacity();
        }

        public void stop() {
            if(!stop.compareAndSet(false, true)){
                return;
            }
            shutdown();
            join();
        }
    }

    private static class Queue extends LinkedBlockingDeque<Runnable> {
        private static final long serialVersionUID = 4785163873836943689L;

        public Queue() {
            this(Integer.MAX_VALUE);
        }

        public Queue(int capacity) {
            super(capacity);
        }
    }

    static class PoolFactory implements ThreadFactory {
        private static final Sequencer seq;

        static {
            seq = new Sequencer();
        }

        private final ThreadGroup group;

        private  final Class<?> type;

        public PoolFactory(Class<?> type) {
            this.type = type;
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            String prefix = type.getSimpleName();
            Thread thread = new Thread(group, r, getName(prefix), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }

        private String getName(String prefix) {
            int count = seq.next(prefix);
            if(prefix == null) {
                return null;
            }
            return String.format("%s-%s",prefix, count);
        }

        private static class Sequencer {
            private final HashMap<String, AtomicInteger> map;

            public Sequencer() {
                this.map = new HashMap<>();
            }

            public synchronized int next(String name) {
                AtomicInteger integer = map.get(name);
                if(integer == null) {
                    integer = new AtomicInteger();
                    AtomicInteger old = map.putIfAbsent(name, integer);
                    if(old != null) {
                        integer = old;
                    }
                }
                return integer.getAndIncrement();
            }
        }
    }
}
