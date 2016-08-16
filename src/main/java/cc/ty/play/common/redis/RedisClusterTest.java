package cc.ty.play.common.redis;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;

/**
 * date: 2016/8/5 15:33.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class RedisClusterTest {

    public static void main(String[] args) {
        JedisCluster jedis = new JedisClusterUtil().instance();

        for (int i = 0; i < 200000; i++) {
            if(i< 23) {
                jedis.hset("device:" + i + ":connector_ip", "ip", "172.31.1.1" );
                jedis.hset("device:" + i + ":connector_ip", "t", String.valueOf(System.currentTimeMillis()));
            } else if (i < 100){
                jedis.hset("device:" + i + ":connector_ip", "ip", "172.31.1.2" );
                jedis.hset("device:" + i + ":connector_ip", "t", String.valueOf(System.currentTimeMillis()));
            } else {
                jedis.hset("device:" + i + ":connector_ip", "ip", "172.31.1.3" );
                jedis.hset("device:" + i + ":connector_ip", "t", String.valueOf(System.currentTimeMillis()));
            }
        }

    }

}
