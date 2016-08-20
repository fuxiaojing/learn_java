package cc.ty.play.channel;

import cc.ty.play.channel.msg.LocalRequest;
import cc.ty.play.common.exec.PoolExecutor;

/**
 * date: 2016/8/20 11:20.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface ChannelContext {

    PoolExecutor executor();

    String name();

    Handler handler();

    boolean isRemoved();

    ChannelContext fireHandle(LocalRequest request);

}
