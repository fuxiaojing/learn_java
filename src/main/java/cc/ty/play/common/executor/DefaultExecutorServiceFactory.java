package cc.ty.play.common.executor;

import cc.ty.play.common.executor.internal.FastThreadLocalAccess;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.chmv8.ForkJoinPool;
import io.netty.util.internal.chmv8.ForkJoinWorkerThread;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date: 2016/8/16 17:41.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class DefaultExecutorServiceFactory implements ExecutorServiceFactory{

    private static final Logger logger = LoggerFactory.getLogger(DefaultExecutorServiceFactory.class);

    private static final AtomicInteger executorId = new AtomicInteger();
    private final String namePrefix;

    public DefaultExecutorServiceFactory(Class<?> clazzNamePrefix) {
        this(toName(clazzNamePrefix));
    }

    public DefaultExecutorServiceFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public ExecutorService newExecutorService(int parallelism) {
        DefaultForkJoinWorkerTreadFactory treadFactory = new DefaultForkJoinWorkerTreadFactory(
                namePrefix + '-' + executorId.getAndIncrement());
        return new ForkJoinPool(parallelism, treadFactory, DefaultUncaughtExceptionHandler.INSTANCE, true);
    }

    private static String toName(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        String clazzName = StringUtil.simpleClassName(clazz);
        switch (clazzName.length()) {
            case 0:
                return "unknown";
            case 1:
                return clazzName.toLowerCase(Locale.US);
            default:
                if (Character.isUpperCase(clazzName.charAt(0)) && Character.isLowerCase(clazzName.charAt(1))) {
                   return Character.toLowerCase(clazzName.charAt(0)) + clazzName.substring(1);
                } else {
                    return clazzName;
                }
        }
    }

    private static final class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        private static final DefaultUncaughtExceptionHandler INSTANCE = new DefaultUncaughtExceptionHandler();

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("Uncaught exception in thread: {}", t.getName(), e);
            }
        }
    }

    private static final class DefaultForkJoinWorkerTreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

        private final AtomicInteger idx = new AtomicInteger();
        private final String namePrefix;

        DefaultForkJoinWorkerTreadFactory (String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
            ForkJoinWorkerThread thread = new DefaultForkJoinWorkerThread(pool);
            thread.setName(namePrefix + '-' + idx.getAndIncrement());
            thread.setPriority(Thread.MAX_PRIORITY);
            return thread;
        }
    }

    private static final class DefaultForkJoinWorkerThread extends ForkJoinWorkerThread implements
            FastThreadLocalAccess {

        private InternalThreadLocalMap threadLocalMap;

        DefaultForkJoinWorkerThread(ForkJoinPool pool) {
            super(pool);
        }

        @Override
        public InternalThreadLocalMap threadLocalMap() {
            return threadLocalMap;
        }

        @Override
        public void setThreadLocalMap(InternalThreadLocalMap threadLocalMap) {
            this.threadLocalMap = threadLocalMap;
        }
    }

}
