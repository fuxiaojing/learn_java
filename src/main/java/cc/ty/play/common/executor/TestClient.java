package cc.ty.play.common.executor;

import io.netty.util.concurrent.DefaultEventExecutor;

import io.netty.util.internal.chmv8.ForkJoinTask;
import javassist.bytecode.analysis.Executor;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date: 2016/8/16 19:17.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class TestClient {

    private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new DefaultExecutorServiceFactory(TestClient.class).newExecutorService(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello :" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.debug("test");
                }
            });
        }


        Thread.sleep(5000);
        executorService.shutdown();


    }

    static class TestTask extends ForkJoinTask<String> {

        @Override
        public String getRawResult() {
            return null;
        }

        @Override
        protected void setRawResult(String value) {

        }

        @Override
        protected boolean exec() {
            return false;
        }
    }
}
