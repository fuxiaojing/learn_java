package cc.ty.play.common.threadpool.executor;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * date: 2016/7/25 11:26.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Demo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> fs: results) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                System.out.println(e);
            } catch (ExecutionException e) {
                System.out.println(e);
            }
        }
    }

    static class TaskWithResult implements Callable<String> {
        private int id;

        public TaskWithResult(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            return "result with id:" + id;
        }
    }

}
