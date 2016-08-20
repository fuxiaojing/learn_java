package cc.ty.play.common.exec;

/**
 * date: 2016/8/19 10:32.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface ExecutorFactory {
    PoolExecutor newExexutor() throws Exception;
}
