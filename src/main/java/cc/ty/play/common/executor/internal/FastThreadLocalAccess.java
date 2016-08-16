package cc.ty.play.common.executor.internal;

import io.netty.util.internal.InternalThreadLocalMap;

/**
 * date: 2016/8/16 17:58.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface FastThreadLocalAccess {

    /**
     * Returns the internal data structure that keeps the thread-local variables bound to this thread.
     * Note that this method is for internal use only, and thus is subject to change at any time.
     */
    InternalThreadLocalMap threadLocalMap();

    /**
     * Sets the internal data structure that keeps the thread-local variables bound to this thread.
     * Note that this method is for internal use only, and thus is subject to change at any time.
     */
    void setThreadLocalMap(InternalThreadLocalMap threadLocalMap);
}
