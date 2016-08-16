
package cc.ty.play.common.executor;

import java.util.concurrent.ExecutorService;

/**
 * date: 2016/8/16 13:53.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface ExecutorServiceFactory {
    ExecutorService newExecutorService(int parallelism);
}

