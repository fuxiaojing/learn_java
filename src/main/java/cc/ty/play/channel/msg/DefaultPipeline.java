package cc.ty.play.channel.msg;

import cc.ty.play.channel.ChannelPipeline;
import cc.ty.play.channel.Handler;
import cc.ty.play.common.exec.PoolExecutor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * date: 2016/8/20 11:46.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class DefaultPipeline implements ChannelPipeline {



    @Override
    public ChannelPipeline addFirst(String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addFirst(PoolExecutor executor, String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addLast(String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addLast(PoolExecutor executor, String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addBefore(String baseName, String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addBefore(PoolExecutor executor, String baseName, String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addAfter(String baseName, String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addAfter(PoolExecutor executor, String baseName, String name, Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline addFirst(Handler... handlers) {
        return null;
    }

    @Override
    public ChannelPipeline addFirst(PoolExecutor executor, Handler... handlers) {
        return null;
    }

    @Override
    public ChannelPipeline addLast(Handler... handlers) {
        return null;
    }

    @Override
    public ChannelPipeline addLast(PoolExecutor executor, Handler... handlers) {
        return null;
    }

    @Override
    public ChannelPipeline remove(Handler handler) {
        return null;
    }

    @Override
    public ChannelPipeline remove(String name) {
        return null;
    }

    @Override
    public <T extends Handler> T remove(Class<T> handlerType) {
        return null;
    }

    @Override
    public ChannelPipeline replace(Handler oldHandler, String newName, Handler newHandler) {
        return null;
    }

    @Override
    public Handler replace(String oldName, String newName, Handler newHandler) {
        return null;
    }

    @Override
    public <T extends Handler> T replace(Class<T> oldHandlerType, String newName, Handler newHandler) {
        return null;
    }

    @Override
    public Handler get(String name) {
        return null;
    }

    @Override
    public <T extends Handler> T get(Class<T> handlerType) {
        return null;
    }

    @Override
    public List<String> names() {
        return null;
    }

    @Override
    public Map<String, Handler> toMap() {
        return null;
    }

    @Override
    public ChannelPipeline fireHandle(LocalRequest request) {
        return null;
    }

    @Override
    public Iterator<Map.Entry<String, Handler>> iterator() {
        return null;
    }
}
