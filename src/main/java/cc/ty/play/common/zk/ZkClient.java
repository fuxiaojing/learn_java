package cc.ty.play.common.zk;

import java.util.List;

/**
 * date: 2016/7/18 20:03.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface ZkClient {

    void create(String path, boolean ephemeral) throws Exception;

    void delete(String path) throws Exception;

    boolean exist(String path) throws Exception;

    List<String> getChildren(String path) throws Exception;

    List<String> addChildListener(String path, ChildListener listener) throws Exception;

    void removeChildListener(String path, ChildListener listener) throws Exception;

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);

    boolean isConnected();

    void close();

    URL getUrl();
}