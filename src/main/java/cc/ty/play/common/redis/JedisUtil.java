package cc.ty.play.common.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * date: 2016/7/15 15:05.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class JedisUtil {
    private JedisPool jedisPool;

    public JedisUtil() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setJmxEnabled(true);
        config.setMaxTotal(20);
        this.jedisPool = new JedisPool(config, "172.31.1.153", 6379);
    }

    public JedisPool get() {
        return jedisPool;
    }
}
