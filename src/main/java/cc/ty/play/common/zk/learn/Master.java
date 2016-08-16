package cc.ty.play.common.zk.learn;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.*;

/**
 * date: 2016/7/18 20:03.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Master implements Watcher {

    ZooKeeper zk;
    String hostPort;

    Master(String hostPort) {
        this.hostPort = hostPort;
    }

    ZooKeeper startZk() throws IOException {
        zk = new ZooKeeper(hostPort, 5000, this);
        return zk;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Master master = new Master("127.0.0.1:2181");
        ZooKeeper zk = master.startZk();
        String serverId = Integer.toHexString(new Random().nextInt());
        try {
            zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException.ConnectionLossException e) {
            System.out.println(e);
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        while(true) {
            Thread.sleep(60000);
        }
    }
}
