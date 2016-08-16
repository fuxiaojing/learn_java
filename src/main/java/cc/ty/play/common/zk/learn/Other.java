package cc.ty.play.common.zk.learn;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * date: 2016/7/18 20:44.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Other {

    Random random = new Random();

    String serverId;

    boolean isLeader;

    ZooKeeper zk;

    boolean checkMaster() {
        while (true) {
            try {
                Stat stat = new Stat();
                byte[] data = zk.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException.NoNodeException e){
                return false;
            } catch(KeeperException.ConnectionLossException e){
                return false;
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

    }

    void runForMaster() throws InterruptedException{
        String serverId = Integer.toHexString(new Random().nextInt());
        try {
            zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            isLeader = true;

        } catch (KeeperException.NoNodeException e1){
            isLeader = false;
        }catch (KeeperException.ConnectionLossException e2) {
            System.out.println(e2);
        } catch (KeeperException e3) {
            e3.printStackTrace();
        }
    }

}
