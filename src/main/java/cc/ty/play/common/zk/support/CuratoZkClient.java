package cc.ty.play.common.zk.support;

import cc.ty.play.common.zk.ChildListener;
import cc.ty.play.common.zk.StateListener;
import cc.ty.play.common.zk.URL;
import cc.ty.play.common.zk.ZkClient;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date: 2016/7/20 10:09.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class CuratoZkClient implements ZkClient, StateListener {

    private final static Logger logger = LoggerFactory.getLogger(CuratoZkClient.class);

    private final static String BACKUPS_KEY = "backups";

    private final CuratorFramework client;

    private final URL url;

    private final ConcurrentMap<String, ConcurrentMap<ChildListener, CuratorWatcher>> childListeners =
            new ConcurrentHashMap<>();

    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();

    public CuratoZkClient(URL url) {
        this.url = url;
        try {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(getZkAddress(url))
                    .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
                    .connectionTimeoutMs(5000);
            client = builder.build();
            client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState state) {
                    if (state == ConnectionState.LOST) {
                        CuratoZkClient.this.stateChanged(StateListener.DISCONNECTED);
                    } else if (state == ConnectionState.CONNECTED) {
                        CuratoZkClient.this.stateChanged(StateListener.CONNECTED);
                    } else if (state == ConnectionState.RECONNECTED) {
                        CuratoZkClient.this.stateChanged(StateListener.RECONNECTED);
                    }
                }
            });
            client.start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void stateChanged(int state) {

    }

    @Override
    public void create(String path, boolean ephemeral) throws Exception {
        if (ephemeral) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            return;
        }
        client.create().creatingParentsIfNeeded().forPath(path);
    }

    @Override
    public void delete(String path) throws Exception {
        client.delete().forPath(path);
    }

    @Override
    public boolean exist(String path) throws Exception {
        return client.checkExists().forPath(path) != null;
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    @Override
    public List<String> addChildListener(String path, ChildListener listener) throws Exception {
        ConcurrentMap<ChildListener, CuratorWatcher> listeners = childListeners.get(path);
        if (listeners == null) {
            childListeners.putIfAbsent(path, new ConcurrentHashMap<ChildListener, CuratorWatcher>());
            listeners = childListeners.get(path);
        }
        CuratorWatcher watcher = listeners.get(listener);
        if (watcher == null) {
            listeners.putIfAbsent(listener, new CuratorWatcherImpl(listener));
            watcher = listeners.get(listener);
        }

        return client.getChildren().usingWatcher(watcher).forPath(path);
    }

    @Override
    public void removeChildListener(String path, ChildListener listener) throws Exception {
        ConcurrentMap<ChildListener, CuratorWatcher> listeners = childListeners.get(path);
        if (listeners != null) {
            CuratorWatcher watcher = listeners.remove(listener);
            if (watcher != null) {
                ((CuratorWatcherImpl) watcher).unwatch();
            }
        }
    }

    @Override
    public void addStateListener(StateListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener is null");
        }
        stateListeners.add(listener);
    }

    @Override
    public void removeStateListener(StateListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener is null");
        }
        stateListeners.remove(listener);
    }

    @Override
    public boolean isConnected() {
        return  client.getZookeeperClient().isConnected();
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    public URL getUrl() {
        return null;
    }

    public String getZkAddress(URL url) {
        String address = url.getUrl().getHost();
        if (url.getUrl().getPort() > 0) {
            address += (":" + url.getUrl().getPort());
        } else {
            address += ":2181";
        }
        StringBuilder builder = new StringBuilder(address);
        String backups = url.getParameter(BACKUPS_KEY);
        if (backups != null && backups.length() > 0) {
            for (String backup : backups.split(",")) {
                builder.append(",");
                builder.append(getAddress(backup, 2181));
            }
        }
        return builder.toString();
    }

    private String getAddress(String address, int defaultPort) {
        if (address != null && address.length() > 0 && defaultPort > 0) {
            int i = address.indexOf(':');
            if (i < 0) {
                return address + ":" + defaultPort;
            } else if (Integer.parseInt(address.substring(i + 1)) == 0) {
                return address.substring(0, i + 1) + defaultPort;
            }
        }
        return address;
    }

    private class CuratorWatcherImpl implements CuratorWatcher {

        private volatile ChildListener listener;

        public CuratorWatcherImpl(ChildListener listener) {
            this.listener = listener;
        }

        public void unwatch() {
            listener = null;
        }

        @Override
        public void process(WatchedEvent watchedEvent) throws Exception {
            ChildListener listener1 = listener;
            if (listener1 != null) {
                logger.info("zookeeper.event:{}", watchedEvent);
                listener1.childChanged(watchedEvent.getPath(),
                        client.getChildren().usingWatcher(this).forPath(watchedEvent.getPath()));
            }
        }
    }
}
