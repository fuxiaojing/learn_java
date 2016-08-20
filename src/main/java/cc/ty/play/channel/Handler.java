package cc.ty.play.channel;

import cc.ty.play.channel.msg.LocalRequest;

/**
 * date: 2016/8/19 14:44.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface Handler {

    void handle(LocalRequest request) throws Exception;

}
