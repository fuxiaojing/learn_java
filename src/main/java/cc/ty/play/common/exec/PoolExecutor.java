package cc.ty.play.common.exec;

import java.util.concurrent.Executor;

/**
 * date: 2016/8/19 10:28.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface PoolExecutor extends Executor {

    int getQueueSize();

    void setRejectHandler();

    void stop();

}
