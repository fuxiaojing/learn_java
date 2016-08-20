package cc.ty.play.channel;

import cc.ty.play.channel.msg.LocalRequest;
import cc.ty.play.channel.msg.LocalResponse;
import cc.ty.play.common.exec.PoolExecutor;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * date: 2016/8/19 14:43.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface ChannelPipeline extends Iterable<Map.Entry<String, Handler>> {

    /**
     * Inserts a {@link Handler} at the first position of this pipeline.
     *
     * @param name     the name of the handler to insert first. {@code null} to let the name auto-generated.
     * @param handler  the handler to insert first
     *
     * @throws IllegalArgumentException
     *         if there's an entry with the same name already in the pipeline
     * @throws NullPointerException
     *         if the specified handler is {@code null}
     */
    ChannelPipeline addFirst(String name, Handler handler);

    ChannelPipeline addFirst(PoolExecutor executor, String name, Handler handler);

    ChannelPipeline addLast(String name, Handler handler);

    ChannelPipeline addLast(PoolExecutor executor, String name, Handler handler);

    /**
     * Inserts a {@link Handler} before an existing handler of this
     * pipeline.
     *
     * @param baseName  the name of the existing handler
     * @param name      the name of the handler to insert before. {@code null} to let the name auto-generated.
     * @param handler   the handler to insert before
     *
     * @throws NoSuchElementException
     *         if there's no such entry with the specified {@code baseName}
     * @throws IllegalArgumentException
     *         if there's an entry with the same name already in the pipeline
     * @throws NullPointerException
     *         if the specified baseName or handler is {@code null}
     */
    ChannelPipeline addBefore(String baseName, String name, Handler handler);

    ChannelPipeline addBefore(PoolExecutor executor, String baseName, String name, Handler handler);

    ChannelPipeline addAfter(String baseName, String name, Handler handler);

    ChannelPipeline addAfter(PoolExecutor executor, String baseName, String name, Handler handler);

    ChannelPipeline addFirst(Handler... handlers);

    ChannelPipeline addFirst(PoolExecutor executor, Handler... handlers);

    ChannelPipeline addLast(Handler... handlers);

    ChannelPipeline addLast(PoolExecutor executor, Handler... handlers);

    /**
     * Removes the specified {@link Handler} from this pipeline.
     *
     * @param  handler          the {@link Handler} to remove
     *
     * @throws NoSuchElementException
     *         if there's no such handler in this pipeline
     * @throws NullPointerException
     *         if the specified handler is {@code null}
     */
    ChannelPipeline remove(Handler handler);

    ChannelPipeline remove(String name);

    /**
     * Removes the {@link Handler} of the specified type from this pipeline.
     *
     * @param <T>           the type of the handler
     * @param handlerType   the type of the handler
     *
     * @return the removed handler
     *
     * @throws NoSuchElementException
     *         if there's no such handler of the specified type in this pipeline
     * @throws NullPointerException
     *         if the specified handler type is {@code null}
     */
    <T extends Handler> T remove(Class<T> handlerType);

    /**
     * Replaces the specified {@link Handler} with a new handler in this pipeline.
     *
     * @param  oldHandler    the {@link Handler} to be replaced
     * @param  newName       the name under which the replacement should be added.
     *                       {@code null} to use the same name with the handler being replaced.
     * @param  newHandler    the {@link Handler} which is used as replacement
     *
     * @return itself

     * @throws NoSuchElementException
     *         if the specified old handler does not exist in this pipeline
     * @throws IllegalArgumentException
     *         if a handler with the specified new name already exists in this
     *         pipeline, except for the handler to be replaced
     * @throws NullPointerException
     *         if the specified old handler or new handler is {@code null}
     */
    ChannelPipeline replace(Handler oldHandler, String newName, Handler newHandler);

    Handler replace(String oldName, String newName, Handler newHandler);

    /**
     * Replaces the {@link Handler} of the specified type with a new handler in this pipeline.
     *
     * @param  oldHandlerType   the type of the handler to be removed
     * @param  newName          the name under which the replacement should be added.
     *                          {@code null} to use the same name with the handler being replaced.
     * @param  newHandler       the {@link Handler} which is used as replacement
     *
     * @return the removed handler
     *
     * @throws NoSuchElementException
     *         if the handler of the specified old handler type does not exist
     *         in this pipeline
     * @throws IllegalArgumentException
     *         if a handler with the specified new name already exists in this
     *         pipeline, except for the handler to be replaced
     * @throws NullPointerException
     *         if the specified old handler or new handler is {@code null}
     */
    <T extends Handler> T replace(Class<T> oldHandlerType, String newName,
            Handler newHandler);

    /**
     * Returns the {@link Handler} with the specified name in this
     * pipeline.
     *
     * @return the handler with the specified name.
     *         {@code null} if there's no such handler in this pipeline.
     */
    Handler get(String name);

    <T extends Handler> T get(Class<T> handlerType);

    List<String> names();

    Map<String, Handler> toMap();

    ChannelPipeline fireHandle(LocalRequest request);


}
