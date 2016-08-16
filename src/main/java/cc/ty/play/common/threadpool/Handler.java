package cc.ty.play.common.threadpool;

/**
 * date: 2016/7/18 15:05.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface Handler {

    void process(Task task);

    void reject(Task task);

}
